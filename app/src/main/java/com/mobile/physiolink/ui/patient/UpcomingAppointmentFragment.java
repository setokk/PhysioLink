package com.mobile.physiolink.ui.patient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentUpcomingAppointmentBinding;
import com.mobile.physiolink.model.appointment.Appointment;
import com.mobile.physiolink.util.date.TimeFormatter;

public class UpcomingAppointmentFragment extends Fragment {

    private FragmentUpcomingAppointmentBinding binding;

    private Appointment appointment;

    public UpcomingAppointmentFragment(Appointment appointment)
    {
        // Required empty public constructor
        this.appointment = appointment;
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
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.appointmentDatePatient.setText(String.format("%s, ", appointment.getDate()
                .replace('-', '/')));
        binding.appointmentTimePatient.setText(TimeFormatter.formatToPM_AM(appointment.getHour()
                .substring(0, 2).replace(":", "")));
        binding.appointmentAdressPatient.setText(String.format("%s,", appointment.getDocAddress()));
        binding.appointmentCityPatient.setText(String.format("%s,", appointment.getDocCity()));
        binding.appointmentPostalCodePatient.setText(appointment.getDocPostalCode());
        binding.appointmentCommentsPatient.setText(appointment.getMessage());

        if (appointment.isConfirmed())
        {
            binding.isConfirmedText.setText("Επιβεβαιωμένο");
            binding.isConfirmedText.setTextColor(getResources()
                    .getColor(R.color.success));
        }
        else
        {
            binding.isConfirmedText.setText("Σε εκκρεμότητα");
        }

        boolean hasContent = binding.appointmentCommentsPatient.length() > 0;
        if (!hasContent) {
            binding.appointmentCommentsPatient.setText("-");
        }

        binding.appointmentPatientConstraint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.appointmentCommentsPatient.getMaxLines() == 1) {
                    binding.appointmentCommentsPatient.setMaxLines(Integer.MAX_VALUE);
                } else {
                    binding.appointmentCommentsPatient.setMaxLines(1);
                    binding.appointmentCommentsPatient.setEllipsize(TextUtils.TruncateAt.END);
                }
            }
        });
    }
}