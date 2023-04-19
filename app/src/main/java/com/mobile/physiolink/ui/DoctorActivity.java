package com.mobile.physiolink.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.mobile.physiolink.databinding.ActivityDoctorBinding;

public class DoctorActivity extends AppCompatActivity
{
    private ActivityDoctorBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceBundle)
    {
        super.onCreate(savedInstanceBundle);
        binding = ActivityDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
