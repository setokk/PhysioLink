package com.mobile.physiolink.ui.doctor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.physiolink.databinding.FragmentDoctorAppointmentsBinding;
import com.mobile.physiolink.ui.doctor.adapter.AdapterForAppointments;
import com.mobile.physiolink.ui.decoration.DecorationSpacingItem;

public class DoctorAppointmentsFragment extends Fragment
{
    private FragmentDoctorAppointmentsBinding binding;
    private AdapterForAppointments adapter;

    public DoctorAppointmentsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        binding = FragmentDoctorAppointmentsBinding.inflate(inflater, container, false);

        adapter = new AdapterForAppointments();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        DecorationSpacingItem itemDecoration = new DecorationSpacingItem(20); // 20px spacing
        binding.appointmentsListAllDoctor.addItemDecoration(itemDecoration);

        binding.appointmentsListAllDoctor.setAdapter(adapter);
        binding.appointmentsListAllDoctor.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }
}