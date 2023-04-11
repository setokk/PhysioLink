package com.mobile.physiolink.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.mobile.physiolink.databinding.ActivityPsfBinding;

public class PSFActivity extends AppCompatActivity
{
    private ActivityPsfBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceBundle)
    {
        super.onCreate(savedInstanceBundle);
        binding = ActivityPsfBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
