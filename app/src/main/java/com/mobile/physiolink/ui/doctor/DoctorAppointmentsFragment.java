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

import com.mobile.physiolink.databinding.FragmentDoctorAppointmentsBinding;
import com.mobile.physiolink.model.user.singleton.UserHolder;
import com.mobile.physiolink.ui.doctor.adapter.AdapterForAppointments;
import com.mobile.physiolink.ui.decoration.DecorationSpacingItem;
import com.mobile.physiolink.ui.doctor.viewmodel.DoctorAppointmentsViewModel;
import com.mobile.physiolink.util.date.DateFormatter;

import java.util.Calendar;

public class DoctorAppointmentsFragment extends Fragment
{
    private FragmentDoctorAppointmentsBinding binding;
    private DoctorAppointmentsViewModel viewModel;
    private AdapterForAppointments adapter;

    private Calendar currCalendar;

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
        viewModel = new ViewModelProvider(this).get(DoctorAppointmentsViewModel.class);
        viewModel.getDoctorAppointments().observe(getViewLifecycleOwner(), appointments ->
        {
            adapter.setAppointments(appointments);
        });

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

        currCalendar = Calendar.getInstance();
        currCalendar.setTimeInMillis(currCalendar.getTimeInMillis());
        String date = currCalendar.get(Calendar.YEAR) + "-" +
                (currCalendar.get(Calendar.MONTH) + 1) + "-" +
                currCalendar.get(Calendar.DAY_OF_MONTH);
        viewModel.loadDoctorAppointments(UserHolder.doctor().getId(), date);
    }
}