package com.mobile.physiolink.ui.psf;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.mobile.physiolink.LoginActivity;
import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentPsfSettingsBinding;
import com.mobile.physiolink.util.FileManager;

public class PsfSettingsFragment extends Fragment {

    private FragmentPsfSettingsBinding binding;

    public PsfSettingsFragment() {}

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
        binding = FragmentPsfSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        binding.aboutUsSettingsPsf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.psf.org.gr/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });


        binding.easterEggPsf.setOnClickListener(v -> {
            if(binding.easterEggPsf.getContentDescription().equals("pamak")){
                binding.easterEggPsf.setContentDescription("pamakara");
                binding.easterEggPsf.setImageResource(R.drawable.easter_egg);
                Animation easterEgg = AnimationUtils.loadAnimation(getContext(), R.anim.easter_egg_animation);
                binding.easterEggPsf.startAnimation(easterEgg);
            }else{
                binding.easterEggPsf.setContentDescription("pamak");
                Animation easterEggBack = AnimationUtils.loadAnimation(getContext(), R.anim.easter_egg_back_animation);
                easterEggBack.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        binding.easterEggPsf.setImageResource(R.color.transparent);}
                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });
                binding.easterEggPsf.startAnimation(easterEggBack);
            }
        });

        binding.changePasswordSettingsPsf.setOnClickListener(v ->
                Navigation.findNavController(getActivity(), R.id.fragmentContainerView)
                        .navigate(R.id.action_fragmentPsfSettings_to_fragmentPsfChangePassword));

        binding.logOutSettingsPsf.setOnClickListener(v ->
        {
            FileManager.deleteUserObj("user.ser");

            getActivity().finish();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });
    }
}

