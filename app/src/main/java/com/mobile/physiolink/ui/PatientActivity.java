package com.mobile.physiolink.ui;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.ActivityPatientBinding;
import com.mobile.physiolink.service.notification.NotificationService;

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

        configureNotificationChannel();
        Intent notificationService = new Intent(getApplicationContext(), NotificationService.class);
        startService(notificationService);
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        NavController navController = Navigation.findNavController(this, R.id.containerPatient);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void configureNotificationChannel()
    {
        CharSequence channelName = "Notification Channel";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(NotificationService.CHANNEL_ID,
                channelName,
                importance);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}
