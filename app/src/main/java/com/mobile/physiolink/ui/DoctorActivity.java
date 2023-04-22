package com.mobile.physiolink.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.ActivityDoctorBinding;
import com.mobile.physiolink.ui.doctor.Appointments_Fragment;
import com.mobile.physiolink.ui.doctor.DoctorHomeFragment;
import com.mobile.physiolink.ui.doctor.DoctorPatientsFragment;
import com.mobile.physiolink.ui.doctor.DoctorServicesFragment;

public class DoctorActivity extends AppCompatActivity
{
    private ActivityDoctorBinding binding;

    private final DoctorHomeFragment doctorHomeFragment = new DoctorHomeFragment();
    private final Appointments_Fragment appointmentsFragment = new Appointments_Fragment();
    private final DoctorServicesFragment doctorServicesFragment = new DoctorServicesFragment();
    private final DoctorPatientsFragment doctorPatientsFragment = new DoctorPatientsFragment();

    @Override
    public void onCreate(Bundle savedInstanceBundle)
    {
        super.onCreate(savedInstanceBundle);
        binding = ActivityDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /* Home Fragment */
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new DoctorHomeFragment())
                .commit();

        binding.bottomNavigation.setOnItemSelectedListener(item ->
        {
            switch (item.getItemId())
            {
                case R.id.home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, doctorHomeFragment).commit();
                    return true;
                case R.id.appointment:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, appointmentsFragment).commit();
                    return true;
                case R.id.services:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, doctorServicesFragment).commit();
                    return true;
                case R.id.patients:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, doctorPatientsFragment).commit();
                    return true;
            }

            return false;
        });
    }
}
