package com.mobile.physiolink.ui.patient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentPatientHomeBinding;
import com.mobile.physiolink.model.user.singleton.UserHolder;
import com.mobile.physiolink.ui.patient.adapter.AdapterForHistoryPatient;
import com.mobile.physiolink.ui.patient.viewmodel.PatientHomeViewModel;


public class PatientHomeFragment extends Fragment
{
    boolean hasAppointment=true;

    String h1[],h2[],h3[],h4[],h5[];
    Fragment AppointmentFragment = new UpcomingAppointmentFragment();
    Fragment NoAppointmentFragment= new NoUpcomingAppointmentFragment();

    private FragmentPatientHomeBinding binding;
    private PatientHomeViewModel viewmodel;

    public PatientHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        viewmodel = new ViewModelProvider(this).get(PatientHomeViewModel.class);
        viewmodel.getDoctor().observe(getViewLifecycleOwner(), doctor ->
        {
            System.out.println(doctor);
        });
        viewmodel.getLatestCompletedAppointment().observe(getViewLifecycleOwner(), appoint ->
        {
            System.out.println();
        });

        binding = FragmentPatientHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        if(hasAppointment){
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.upcomingAppointmentFragmentContainer, AppointmentFragment);
            transaction.commit();
        }
        else{
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.upcomingAppointmentFragmentContainer, NoAppointmentFragment);
            transaction.commit();
        }

        viewmodel.loadDoctor(UserHolder.patient().getDoctorId());
        viewmodel.loadLatestCompletedAppointment(UserHolder.patient().getDoctorId(),
                UserHolder.patient().getId());

        h1=getResources().getStringArray(R.array.appointmentsService);
        h2=getResources().getStringArray(R.array.servicesPatientHistoryListExampleDate);
        h3=getResources().getStringArray(R.array.timePatientHistory);
        h4=getResources().getStringArray(R.array.servicesListExampleDescription);
        h5=getResources().getStringArray(R.array.paroxesCostExamle);

        binding.patientHistoryLastItemPatient.setAdapter(new AdapterForHistoryPatient(h2,h3,h4,h1,h5,R.id.patientHistoryLastItemPatient));
        binding.patientHistoryLastItemPatient.setLayoutManager(new LinearLayoutManager(this.getContext()));


        binding.doctorInfoBasicPatientConstraint.setOnClickListener(v ->
                Navigation.findNavController(getActivity(), R.id.containerPatient)
                        .navigate(R.id.action_fragmentPatientHome_to_fragmentPatientDoctor));

        binding.myDoctorPatientBtn.setOnClickListener(v ->
                Navigation.findNavController(getActivity(), R.id.containerPatient)
                        .navigate(R.id.action_fragmentPatientHome_to_fragmentPatientDoctor));

        binding.seeYourHistoryBtnPatient.setOnClickListener(v ->
                Navigation.findNavController(getActivity(),R.id.containerPatient)
                        .navigate(R.id.action_fragmentPatientHome_to_patientHistoryFragment));

    }
}

