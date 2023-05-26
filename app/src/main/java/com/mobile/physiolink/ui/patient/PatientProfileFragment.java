package com.mobile.physiolink.ui.patient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mobile.physiolink.databinding.FragmentPatientProfileBinding;
import com.mobile.physiolink.model.user.singleton.UserHolder;

public class PatientProfileFragment extends Fragment{
    private FragmentPatientProfileBinding binding;

    public PatientProfileFragment()
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
        binding = FragmentPatientProfileBinding.inflate(inflater, container, false);
        binding.profileNamePatient.setText(String.format("%s %s",
                UserHolder.patient().getName(), UserHolder.patient().getSurname()));
        binding.profileUsernamePatient.setText(String.format("%s ",
                UserHolder.patient().getUsername()));
        binding.amkaOutputProfilePatient.setText(String.format("%s ",
                UserHolder.patient().getAmka()));
        binding.emailOutputPatientProfile.setText(String.format("%s ",
                UserHolder.patient().getEmail()));
        binding.cityOutputPatientProfile.setText(String.format("%s ",
                UserHolder.patient().getCity()));
        binding.adressOutputPatientProfile.setText(String.format("%s ",
                UserHolder.patient().getAddress()));
        binding.phoneOutputPatientProfile.setText(String.format("%s ",
                UserHolder.patient().getPhoneNumber()));
        binding.postalCodePatientProfile.setText(String.format("%s ",
                UserHolder.patient().getPostalCode()));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
