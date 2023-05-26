package com.mobile.physiolink.ui.patient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentPatientHomeBinding;
import com.mobile.physiolink.model.appointment.Appointment;
import com.mobile.physiolink.model.user.singleton.UserHolder;
import com.mobile.physiolink.ui.patient.adapter.AdapterForHistoryPatient;
import com.mobile.physiolink.ui.patient.viewmodel.PatientHomeViewModel;


public class PatientHomeFragment extends Fragment
{
    private FragmentPatientHomeBinding binding;
    private AdapterForHistoryPatient adapter;
    private PatientHomeViewModel viewmodel;

    private Fragment AppointmentFragment;

    public PatientHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        binding = FragmentPatientHomeBinding.inflate(inflater, container, false);

        viewmodel = new ViewModelProvider(this).get(PatientHomeViewModel.class);
        viewmodel.getDoctor().observe(getViewLifecycleOwner(), doctor ->
        {
            System.out.println(doctor);
        });
        viewmodel.getUpcomingAppointment().observe(getViewLifecycleOwner(), appoint ->
        {
            if (appoint.isFound())
            {
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                AppointmentFragment = new UpcomingAppointmentFragment(appoint);
                transaction.replace(R.id.upcomingAppointmentFragmentContainer, AppointmentFragment);
                transaction.commit();
            }
        });
        viewmodel.getLatestCompletedAppointment().observe(getViewLifecycleOwner(), appoint ->
        {
            System.out.println(appoint.getDate() + "-" + appoint.getHour());

            Appointment[] appointments = new Appointment[1];
            appointments[0] = appoint;
            adapter.setAppointments(appointments);
        });

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        this.adapter = new AdapterForHistoryPatient();
        binding.patientHistoryLastItemPatient.setAdapter(adapter);
        binding.patientHistoryLastItemPatient.setLayoutManager(new LinearLayoutManager(this.getContext()));

        binding.patientNamePatient.setText(String.format("%s %s",
                UserHolder.patient().getName(), UserHolder.patient().getSurname()));

        /* Load data */
        viewmodel.loadDoctor(UserHolder.patient().getDoctorId());
        viewmodel.loadUpcomingAppointment(UserHolder.patient().getDoctorId(),
                UserHolder.patient().getId());
        viewmodel.loadLatestCompletedAppointment(UserHolder.patient().getDoctorId(),
                UserHolder.patient().getId());

        binding.doctorInfoBasicPatientConstraint.setOnClickListener(v ->
                Navigation.findNavController(getActivity(), R.id.containerPatient)
                        .navigate(R.id.action_fragmentPatientHome_to_fragmentPatientDoctor));

        binding.myDoctorPatientBtn.setOnClickListener(v ->
                Navigation.findNavController(getActivity(), R.id.containerPatient)
                        .navigate(R.id.action_fragmentPatientHome_to_fragmentPatientDoctor));

        binding.seeYourHistoryBtnPatient.setOnClickListener(v ->
                Navigation.findNavController(getActivity(),R.id.containerPatient)
                        .navigate(R.id.action_fragmentPatientHome_to_patientHistoryFragment));

        binding.patientLoginInfoConstraint.setOnClickListener(v ->
                Navigation.findNavController(getActivity(),R.id.containerPatient)
                        .navigate(R.id.action_fragmentPatientHome_to_fragmentPatientProfile));



    }
}

