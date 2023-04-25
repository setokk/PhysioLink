package com.mobile.physiolink.ui.psf;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentPsfHomeBinding;

public class PsfHomeFragment extends Fragment {

    private FragmentPsfHomeBinding binding;

    public PsfHomeFragment() {

    }

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
        binding = FragmentPsfHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        //phisiotherapeftiria button
        binding.psfImageButton1.setOnClickListener((v)->{
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, new PhisiotherpeftiriaFragment()).commit();});

        //parohes button
        binding.psfImageButton2.setOnClickListener((v)->{
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, new ParohesFragment()).commit();});

    }
}