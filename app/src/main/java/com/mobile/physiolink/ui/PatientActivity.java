package com.mobile.physiolink.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.ActivityPatientBinding;
import com.mobile.physiolink.databinding.FragmentEpilogiImerominiasScreenBinding;

public class PatientActivity extends AppCompatActivity
{
    //private ActivityPatientBinding binding;
    private FragmentEpilogiImerominiasScreenBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceBundle)
    {
        super.onCreate(savedInstanceBundle);
        binding = FragmentEpilogiImerominiasScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}
