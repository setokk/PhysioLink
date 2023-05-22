package com.mobile.physiolink.ui.patient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentNoUpcomingAppointmentBinding;

public class NoUpcomingAppointmentFragment extends Fragment
{
    private FragmentNoUpcomingAppointmentBinding binding;

    public NoUpcomingAppointmentFragment()
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
        binding = FragmentNoUpcomingAppointmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        String text = "πατώντας εδώ!";
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new UnderlineSpan(), 0, text.length(), 0);
        binding.makeAppointmentBtnPatient.setText(spannableString);

        binding.makeAppointmentBtnPatient.setOnClickListener(v ->
                Navigation.findNavController(getActivity(),R.id.containerPatient)
                        .navigate(R.id.action_fragmentPatientHome_to_fragmentRequestAppointment));
    }
}