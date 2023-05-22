package com.mobile.physiolink.ui.patient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentRequestAppointmentBinding;
import com.mobile.physiolink.databinding.ItemListTimeBinding;
import com.mobile.physiolink.model.user.singleton.UserHolder;
import com.mobile.physiolink.ui.patient.adapter.AdapterForAppointmentHour;
import com.mobile.physiolink.ui.patient.viewmodel.RequestAppointmentViewModel;
import com.mobile.physiolink.util.DateFormatter;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class RequestAppointmentFragment extends Fragment
{
    private RequestAppointmentViewModel appointmentViewmodel;
    private AdapterForAppointmentHour adapter;

    private FragmentRequestAppointmentBinding binding;

    private ItemListTimeBinding itemListTimeBinding;

    private ArrayWeekDayFormatter weekDayFormatter;
    private MonthArrayTitleFormatter monthFormatter;

    private Calendar currentDate;
    private int month;


    public RequestAppointmentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentRequestAppointmentBinding.inflate(inflater, container, false);

        currentDate = Calendar.getInstance();
        currentDate.setTimeInMillis(currentDate.getTimeInMillis());

        String monthPrefix = "";
        String dayPrefix = "";
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH) + 1;
        int day = currentDate.get(Calendar.DAY_OF_MONTH);
        if (day <= 9)
            dayPrefix = "0";
        if (month <= 9)
            monthPrefix = "0";

        String initialDate = year + "-" +
                monthPrefix + month + "-" +
                dayPrefix + day;

        appointmentViewmodel = new ViewModelProvider(this).get(RequestAppointmentViewModel.class);
        appointmentViewmodel.getAvailableHours().observe(getViewLifecycleOwner(), hours ->
        {
            if (currentDate.get(Calendar.MONTH) + 1 == month)
                adapter.setHours(hours.getAvailableHoursOfDate(initialDate));
        });

        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        monthFormatter = new MonthArrayTitleFormatter(
            getResources().getTextArray(R.array.greek_months));
        weekDayFormatter = new ArrayWeekDayFormatter(
            getResources().getTextArray(R.array.greek_days));
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        adapter = new AdapterForAppointmentHour(new String[]{"420:00"});
        binding.availableAppointmentHoursList.setAdapter(adapter);
        binding.availableAppointmentHoursList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.hourBtn.setOnClickListener(view1 ->
        {
            if(binding.availableAppointmentHoursList.getVisibility()==View.GONE){
                TransitionManager.beginDelayedTransition(binding.chooseAppointmentHourCardView,new AutoTransition());
                binding.availableAppointmentHoursList.setVisibility(View.VISIBLE);
            }
            else{
                TransitionManager.beginDelayedTransition(binding.chooseAppointmentHourCardView,new AutoTransition());
                binding.availableAppointmentHoursList.setVisibility(View.GONE);
            }
        });

        binding.availableAppointmentHoursList.addOnItemTouchListener(
                new RecyclerItemClickListener(this.getContext(), binding.availableAppointmentHoursList ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                            String text = adapter.getHours()[position];
                            binding.hourBtn.setText(text);
                            binding.availableAppointmentHoursList.setVisibility(View.GONE);
                    }
                    @Override public void onLongItemClick(View view, int position) {

                    }
                })
        );


        // Initialize calendar
        appointmentViewmodel.loadAvailableHours(currentDate.get(Calendar.MONTH) + 1,
                currentDate.get(Calendar.YEAR),
                UserHolder.patient().getDoctorId());

        binding.dateText.setText(DateFormatter.formatToAlphanumeric(currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH) + 1,
                currentDate.get(Calendar.DAY_OF_MONTH)));

        binding.calendarView.setTitleFormatter(monthFormatter);
        binding.calendarView.setWeekDayFormatter(weekDayFormatter);

        binding.calendarView.setOnDateChangedListener((widget, date, selected) ->
        {
            String dateString = DateFormatter.formatToAlphanumeric(date.getYear(),
                    date.getMonth(),
                    date.getDay());

            String monthPrefix = "";
            String dayPrefix = "";
            int year = date.getYear();
            int month = date.getMonth();
            int day = date.getDay();
            if (day <= 9)
                dayPrefix = "0";
            if (month <= 9)
                monthPrefix = "0";

            String selectedDate = year + "-" +
                    monthPrefix + month + "-" +
                    dayPrefix + day;
            adapter.setHours(appointmentViewmodel.getAvailableHoursOfDate(selectedDate));
            binding.dateText.setText(dateString);
        });

        binding.calendarView.state().edit()
                        .setMinimumDate(CalendarDay.from(currentDate.get(Calendar.YEAR),
                                currentDate.get(Calendar.MONTH) + 1,
                                currentDate.get(Calendar.DAY_OF_MONTH)))
                                .commit();
        binding.calendarView.setSelectedDate(CalendarDay.from(currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH) + 1,
                currentDate.get(Calendar.DAY_OF_MONTH)));

        binding.calendarView.setOnMonthChangedListener((widget, date) ->
        {
            this.month = date.getMonth();
            appointmentViewmodel.loadAvailableHours(date.getMonth(),
                    date.getYear(),
                    UserHolder.patient().getDoctorId());
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}