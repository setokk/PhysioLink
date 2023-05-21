package com.mobile.physiolink.ui.patient;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.physiolink.ui.decoration.DecorationSpacingItem;
import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentPatientHistoryBinding;
import com.mobile.physiolink.ui.patient.adapter.AdapterForHistoryPatient;

public class PatientHistoryFragment extends Fragment
{
    private FragmentPatientHistoryBinding binding;

    String h1[],h2[],h3[],h4[],h5[];

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        binding = FragmentPatientHistoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        h1=getResources().getStringArray(R.array.appointmentsService);
        h2=getResources().getStringArray(R.array.servicesPatientHistoryListExampleDate);
        h3=getResources().getStringArray(R.array.timePatientHistory);
        h4=getResources().getStringArray(R.array.servicesListExampleDescription);
        h5=getResources().getStringArray(R.array.paroxesCostExamle);

        binding.historyRecyclerview.addItemDecoration(new DecorationSpacingItem(20));
        binding.historyRecyclerview.setAdapter(new AdapterForHistoryPatient(h2,h3,h4,h1,h5,R.id.historyRecyclerview));
        binding.historyRecyclerview.setLayoutManager(new LinearLayoutManager(this.getContext()));

    }
}