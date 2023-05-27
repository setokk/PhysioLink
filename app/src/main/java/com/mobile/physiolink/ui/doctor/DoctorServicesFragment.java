package com.mobile.physiolink.ui.doctor;
// TODO Na ginei katallhlh allagh tou arxeiou molis ginei h diasyndesh me ta dedomena ths bashs
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.physiolink.R;

import com.mobile.physiolink.databinding.FragmentDoctorServicesBinding;
import com.mobile.physiolink.model.user.singleton.UserHolder;
import com.mobile.physiolink.ui.doctor.adapter.AdapterForDoctorServices;
import com.mobile.physiolink.ui.decoration.DecorationSpacingItem;
import com.mobile.physiolink.ui.doctor.viewmodel.DoctorServicesViewModel;

public class DoctorServicesFragment extends Fragment
{
    private FragmentDoctorServicesBinding binding;
    private DoctorServicesViewModel viewModel;
    private AdapterForDoctorServices adapter;

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
        binding = FragmentDoctorServicesBinding.inflate(inflater, container, false);

        adapter = new AdapterForDoctorServices();
        viewModel = new ViewModelProvider(this).get(DoctorServicesViewModel.class);
        viewModel.getDoctorServices().observe(getViewLifecycleOwner(), services ->
        {
            adapter.setServices(services);
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        DecorationSpacingItem itemDecoration = new DecorationSpacingItem(20); // 20px spacing
        binding.servicesListDoctor.addItemDecoration(itemDecoration);

        binding.servicesListDoctor.setAdapter(adapter);
        binding.servicesListDoctor.setLayoutManager(new LinearLayoutManager(this.getContext()));

        viewModel.loadDoctorServices(UserHolder.doctor().getId());
    }
}