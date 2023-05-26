package com.mobile.physiolink.ui.patient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentPatientDoctorBinding;
import com.mobile.physiolink.model.user.singleton.UserHolder;
import com.mobile.physiolink.ui.decoration.DecorationSpacingItem;
import com.mobile.physiolink.ui.patient.adapter.AdapterForPatientDoctorServices;
import com.mobile.physiolink.ui.patient.viewmodel.PatientDoctorViewModel;

public class PatientDoctorFragment extends Fragment
{
    private FragmentPatientDoctorBinding binding;
    private PatientDoctorViewModel viewModel;
    private AdapterForPatientDoctorServices adapter;

    String s1[],s2[],s3[];

    public PatientDoctorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        viewModel = new ViewModelProvider(this).get(PatientDoctorViewModel.class);
        viewModel.getDoctor().observe(getViewLifecycleOwner(), doctor ->
        {
            binding.doctorPatientName.setText(doctor.getName());
            binding.doctorPatientSurname.setText(doctor.getSurname());
            binding.doctorAfmPatient.setText(doctor.getAfm());
            binding.doctorEmailPatient.setText(doctor.getEmail());
            binding.doctorPhonePatient.setText(doctor.getPhoneNumber());
            binding.physioAdressPatient.setText(doctor.getAddress());
            binding.physioCityPatient.setText(doctor.getCity());
            binding.physioPCPatient.setText(doctor.getPostalCode());
            binding.physioNamePatient.setText(doctor.getPhysioName());
        });
        // Inflate the layout for this fragment
        binding = FragmentPatientDoctorBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        viewModel.loadDoctor(UserHolder.patient().getDoctorId());
        binding.doctorPatientDownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.doctorPhysioMoreInfoPatientConstraint.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(binding.doctorPhysioInfoPatientCardView, new AutoTransition());
                    binding.doctorPhysioMoreInfoPatientConstraint.setVisibility(View.VISIBLE);
                    binding.doctorPatientDownBtn.setBackgroundResource(R.drawable.baseline_arrow_drop_up_24);
                }
                else{
                    TransitionManager.beginDelayedTransition(binding.doctorPhysioInfoPatientCardView, new AutoTransition());
                    binding.doctorPhysioMoreInfoPatientConstraint.setVisibility(View.GONE);
                    binding.doctorPatientDownBtn.setBackgroundResource(R.drawable.baseline_arrow_drop_down_24);
                }
            }
        });

        binding.patientDoctorMakeAppointmentBtn.setOnClickListener(v ->
                Navigation.findNavController(getActivity(),R.id.containerPatient)
                        .navigate(R.id.action_fragmentPatientDoctor_to_fragmentRequestAppointment));

        s1=getResources().getStringArray(R.array.appointmentsService);
        s2=getResources().getStringArray(R.array.servicesListExampleDescription);
        s3=getResources().getStringArray(R.array.servicesListExamplePrices);

        binding.doctorPatientServicesList.addItemDecoration(new DecorationSpacingItem(20));

        adapter = new AdapterForPatientDoctorServices();
        binding.doctorPatientServicesList.setAdapter(adapter);
        binding.doctorPatientServicesList.setLayoutManager(new LinearLayoutManager(this.getContext()));


    }
}