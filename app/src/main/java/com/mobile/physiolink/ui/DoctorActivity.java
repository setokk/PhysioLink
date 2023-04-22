package com.mobile.physiolink.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.ActivityDoctorBinding;
import com.mobile.physiolink.ui.doctor.Appointments_Fragment;
import com.mobile.physiolink.ui.doctor.DoctorHomeFragment;
import com.mobile.physiolink.ui.doctor.DoctorPatientsFragment;
import com.mobile.physiolink.ui.doctor.DoctorServicesFragment;

public class DoctorActivity extends AppCompatActivity
{
    BottomNavigationView bottomNavigationView;
    DoctorHomeFragment doctorHomeFragment = new DoctorHomeFragment();
    Appointments_Fragment doctorAppointmentsFragment = new Appointments_Fragment();
    DoctorServicesFragment doctorServicesFragment = new DoctorServicesFragment();
    DoctorPatientsFragment doctorPatientsFragment = new DoctorPatientsFragment();

    private ActivityDoctorBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceBundle)
    {
        super.onCreate(savedInstanceBundle);
        binding = ActivityDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bottomNavigationView = findViewById( R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,doctorHomeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,doctorHomeFragment).commit();
                        return true;
                    case R.id.appointment:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,doctorAppointmentsFragment).commit();
                        return true;
                    case R.id.services:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,doctorServicesFragment).commit();
                        return true;
                    case R.id.patients:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,doctorPatientsFragment).commit();
                        return true;
                }

                return false;
            }
        });

    }
}
