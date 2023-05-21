package com.mobile.physiolink.ui.patient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentRequestAppointmentBinding;
import com.mobile.physiolink.model.user.singleton.UserHolder;
import com.mobile.physiolink.ui.patient.viewmodel.RequestAppointmentViewmodel;
import com.mobile.physiolink.util.DateFormatter;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RequestAppointmentFragment extends Fragment
{
    private List<String> dropdownData;
    private ArrayAdapter<String> adapter;
    private RequestAppointmentViewmodel appointmentViewmodel;

    private FragmentRequestAppointmentBinding binding;

    private ArrayWeekDayFormatter weekDayFormatter;
    private MonthArrayTitleFormatter monthFormatter;

    public RequestAppointmentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentRequestAppointmentBinding.inflate(inflater, container, false);

        appointmentViewmodel = new ViewModelProvider(this).get(RequestAppointmentViewmodel.class);
        appointmentViewmodel.getAvailableHours().observe(getViewLifecycleOwner(), hours ->
        {
            //adapter.setData()
            Log.i("YESYSEYSEYSEYSEYSE", hours.toString());
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
        dropdownData = Arrays.asList("15:00-16:00", "16:00-17:00", "17:00-18:00","18:00-19:00","19:00-20:00","20:00-21:00","22:00-23:00","23:00-24:00");
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, dropdownData);
        binding.autoCompleteTime.setAdapter(adapter);

        // Handle item selection
        binding.autoCompleteTime.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = adapter.getItem(position);
                // Handle the selected option
            }
        });

        // Init
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTimeInMillis(currentDate.getTimeInMillis());
        String today = DateFormatter.formatToAlphanumeric(currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH) + 1,
                        currentDate.get(Calendar.DAY_OF_MONTH));

        appointmentViewmodel.loadAvailableHours(currentDate.get(Calendar.MONTH) + 1,
                currentDate.get(Calendar.YEAR),
                UserHolder.patient().getDoctorId());

        binding.dateText.setText(today);
        binding.calendarView.setTitleFormatter(monthFormatter);
        binding.calendarView.setWeekDayFormatter(weekDayFormatter);

        binding.calendarView.setOnDateChangedListener((widget, date, selected) ->
        {
            String dateString = DateFormatter.formatToAlphanumeric(date.getYear(),
                    date.getMonth(),
                    date.getDay());
            binding.dateText.setText(dateString);
        });

        binding.calendarView.setOnMonthChangedListener((widget, date) ->
        {
            Log.i("INFOFWOFOWWFOFWO", String.valueOf(date.getMonth()));
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