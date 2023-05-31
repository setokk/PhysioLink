package com.mobile.physiolink.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.mobile.physiolink.databinding.ActivityPsfBinding;

import com.mobile.physiolink.R;

public class PSFActivity extends AppCompatActivity
{
    private ActivityPsfBinding binding;
    private AppBarConfiguration appBarConfiguration;

    @Override
    public void onCreate(Bundle savedInstanceBundle)
    {
        super.onCreate(savedInstanceBundle);
        binding = ActivityPsfBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /* Setup Navigation with top level destinations */
        NavController navController = Navigation.findNavController(this, R.id.fragmentContainerView);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();

        /* AppBar Configuration */
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.bottomNavPsf, navController);

//        BottomNavigation listener for popping backstack
        binding.bottomNavPsf.setOnItemSelectedListener((item ->
        {
            navController.popBackStack();
            navController.navigate(item.getItemId());
            return true;
        }));
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        NavController navController = Navigation.findNavController(this, R.id.fragmentContainerView);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
