package com.mobile.physiolink.ui;

import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.mobile.physiolink.databinding.ActivityDoctorBinding;
import com.mobile.physiolink.model.user.Doctor;
import com.mobile.physiolink.model.user.User;

public class DoctorActivity extends AppCompatActivity
{
    private ActivityDoctorBinding binding;
    private Doctor doctor;

    @Override
    public void onCreate(Bundle savedInstanceBundle)
    {
        super.onCreate(savedInstanceBundle);
        binding = ActivityDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /* Get passed user */
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            {
                doctor = bundle.getSerializable("user", Doctor.class);
            }
        }

        System.out.println(doctor.toString());
    }
}
