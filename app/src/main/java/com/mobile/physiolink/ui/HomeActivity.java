package com.mobile.physiolink.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.mobile.physiolink.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity
{
    private ActivityHomeBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceBundle)
    {
        super.onCreate(savedInstanceBundle);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
