package com.mobile.physiolink.ui.doctor;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentDoctorAppointmentsBinding;
import com.mobile.physiolink.model.user.singleton.UserHolder;
import com.mobile.physiolink.ui.decoration.DecorationSpacingItem;
import com.mobile.physiolink.ui.doctor.adapter.AdapterForAppointments;
import com.mobile.physiolink.ui.doctor.viewmodel.DoctorAppointmentsViewModel;
import com.mobile.physiolink.util.date.DateFormatter;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DoctorAppointmentsFragment extends Fragment
{
    private FragmentDoctorAppointmentsBinding binding;
    private DoctorAppointmentsViewModel viewModel;
    private AdapterForAppointments adapter;

    private Button lastPressedButton = null;
    private LocalDate currDate;

    public DoctorAppointmentsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* On back button pressed, Go back to home fragment */
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true)
        {
            @Override
            public void handleOnBackPressed()
            {
                NavController navController = Navigation.findNavController(getActivity(), R.id.container);
                navController.navigate(R.id.action_doctorAppointmentsFragment_to_doctorHomeFragment);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        binding = FragmentDoctorAppointmentsBinding.inflate(inflater, container, false);

        adapter = new AdapterForAppointments(this.requireActivity().getSupportFragmentManager());
        adapter.setContext(getActivity());
        viewModel = new ViewModelProvider(this).get(DoctorAppointmentsViewModel.class);
        viewModel.getDoctorAppointments().observe(getViewLifecycleOwner(), appointments ->
        {

            adapter.setAppointments(appointments);
            if (appointments.length == 0)
            {
                binding.noAppointmentsDayImg.setVisibility(View.VISIBLE);
                binding.noAppointmentDayTextView.setVisibility(View.VISIBLE);
            } else {
                binding.noAppointmentsDayImg.setVisibility(View.GONE);
                binding.noAppointmentDayTextView.setVisibility(View.GONE);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        ColorStateList selectedColor = ColorStateList.valueOf(Color.parseColor("#CCCCCC"));
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

        switch (currDate.getDayOfWeek()) {
            case MONDAY:
                binding.mondayButton.setBackgroundTintList(selectedColor);
                lastPressedButton = binding.mondayButton;
                break;
            case TUESDAY:
                binding.tuesdayButton.setBackgroundTintList(selectedColor);
                lastPressedButton = binding.tuesdayButton;
                break;
            case WEDNESDAY:
                binding.wednsdayButton.setBackgroundTintList(selectedColor);
                lastPressedButton = binding.wednsdayButton;
                break;
            case THURSDAY:
                binding.thursdayButton.setBackgroundTintList(selectedColor);
                lastPressedButton = binding.thursdayButton;
                break;
            case FRIDAY:
                binding.fridayButton.setBackgroundTintList(selectedColor);
                lastPressedButton = binding.fridayButton;
                break;
            case SATURDAY:
                binding.saturdayButton.setBackgroundTintList(selectedColor);
                lastPressedButton = binding.saturdayButton;
                break;
        }
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
        ColorStateList selectedColor = ColorStateList.valueOf(Color.parseColor("#CCCCCC"));
        ColorStateList defaultColor = ColorStateList.valueOf(Color.WHITE);
        binding.mondayButton.setOnClickListener(v ->
        {
            currDate = currDate.with(DayOfWeek.MONDAY);
            updateSelectedDateText();
            viewModel.loadDoctorAppointments(UserHolder.doctor().getId(), getFormattedDate());
            binding.mondayButton.setBackgroundTintList(selectedColor);
            if (lastPressedButton != null && lastPressedButton != binding.mondayButton) {
                lastPressedButton.setBackgroundTintList(defaultColor);
            }
            lastPressedButton = binding.mondayButton;

        });



        binding.tuesdayButton.setOnClickListener(v ->
        {
            currDate = currDate.with(DayOfWeek.TUESDAY);
            updateSelectedDateText();
            viewModel.loadDoctorAppointments(UserHolder.doctor().getId(), getFormattedDate());
            binding.tuesdayButton.setBackgroundTintList(selectedColor);
            if (lastPressedButton != null && lastPressedButton != binding.tuesdayButton) {
                lastPressedButton.setBackgroundTintList(defaultColor);
            }
            lastPressedButton = binding.tuesdayButton;

        });

        binding.wednsdayButton.setOnClickListener(v ->
        {
            currDate = currDate.with(DayOfWeek.WEDNESDAY);
            updateSelectedDateText();
            viewModel.loadDoctorAppointments(UserHolder.doctor().getId(), getFormattedDate());
            binding.wednsdayButton.setBackgroundTintList(selectedColor);
            if (lastPressedButton != null && lastPressedButton != binding.wednsdayButton) {
                lastPressedButton.setBackgroundTintList(defaultColor);
            }
            lastPressedButton = binding.wednsdayButton;

        });

        binding.thursdayButton.setOnClickListener(v ->
        {
            currDate = currDate.with(DayOfWeek.THURSDAY);
            updateSelectedDateText();
            viewModel.loadDoctorAppointments(UserHolder.doctor().getId(), getFormattedDate());
            binding.thursdayButton.setBackgroundTintList(selectedColor);
            if (lastPressedButton != null && lastPressedButton != binding.thursdayButton) {
                lastPressedButton.setBackgroundTintList(defaultColor);
            }
            lastPressedButton = binding.thursdayButton;

        });

        binding.fridayButton.setOnClickListener(v ->
        {
            currDate = currDate.with(DayOfWeek.FRIDAY);
            updateSelectedDateText();
            viewModel.loadDoctorAppointments(UserHolder.doctor().getId(), getFormattedDate());
            binding.fridayButton.setBackgroundTintList(selectedColor);
            if (lastPressedButton != null && lastPressedButton != binding.fridayButton) {
                lastPressedButton.setBackgroundTintList(defaultColor);
            }
            lastPressedButton = binding.fridayButton;

        });

        binding.saturdayButton.setOnClickListener(v ->
        {
            currDate = currDate.with(DayOfWeek.SATURDAY);
            updateSelectedDateText();
            viewModel.loadDoctorAppointments(UserHolder.doctor().getId(), getFormattedDate());
            binding.saturdayButton.setBackgroundTintList(selectedColor);
            if (lastPressedButton != null && lastPressedButton != binding.saturdayButton) {
                lastPressedButton.setBackgroundTintList(defaultColor);
            }
            lastPressedButton = binding.saturdayButton;

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