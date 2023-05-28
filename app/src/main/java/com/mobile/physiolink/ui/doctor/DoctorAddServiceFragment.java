package com.mobile.physiolink.ui.doctor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.physiolink.databinding.FragmentDoctorAddServiceBinding;
import com.mobile.physiolink.model.user.singleton.UserHolder;
import com.mobile.physiolink.ui.decoration.DecorationSpacingItem;
import com.mobile.physiolink.ui.doctor.adapter.AdapterForNewDoctorServices;
import com.mobile.physiolink.ui.doctor.viewmodel.DoctorAddServicesViewModel;


public class DoctorAddServiceFragment extends Fragment {

    private FragmentDoctorAddServiceBinding binding;
    private AdapterForNewDoctorServices adapter;
    private DoctorAddServicesViewModel viewModel;


    public DoctorAddServiceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        adapter = new AdapterForNewDoctorServices();
        viewModel = new ViewModelProvider(this).get(DoctorAddServicesViewModel.class);
        viewModel.getNewDoctorServices().observe(getViewLifecycleOwner(), newDoctorServices ->{
            adapter.setServices(newDoctorServices);
        });

        // Inflate the layout for this fragment
        binding = FragmentDoctorAddServiceBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        binding.doctorAddServicesList.setAdapter(adapter);
        binding.doctorAddServicesList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        DecorationSpacingItem itemDecoration = new DecorationSpacingItem(20); // 20px spacing
        binding.doctorAddServicesList.addItemDecoration(itemDecoration);

        viewModel.loadNewDoctorServices(UserHolder.doctor().getId());
    }
}