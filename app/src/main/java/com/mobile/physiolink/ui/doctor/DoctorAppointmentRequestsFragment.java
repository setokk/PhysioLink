package com.mobile.physiolink.ui.doctor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mobile.physiolink.databinding.FragmentDoctorAppointmentRequestsBinding;
import com.mobile.physiolink.ui.decoration.DecorationSpacingItem;
import com.mobile.physiolink.ui.doctor.adapter.AdapterForRequests;

public class DoctorAppointmentRequestsFragment extends Fragment
{
    private FragmentDoctorAppointmentRequestsBinding binding;
    private AdapterForRequests adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {super.onCreate(savedInstanceState);}


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDoctorAppointmentRequestsBinding.inflate(inflater,container,false);

        adapter = new AdapterForRequests();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DecorationSpacingItem spacingItem = new DecorationSpacingItem(20);
        binding.requestListDoctor.addItemDecoration(spacingItem);

        binding.requestListDoctor.setAdapter(adapter);
        binding.requestListDoctor.setLayoutManager(new LinearLayoutManager(this.getContext()));

        // TODO: DO IN OBSERVE
        if (true)
        {
            binding.noRequestsTextView.setVisibility(View.VISIBLE);
        }
        else
        {
            binding.noRequestsTextView.setVisibility(View.GONE);
        }
    }
}
