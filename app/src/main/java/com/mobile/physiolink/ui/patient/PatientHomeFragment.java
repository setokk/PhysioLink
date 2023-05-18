package com.mobile.physiolink.ui.patient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentPatientHomeBinding;


public class PatientHomeFragment extends Fragment
{
    boolean hasAppointment=true;
    Fragment AppointmentFragment = new UpcomingAppointmentFragment();
    Fragment NoAppointmentFragment= new NoUpcomingAppointmentFragment();
    private FragmentPatientHomeBinding binding;

    public PatientHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        binding = FragmentPatientHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        if(hasAppointment){
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.upcomingAppointmentFragmentContainer, AppointmentFragment);
            transaction.commit();
        }
        else{
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.upcomingAppointmentFragmentContainer, NoAppointmentFragment);
            transaction.commit();
        }

    }
}

