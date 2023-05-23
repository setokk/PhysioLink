package com.mobile.physiolink.ui.patient;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentNoUpcomingAppointmentBinding;
import com.mobile.physiolink.databinding.FragmentUpcomingAppointmentBinding;

public class UpcomingAppointmentFragment extends Fragment {

    private FragmentUpcomingAppointmentBinding binding;

    public UpcomingAppointmentFragment()
    {
        // Required empty public constructor
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
        binding = FragmentUpcomingAppointmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        if (binding.appointmentCommentsPatient.getLineCount()<=1){
            binding.appointmentDownArrowPatient.setVisibility(View.GONE);
            if(binding.appointmentCommentsPatient.getLineCount()==0){
                binding.appointmentCommentsPatient.setText("-");
            }
        }
        binding.appointmentPatientConstraint.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(binding.appointmentCommentsPatient.getLineCount()>1){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        if(binding.appointmentCommentsPatient.isSingleLine()==true){
                            binding.appointmentCommentsPatient.setSingleLine(false);
                            binding.appointmentCommentsPatient.setEllipsize(null);
                            binding.appointmentDownArrowPatient.setBackgroundResource(R.drawable.baseline_arrow_drop_up_purple);
                        }
                        else{
                            binding.appointmentCommentsPatient.setSingleLine(true);
                            binding.appointmentCommentsPatient.setEllipsize(TextUtils.TruncateAt.END);
                            binding.appointmentDownArrowPatient.setBackgroundResource(R.drawable.baseline_arrow_drop_down_purple);
                        }
                    }

                }
            }
        });
    }
}