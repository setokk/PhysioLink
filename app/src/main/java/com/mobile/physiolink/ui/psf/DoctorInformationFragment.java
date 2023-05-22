package com.mobile.physiolink.ui.psf;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentDoctorInformationBinding;

import java.util.ArrayList;

public class DoctorInformationFragment extends Fragment {

    private FragmentDoctorInformationBinding binding;

    private boolean edit;

    private ArrayList<TextInputEditText> all_inputs = new ArrayList<>();

    public DoctorInformationFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDoctorInformationBinding.inflate(inflater, container, false);


        all_inputs.add(binding.afmInput);

        all_inputs.add(binding.phoneInput);

        all_inputs.add(binding.clinicNameInput);

        all_inputs.add(binding.cityInput);

        all_inputs.add(binding.addressInput);

        all_inputs.add(binding.tkInput);

        all_inputs.add(binding.numberInput);

        all_inputs.add(binding.emailInput);

        binding.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edit){
                    binding.editButton.setText("Αποθήκευση Αλλαγών");
                    Toast.makeText(getActivity(), "Μπορείται να επεξεργαστείται τα πεδία.", Toast.LENGTH_SHORT).show();
                    for(int i = 0; i < all_inputs.size(); i++){
                        all_inputs.get(i).setEnabled(true);
                        all_inputs.get(i).setTextColor(getResources().getColor(R.color.black));
                        all_inputs.get(i).setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                    }
                    edit = true;
                }
                else{
                    binding.editButton.setText("Επεξεργασία");
                    for(int i = 0; i < all_inputs.size(); i++){
                        all_inputs.get(i).setEnabled(false);
                        all_inputs.get(i).setTextColor(getResources().getColor(R.color.white));
                        all_inputs.get(i).setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.transparent)));
                    }
                    edit = false;
                }
            }
        });

        return binding.getRoot();
    }
}