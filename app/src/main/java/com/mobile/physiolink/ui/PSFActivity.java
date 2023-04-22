package com.mobile.physiolink.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mobile.physiolink.databinding.ActivityPsfBinding;

import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import com.mobile.physiolink.R;
import com.mobile.physiolink.ui.psf.PsfHomeFragment;

public class PSFActivity extends AppCompatActivity
{
    private ActivityPsfBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceBundle)
    {
        super.onCreate(savedInstanceBundle);
        binding = ActivityPsfBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavPsf.setOnItemSelectedListener(item ->
        {
            switch (item.getItemId())
            {
                case R.id.home_psf:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new PsfHomeFragment()).commit();
                    return true;
            }

            return false;
        });
    }
}
