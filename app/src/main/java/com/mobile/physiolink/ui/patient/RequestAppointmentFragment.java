package com.mobile.physiolink.ui.patient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentRequestAppointmentBinding;
import com.mobile.physiolink.ui.patient.adapter.AdapterForDropdown;
import com.mobile.physiolink.util.DateFormatter;

import java.util.Arrays;
import java.util.List;

public class RequestAppointmentFragment extends Fragment
{
    private AdapterForDropdown adapter;

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
        binding.autoCompleteTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDropdown();
            }
        });

        // Set up the RecyclerView
        adapter = new AdapterForDropdown(R.array.appointmentTime, new AdapterForDropdown.OnItemClickListener() {
            @Override
            public void onItemClick(String item) {
                binding.autoCompleteTime.setText(item);
                hideDropdown();
            }
        }, requireContext());
        binding.dropdownRecyclerView.setAdapter(adapter);
        binding.dropdownRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.dropdownRecyclerView.setVisibility(View.GONE);

        binding.calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) ->
        {
            String date = DateFormatter.formatToAlphanumeric(year, month + 1, dayOfMonth);
            binding.dateText.setText(date);
        });
    }
    private void showDropdown() {
        binding.dropdownRecyclerView.setVisibility(View.VISIBLE);
    }

    private void hideDropdown() {
        binding.dropdownRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}