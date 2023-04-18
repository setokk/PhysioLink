package com.mobile.physiolink.ui.psf;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.mobile.physiolink.databinding.FragmentPsfHomeBinding;

public class PsfHomeFragment extends Fragment {

    private FragmentPsfHomeBinding binding;

    public PsfHomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPsfHomeBinding.inflate(inflater, container, false);



        return binding.getRoot();
    }

}