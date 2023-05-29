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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;

public class DoctorAppointmentsFragment extends Fragment
{
    private FragmentDoctorAppointmentsBinding binding;
    private DoctorAppointmentsViewModel viewModel;
    private AdapterForAppointments adapter;

    private LocalDate currDate;

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

        setupWeekSwitchButtons();
        setupDaySwitchButtons();

        currDate = LocalDate.now();
        String date = DateFormatter.formatToDate(currDate.getYear(),
                currDate.getMonthValue(),
                currDate.getDayOfMonth());

        updateSelectedDateText();
        viewModel.loadDoctorAppointments(UserHolder.doctor().getId(), date);
    }

    private void setupWeekSwitchButtons()
    {
        binding.leftArrowDoctorAppointment.setOnClickListener(v ->
        {
            currDate = currDate.minusWeeks(1);
            updateSelectedDateText();
            viewModel.loadDoctorAppointments(UserHolder.doctor().getId(), getFormattedDate());
        });
        binding.rightArrowDoctorAppointment.setOnClickListener(v ->
        {
            currDate = currDate.plusWeeks(1);
            updateSelectedDateText();
            viewModel.loadDoctorAppointments(UserHolder.doctor().getId(), getFormattedDate());
        });
    }

    private void setupDaySwitchButtons()
    {
        binding.mondayButton.setOnClickListener(v ->
        {
            currDate = currDate.with(DayOfWeek.MONDAY);
            updateSelectedDateText();
            viewModel.loadDoctorAppointments(UserHolder.doctor().getId(), getFormattedDate());
        });

        binding.mondayButton.setOnClickListener(v ->
        {
            currDate = currDate.with(DayOfWeek.MONDAY);
            updateSelectedDateText();
            viewModel.loadDoctorAppointments(UserHolder.doctor().getId(), getFormattedDate());
        });

        binding.tuesdayButton.setOnClickListener(v ->
        {
            currDate = currDate.with(DayOfWeek.TUESDAY);
            updateSelectedDateText();
            viewModel.loadDoctorAppointments(UserHolder.doctor().getId(), getFormattedDate());
        });

        binding.wednsdayButton.setOnClickListener(v ->
        {
            currDate = currDate.with(DayOfWeek.WEDNESDAY);
            updateSelectedDateText();
            viewModel.loadDoctorAppointments(UserHolder.doctor().getId(), getFormattedDate());
        });

        binding.thursdayButton.setOnClickListener(v ->
        {
            currDate = currDate.with(DayOfWeek.THURSDAY);
            updateSelectedDateText();
            viewModel.loadDoctorAppointments(UserHolder.doctor().getId(), getFormattedDate());
        });

        binding.fridayButton.setOnClickListener(v ->
        {
            currDate = currDate.with(DayOfWeek.FRIDAY);
            updateSelectedDateText();
            viewModel.loadDoctorAppointments(UserHolder.doctor().getId(), getFormattedDate());
        });

        binding.saturdayButton.setOnClickListener(v ->
        {
            currDate = currDate.with(DayOfWeek.SATURDAY);
            updateSelectedDateText();
            viewModel.loadDoctorAppointments(UserHolder.doctor().getId(), getFormattedDate());
        });
    }

    private String getFormattedDate()
    {
        return DateFormatter.formatToDate(currDate.getYear(),
                currDate.getMonthValue(),
                currDate.getDayOfMonth());
    }

    private void updateSelectedDateText()
    {
        binding.celectedDateDoctorAppointments.setText(
                DateFormatter.formatToAlphanumeric(currDate.getYear(),
                        currDate.getMonthValue(), currDate.getDayOfMonth()));
    }
}