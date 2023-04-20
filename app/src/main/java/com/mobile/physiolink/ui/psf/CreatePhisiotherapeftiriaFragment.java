package com.mobile.physiolink.ui.psf;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentCreatePhisioterapeftiriaBinding;


public class CreatePhisiotherapeftiriaFragment extends Fragment {


    private FragmentCreatePhisioterapeftiriaBinding binding;

    public CreatePhisiotherapeftiriaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCreatePhisioterapeftiriaBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}