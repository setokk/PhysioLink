package com.mobile.physiolink.ui;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
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


        /*ActionBar*/
        getSupportActionBar().setTitle("PhysioLink");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.physiolink_logo_circle_small);

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.doc_top, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.docSettings) {
            NavController navController = Navigation.findNavController(this, R.id.container);
            navController.navigate(R.id.doctorSettingsFragment);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
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