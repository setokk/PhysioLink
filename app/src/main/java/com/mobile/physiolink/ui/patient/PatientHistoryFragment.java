package com.mobile.physiolink.ui.patient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.physiolink.HistoryRecyclerAdapter;
import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentPatientHistoryBinding;

public class PatientHistoryFragment extends Fragment
{
    private FragmentPatientHistoryBinding binding;
    String[] history_date, history_time, history_fysio, history_doc,
            history_service, history_price;
    private HistoryRecyclerAdapter adapter;

    public PatientHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPatientHistoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        history_date = getResources().getStringArray(R.array.servicesPatientHistoryListExampleDate);
        history_time = getResources().getStringArray(R.array.requestListExampleTime);
        history_fysio = getResources().getStringArray(R.array.doctorOfficeListExample);
        history_doc = getResources().getStringArray(R.array.doctoNameListExample);
        history_service = getResources().getStringArray(R.array.paroxesNameExample);
        history_price =  getResources().getStringArray(R.array.paroxesCostExamle);

        binding.historyRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new HistoryRecyclerAdapter(history_date,history_time,history_fysio,history_doc,history_service,history_price);
        binding.historyRecyclerview.setAdapter(adapter);
    }
}