package com.mobile.physiolink.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.mobile.physiolink.databinding.ActivityPsfBinding;

import com.mobile.physiolink.R;
import com.mobile.physiolink.ui.psf.CreateParoxesFragment;
import com.mobile.physiolink.ui.psf.PsfHomeFragment;

public class PSFActivity extends AppCompatActivity
{
    private ActivityPsfBinding binding;

    private final PsfHomeFragment psfHomeFragment = new PsfHomeFragment();
    private final CreateParoxesFragment createParoxesFragment = new CreateParoxesFragment();

    @Override
    public void onCreate(Bundle savedInstanceBundle)
    {
        super.onCreate(savedInstanceBundle);
        binding = ActivityPsfBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, psfHomeFragment)
                .commit();

        /* Bottom Navigation Listener */
        binding.bottomNavPsf.setOnItemSelectedListener(item ->
        {
            switch (item.getItemId())
            {
                case R.id.home_psf:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, psfHomeFragment).commit();
                    return true;
                case R.id.profile_psf:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, createParoxesFragment).commit();
                    return true;

            }

           return false;
        });
    }
}
