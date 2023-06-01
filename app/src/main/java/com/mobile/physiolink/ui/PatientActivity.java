package com.mobile.physiolink.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.ActivityPatientBinding;

public class PatientActivity extends AppCompatActivity
{
    private ActivityPatientBinding binding;
    private AppBarConfiguration appBarConfiguration;

    @Override
    public void onCreate(Bundle savedInstanceBundle)
    {
        super.onCreate(savedInstanceBundle);
        binding = ActivityPatientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /* Setup Navigation with top level destinations */
        NavController navController = Navigation.findNavController(this, R.id.containerPatient);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();

        /* AppBar Configuration */
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.bottomNavigationPatient, navController, false);

        /*ActionBar*/
        getSupportActionBar().setTitle("PhysioLink");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.physiolink_logo);

        /* Pop backstack after every navigation bar click
        binding.bottomNavigationPatient.setOnItemSelectedListener((item) ->
        {
            navController.popBackStack();
            navController.navigate(item.getItemId());
            return true;
        });
        */
        /* Hide the back button from the header
        navController.addOnDestinationChangedListener((oNavController, navDestination, bundle) ->
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        });
        */
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        NavController navController = Navigation.findNavController(this, R.id.containerPatient);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
