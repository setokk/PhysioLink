package com.mobile.physiolink.ui.patient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentPatientDoctorBinding;

public class PatientDoctorFragment extends Fragment
{
    private FragmentPatientDoctorBinding binding;


    public PatientDoctorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        binding = FragmentPatientDoctorBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);


        binding.doctorPatientDownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.doctorPhysioMoreInfoPatientConstraint.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(binding.doctorPhysioInfoPatientCardView, new AutoTransition());
                    binding.doctorPhysioMoreInfoPatientConstraint.setVisibility(View.VISIBLE);
                    binding.doctorPatientDownBtn.setBackgroundResource(R.drawable.baseline_arrow_drop_up_24);
                }
                else{
                    TransitionManager.beginDelayedTransition(binding.doctorPhysioInfoPatientCardView, new AutoTransition());
                    binding.doctorPhysioMoreInfoPatientConstraint.setVisibility(View.GONE);
                    binding.doctorPatientDownBtn.setBackgroundResource(R.drawable.baseline_arrow_drop_down_24);
                }
            }
        });

    }
}