package com.mobile.physiolink.ui.patient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.mobile.physiolink.databinding.FragmentRequestAppointmentBinding;
import com.mobile.physiolink.util.DateFormatter;

import java.util.Arrays;
import java.util.List;

public class RequestAppointmentFragment extends Fragment
{
    private List<String> dropdownData;
    private ArrayAdapter<String> adapter;

    private FragmentRequestAppointmentBinding binding;

    public RequestAppointmentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentRequestAppointmentBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {super.onCreate(savedInstanceState);}
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
        binding.calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) ->
        {
            String date = DateFormatter.formatToAlphanumeric(year, month + 1, dayOfMonth);
            binding.dateText.setText(date);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}