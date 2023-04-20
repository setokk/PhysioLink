package com.mobile.physiolink.ui.psf;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentCreateParoxesBinding;


public class CreateParoxesFragment extends Fragment {

    private FragmentCreateParoxesBinding binding;

    public CreateParoxesFragment() {
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
        binding = FragmentCreateParoxesBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}