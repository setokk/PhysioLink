package com.mobile.physiolink.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentEpilogiImerominiasScreenBinding;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Epilogi_Imerominias_Screen extends Fragment {

    CalendarView calendarView;
    TextView myDate;
    private FragmentEpilogiImerominiasScreenBinding binding;

    public Epilogi_Imerominias_Screen() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEpilogiImerominiasScreenBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        calendarView = view.findViewById(R.id.calendarView);
        myDate = view.findViewById(R.id.dateText);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = (month + 1) + "/" + dayOfMonth + "/" + year;
                myDate.setText(date);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}