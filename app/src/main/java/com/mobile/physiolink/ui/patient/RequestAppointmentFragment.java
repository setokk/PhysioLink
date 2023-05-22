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
    private String dropdownData[]={"15:00-16:00", "16:00-17:00", "17:00-18:00","18:00-19:00","19:00-20:00","20:00-21:00","22:00-23:00","23:00-24:00"};
    private RequestAppointmentViewModel appointmentViewmodel;

    private FragmentRequestAppointmentBinding binding;
    private ItemListTimeBinding itemListTimeBinding;

    private ArrayWeekDayFormatter weekDayFormatter;
    private MonthArrayTitleFormatter monthFormatter;

    public RequestAppointmentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentRequestAppointmentBinding.inflate(inflater, container, false);

        appointmentViewmodel = new ViewModelProvider(this).get(RequestAppointmentViewModel.class);
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
        AdapterForAppointmentHour adapter =new AdapterForAppointmentHour(dropdownData);
        binding.availableAppointmentHoursList.setAdapter(adapter);
        binding.availableAppointmentHoursList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.hourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.availableAppointmentHoursList.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(binding.chooseAppointmentHourCardView,new AutoTransition());
                    binding.availableAppointmentHoursList.setVisibility(View.VISIBLE);
                }
                else{
                    TransitionManager.beginDelayedTransition(binding.chooseAppointmentHourCardView,new AutoTransition());
                    binding.availableAppointmentHoursList.setVisibility(View.GONE);
                }
            }
        });

        binding.availableAppointmentHoursList.addOnItemTouchListener(
                new RecyclerItemClickListener(this.getContext(), binding.availableAppointmentHoursList ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                            String text=dropdownData[position].toString();
                            binding.hourBtn.setText(text);
                            binding.availableAppointmentHoursList.setVisibility(View.GONE);
                    }
                    @Override public void onLongItemClick(View view, int position) {

                    }
                })
        );


        // Initialize calendar
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTimeInMillis(currentDate.getTimeInMillis());

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