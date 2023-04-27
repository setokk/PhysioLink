package com.mobile.physiolink.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.ActivityDoctorBinding;

public class DoctorActivity extends AppCompatActivity
{
    private ActivityDoctorBinding binding;
    private AppBarConfiguration appBarConfiguration;

    @Override
    public void onCreate(Bundle savedInstanceBundle)
    {
        super.onCreate(savedInstanceBundle);
        binding = ActivityDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /* Setup Navigation with top level destinations */
        NavController navController = Navigation.findNavController(this, R.id.container);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();

        /* AppBar Configuration */
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController, false);

        /* Pop backstack after every navigation bar click */
        binding.bottomNavigation.setOnItemSelectedListener((item) ->
        {
            navController.popBackStack();
            navController.navigate(item.getItemId());
            return true;
        });

        /* Hide the back button from the header */
        navController.addOnDestinationChangedListener((oNavController, navDestination, bundle) ->
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        });
    }
}
