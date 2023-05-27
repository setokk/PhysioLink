package com.mobile.physiolink.ui.doctor;
// TODO: Na ginei katallhlh allagh tou arxeiou molis ginei h diasyndesh me ta dedomena ths bashs
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentDoctorPatientsBinding;
import com.mobile.physiolink.model.user.singleton.UserHolder;
import com.mobile.physiolink.ui.doctor.adapter.AdapterForPatients;
import com.mobile.physiolink.ui.decoration.DecorationSpacingItem;
import com.mobile.physiolink.ui.doctor.viewmodel.DoctorPatientsViewModel;

public class DoctorPatientsFragment extends Fragment
{
    private FragmentDoctorPatientsBinding binding;
    private DoctorPatientsViewModel viewModel;
    private AdapterForPatients adapter;

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
        binding = FragmentDoctorPatientsBinding.inflate(inflater, container, false);

        adapter = new AdapterForPatients();

        viewModel = new ViewModelProvider(this).get(DoctorPatientsViewModel.class);
        viewModel.getDoctorPatients().observe(getViewLifecycleOwner(), patients ->
        {
            adapter.setPatients(patients);
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        DecorationSpacingItem itemDecoration = new DecorationSpacingItem(20); // 20px spacing
        binding.patientsListDoctor.addItemDecoration(itemDecoration);

        binding.patientsListDoctor.setAdapter(adapter);
        binding.patientsListDoctor.setLayoutManager(new LinearLayoutManager(this.getContext()));

        viewModel.loadDoctorPatients(UserHolder.doctor().getId());

        binding.newPatientBtn.setOnClickListener(v ->
                Navigation.findNavController(getActivity(), R.id.container)
                        .navigate(R.id.action_doctorPatientsFragment_to_doctorNewPatientFragment));

        binding.tempHistoryBtn.setOnClickListener(v ->
                Navigation.findNavController(getActivity(), R.id.container)
                        .navigate(R.id.action_doctorPatientsFragment_to_doctorPatientHistoryFragment));
    }
}