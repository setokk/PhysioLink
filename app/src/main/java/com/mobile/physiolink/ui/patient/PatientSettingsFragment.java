package com.mobile.physiolink.ui.patient;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.mobile.physiolink.LoginActivity;
import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentPatientSettingsBinding;
import com.mobile.physiolink.util.FileManager;

public class PatientSettingsFragment extends Fragment {
    private FragmentPatientSettingsBinding binding;

    public PatientSettingsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        binding = FragmentPatientSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        binding.aboutUsSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.psf.org.gr/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        binding.easterEggPat.setOnClickListener(v -> {
            if(binding.easterEggPat.getContentDescription().equals("pamak")){
                binding.easterEggPat.setContentDescription("pamakara");
                binding.easterEggPat.setImageResource(R.drawable.easter_egg);
                Animation easterEgg = AnimationUtils.loadAnimation(getContext(), R.anim.easter_egg_animation);
                binding.easterEggPat.startAnimation(easterEgg);
            }else{
                binding.easterEggPat.setContentDescription("pamak");
                Animation easterEggBack = AnimationUtils.loadAnimation(getContext(), R.anim.easter_egg_back_animation);
                easterEggBack.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        binding.easterEggPat.setImageResource(R.color.transparent);}
                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });
                binding.easterEggPat.startAnimation(easterEggBack);
            }
        });

        binding.changePasswordSettings.setOnClickListener(v ->
                Navigation.findNavController(getActivity(), R.id.containerPatient)
                        .navigate(R.id.action_fragmentPatientSettings_to_fragmentPatientChangePassword));

        binding.logOutSettings.setOnClickListener(v ->
        {
            FileManager.deleteUserObj("user.ser");

            getActivity().finish();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });
    }
}

