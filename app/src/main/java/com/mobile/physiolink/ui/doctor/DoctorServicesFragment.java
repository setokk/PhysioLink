package com.mobile.physiolink.ui.doctor;
// TODO Na ginei katallhlh allagh tou arxeiou molis ginei h diasyndesh me ta dedomena ths bashs
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.physiolink.R;

import com.mobile.physiolink.databinding.FragmentDoctorServicesBinding;
import com.mobile.physiolink.ui.doctor.adapter.AdapterForDoctorServices;
import com.mobile.physiolink.ui.decoration.DecorationSpacingItem;

public class DoctorServicesFragment extends Fragment
{
    RecyclerView servicesList;
    String s1[],s2[],s3[];
    private FragmentDoctorServicesBinding binding;

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
        binding = FragmentDoctorServicesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        servicesList = view.findViewById(R.id.servicesListDoctor);

        s1=getResources().getStringArray(R.array.patientListExampleName);
        s2=getResources().getStringArray(R.array.servicesListExampleDescription);
        s3=getResources().getStringArray(R.array.servicesListExamplePrices);

        DecorationSpacingItem itemDecoration = new DecorationSpacingItem(20); // 20px spacing
        servicesList.addItemDecoration(itemDecoration);

        AdapterForDoctorServices myAdapter = new AdapterForDoctorServices(s1,s2,s3,R.id.servicesListDoctor);
        servicesList.setAdapter(myAdapter);
        servicesList.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }
}