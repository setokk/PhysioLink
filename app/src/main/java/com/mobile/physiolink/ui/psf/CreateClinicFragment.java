package com.mobile.physiolink.ui.psf;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobile.physiolink.databinding.FragmentCreateClinicBinding;

import java.util.ArrayList;


public class CreateClinicFragment extends Fragment
{
    private FragmentCreateClinicBinding binding;

    private boolean input_erros;
    private ArrayList<TextInputLayout> all_inputs_layouts = new ArrayList<>();
    private ArrayList<TextInputEditText> all_inputs = new ArrayList<>();


    public CreateClinicFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        binding = FragmentCreateClinicBinding.inflate(inflater, container, false);


//        Θα ψάξω να δω αν μπορώ να το κάνω σε μια λούπα αυτό
        all_inputs_layouts.add(binding.docUsernameInputLayout);
        all_inputs.add(binding.docUsernameInput);

        all_inputs_layouts.add(binding.docPasswardInputLayout);
        all_inputs.add(binding.docPasswardInput);

        all_inputs_layouts.add(binding.docNameInputLayout);
        all_inputs.add(binding.docNameInput);

        all_inputs_layouts.add(binding.docSurnameInputLayout);
        all_inputs.add(binding.docSurnameInput);

        all_inputs_layouts.add(binding.afmInputLayout);
        all_inputs.add(binding.afmInput);

        all_inputs_layouts.add(binding.phonenumberInputLayout);
        all_inputs.add(binding.phonenumberInput);

        all_inputs_layouts.add(binding.clinicNameInputLayout);
        all_inputs.add(binding.clinicNameInput);

        all_inputs_layouts.add(binding.cityInputLayout);
        all_inputs.add(binding.cityInput);

        all_inputs_layouts.add(binding.addressInputLayout);
        all_inputs.add(binding.addressInput);

        for(int i =0; i<all_inputs.size(); i++){
            TextInputEditText current = all_inputs.get(i);
            TextInputLayout current_layout = all_inputs_layouts.get(i);
            current.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(current.getText().length() > current_layout.getCounterMaxLength()){
                        current_layout.setError("Έχετε υπερβεί το όριο των χαρακτήρων!");
                        input_erros = true;
                    } else if (current.getText().length() == 0 && !current_layout.equals(binding.phonenumberInputLayout)) {
                        current_layout.setError("Το πεδίο πρέπει να συμπληρωθεί!");
                        input_erros = true;
                    } else{
                        current_layout.setError(null);
                        input_erros = false;
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i< all_inputs.size(); i++){
                    if(all_inputs.get(i).getText().length() == 0 && !all_inputs_layouts.get(i).equals(binding.phonenumberInputLayout)){
                        all_inputs_layouts.get(i).setError("Το πεδίο πρέπει να συμπληρωθεί!");
                        input_erros = true;
                    }
                }
                if(input_erros){
                    Toast.makeText(getActivity(), "Πρέπει να συμπληρώσετε σωστά όλα τα πεδία", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return binding.getRoot();
    }
}