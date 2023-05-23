package com.mobile.physiolink.ui.patient;

import static android.icu.text.ListFormatter.Type.AND;

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

        boolean isMultiline = binding.appointmentCommentsPatient.getLineCount() > 1;
        boolean hasContent = binding.appointmentCommentsPatient.length() > 0;

        //checks if the lines are not more than 1 and makes the arrow disappear, if there is no content it just shows "-"
        if(!isMultiline){
            binding.appointmentDownArrowPatient.setVisibility(View.GONE);
            if(!hasContent){
                binding.appointmentCommentsPatient.setText("-");
            }
        }

        binding.appointmentPatientConstraint.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if(isMultiline){

                    if(binding.appointmentCommentsPatient.getMaxLines() == 1){
                        binding.appointmentCommentsPatient.setMaxLines(Integer.MAX_VALUE);
                        binding.appointmentCommentsPatient.setEllipsize(null);
                        binding.appointmentDownArrowPatient.setImageResource(R.drawable.baseline_arrow_drop_up_purple);
                    }
                    else {
                        binding.appointmentCommentsPatient.setMaxLines(1);
                        binding.appointmentCommentsPatient.setEllipsize(TextUtils.TruncateAt.END);
                        binding.appointmentDownArrowPatient.setImageResource(R.drawable.baseline_arrow_drop_down_purple);
                    }
                }
            }
        });
    }
}