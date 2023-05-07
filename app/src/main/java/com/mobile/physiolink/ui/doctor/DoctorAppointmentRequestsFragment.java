package com.mobile.physiolink.ui.doctor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentDoctorAppointmentRequestsBinding;
import com.mobile.physiolink.ui.doctor.adapter.AdapterForRequests;

public class DoctorAppointmentRequestsFragment extends Fragment {

    RecyclerView requestList;

    String s1[],s2[],s3[],s4[],s5[],s6[],s7[];

    private FragmentDoctorAppointmentRequestsBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {super.onCreate(savedInstanceState);}


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDoctorAppointmentRequestsBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestList =  view.findViewById(R.id.requestListDoctor);

        s1 = getResources().getStringArray(R.array.requestListExampleName);
        s2 = getResources().getStringArray(R.array.requestListExampleSurname);
        s3 = getResources().getStringArray(R.array.requestListExampleAMKA);
        s4 = getResources().getStringArray(R.array.requestListExampleDate);
        s5 = getResources().getStringArray(R.array.requestListExampleTime);
        s6 = getResources().getStringArray(R.array.requestListExampleProblem);
        s7 = getResources().getStringArray(R.array.requestListExampleRequestTime);

        AdapterForRequests adapter = new AdapterForRequests(this,s1,s2,s3,s4,s5,s6,s7);
        requestList.setAdapter(adapter);
        requestList.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }
}
