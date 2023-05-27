package com.mobile.physiolink.ui.doctor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentDoctorPatientHistoryBinding;
import com.mobile.physiolink.ui.doctor.adapter.AdapterForPatientHistory;
import com.mobile.physiolink.ui.decoration.DecorationSpacingItem;
import com.mobile.physiolink.ui.doctor.viewmodel.DoctorPatientHistoryViewModel;

public class DoctorPatientHistoryFragment extends Fragment
{
    private FragmentDoctorPatientHistoryBinding binding;
    private DoctorPatientHistoryViewModel viewModel;
    private AdapterForPatientHistory adapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        binding = FragmentDoctorPatientHistoryBinding.inflate(inflater, container, false);

        adapter = new AdapterForPatientHistory();

        viewModel = new ViewModelProvider(this).get(DoctorPatientHistoryViewModel.class);
        viewModel.getSelectedPatient().observe(getViewLifecycleOwner(), patient ->
        {
            binding.patientHistoryNameDoctor
                    .setText(patient.getName());
            binding.patientHistorySurnameDoctor
                    .setText(patient.getSurname());
            binding.emailPatientHistoryDoctor
                    .setText(patient.getEmail());
            binding.phonePatientHistoryDoctor
                    .setText(patient.getPhoneNumber());
            binding.amkaPatientHistoryDoctor
                    .setText(patient.getAmka());
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        binding.patientHistoryInfoDownBtn.setOnClickListener(view1 ->
        {
            if(binding.patientHistoryMoreInfoDoctorConstraint.getVisibility()==View.GONE)
            {
                TransitionManager.beginDelayedTransition(binding.patientHistoryInfoDoctor,
                        new AutoTransition());
                binding.patientHistoryMoreInfoDoctorConstraint.setVisibility(View.VISIBLE);
                binding.patientHistoryInfoDownBtn
                        .setBackgroundResource(R.drawable.baseline_arrow_drop_up_24);
            }
            else
            {
                TransitionManager.beginDelayedTransition(binding.patientHistoryInfoDoctor,
                        new AutoTransition());
                binding.patientHistoryMoreInfoDoctorConstraint.setVisibility(View.GONE);
                binding.patientHistoryInfoDownBtn
                        .setBackgroundResource(R.drawable.baseline_arrow_drop_down_24);
            }
        });

        DecorationSpacingItem itemDecoration = new DecorationSpacingItem(20); // 20px spacing
        binding.servicesListPatientHistoryDoctor.addItemDecoration(itemDecoration);

        binding.servicesListPatientHistoryDoctor.setAdapter(adapter);
        binding.servicesListPatientHistoryDoctor.setLayoutManager(new LinearLayoutManager(this.getContext()));

        long patientId = DoctorPatientHistoryFragmentArgs.fromBundle(getArguments()).getPatientId();
        viewModel.loadPatient(patientId);
    }


}