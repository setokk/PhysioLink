package com.mobile.physiolink.ui;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
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
import com.mobile.physiolink.service.notification.NotificationService;

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

        configureNotificationChannel();
        Intent notificationService = new Intent(getApplicationContext(), NotificationService.class);
        startService(notificationService);
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        NavController navController = Navigation.findNavController(this, R.id.container);
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
