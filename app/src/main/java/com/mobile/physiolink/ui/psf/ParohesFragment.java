package com.mobile.physiolink.ui.psf;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentParohesBinding;


public class ParohesFragment extends Fragment {

    private FragmentParohesBinding binding;

    public ParohesFragment() {
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
        binding = FragmentParohesBinding.inflate(inflater, container, false);

        //create parohes button
        binding.paroxesButton.setOnClickListener((v)->
        {
            Navigation.findNavController(getActivity(), R.id.fragmentContainerView)
                    .navigate(R.id.action_parohesFragment_to_createParoxesFragment);
        });

        return binding.getRoot();
    }
}