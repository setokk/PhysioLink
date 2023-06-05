package com.mobile.physiolink.ui.doctor;

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
import com.mobile.physiolink.databinding.FragmentDoctorSettingsBinding;
import com.mobile.physiolink.util.FileManager;


public class DoctorSettingsFragment extends Fragment {
    private FragmentDoctorSettingsBinding binding;

    public DoctorSettingsFragment() {}

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
        binding = FragmentDoctorSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        binding.aboutUsSettingsDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.psf.org.gr/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        binding.easterEggDoc.setOnClickListener(v -> {
            if(binding.easterEggDoc.getContentDescription().equals("pamak")){
                binding.easterEggDoc.setContentDescription("pamakara");
                binding.easterEggDoc.setImageResource(R.drawable.easter_egg);
                Animation easterEgg = AnimationUtils.loadAnimation(getContext(), R.anim.easter_egg_animation);
                binding.easterEggDoc.startAnimation(easterEgg);
            }else{
                binding.easterEggDoc.setContentDescription("pamak");
                Animation easterEggBack = AnimationUtils.loadAnimation(getContext(), R.anim.easter_egg_back_animation);
                easterEggBack.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        binding.easterEggDoc.setImageResource(R.color.transparent);}
                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });
                binding.easterEggDoc.startAnimation(easterEggBack);
            }
        });

        binding.changePasswordSettingsDoc.setOnClickListener(v ->
                Navigation.findNavController(getActivity(), R.id.container)
                        .navigate(R.id.action_doctorSettingsFragment_to_doctorChangePassword));

        binding.logOutSettingsDoc.setOnClickListener(v ->
        {
            FileManager.deleteUserObj("user.ser");

            getActivity().finish();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });
    }
}

