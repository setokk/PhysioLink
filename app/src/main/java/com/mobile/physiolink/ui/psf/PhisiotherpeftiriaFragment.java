package com.mobile.physiolink.ui.psf;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.physiolink.R;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.mobile.physiolink.databinding.FragmentPhisiotherpeftiriaBinding;


public class PhisiotherpeftiriaFragment extends Fragment {


    private FragmentPhisiotherpeftiriaBinding binding;

    public PhisiotherpeftiriaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPhisiotherpeftiriaBinding.inflate(inflater, container, false);

        //create pishiotherapeftiria button
        binding.newPfisiotherapeftirioButton.setOnClickListener((v)->
        {
            Navigation.findNavController(getActivity(), R.id.fragmentContainerView)
                    .navigate(R.id.action_phisiotherpeftiriaFragment_to_createPhisiotherapeftiriaFragment);
        });

        return binding.getRoot();
    }



}