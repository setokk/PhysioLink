package com.mobile.physiolink.ui.patient;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.physiolink.databinding.FragmentPatientHomeBinding;

public class PatientHomeFragment extends Fragment
{
    private FragmentPatientHomeBinding binding;

    public PatientHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        binding = FragmentPatientHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}