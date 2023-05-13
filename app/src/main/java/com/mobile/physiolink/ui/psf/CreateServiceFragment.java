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
import com.mobile.physiolink.databinding.FragmentCreateServiceBinding;

import java.util.ArrayList;


public class CreateServiceFragment extends Fragment {

    private FragmentCreateServiceBinding binding;
    private boolean input_erros;
    private ArrayList<TextInputLayout> all_inputs_layouts = new ArrayList<>();
    private ArrayList<TextInputEditText> all_inputs = new ArrayList<>();
    public CreateServiceFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCreateServiceBinding.inflate(inflater, container, false);
        all_inputs_layouts.add(binding.serviceCostInputLayout);
        all_inputs.add(binding.serviceIdInput);

        all_inputs_layouts.add(binding.serviceNameInputLayout);
        all_inputs.add(binding.serviceNameInput);

        all_inputs_layouts.add(binding.serviceIdInputLayout);
        all_inputs.add(binding.serviceIdInput);

        all_inputs_layouts.add(binding.serviceDescriptionInputLayout);
        all_inputs.add(binding.serviceDescriptionInput);

        binding.serviceNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(binding.serviceNameInput.getText().length() > 30){
                    binding.serviceNameInputLayout.setError("Έχετε υπερβεί το όριο των χαρακτήρων!");
                    input_erros = true;
                } else if (binding.serviceNameInput.getText().length() == 0) {
                    binding.serviceNameInputLayout.setError("Το πεδίο πρέπει να συμπληρωθεί!");
                    input_erros = true;
                } else{
                    binding.serviceNameInputLayout.setError(null);
                    input_erros = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.serviceIdInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(binding.serviceIdInput.getText().length() > 20){
                    binding.serviceIdInputLayout.setError("Έχετε υπερβεί το όριο των χαρακτήρων!");
                    input_erros = true;
                }else if (binding.serviceIdInput.getText().length() == 0) {
                    binding.serviceIdInputLayout.setError("Το πεδίο πρέπει να συμπληρωθεί!");
                    input_erros = true;
                } else{
                    binding.serviceIdInputLayout.setError(null);
                    input_erros = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.serviceCostInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(binding.serviceCostInput.getText().length() > 10){
                    binding.serviceCostInputLayout.setError("Έχετε υπερβεί το όριο των χαρακτήρων!");
                    input_erros = true;
                }else if (binding.serviceCostInput.getText().length() == 0) {
                    binding.serviceCostInputLayout.setError("Το πεδίο πρέπει να συμπληρωθεί!");
                    input_erros = true;
                }else{
                    binding.serviceCostInputLayout.setError(null);
                    input_erros = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.serviceDescriptionInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(binding.serviceDescriptionInput.getText().length() > 70){
                    binding.serviceDescriptionInputLayout.setError("Έχετε υπερβεί το όριο των χαρακτήρων!");
                    input_erros = true;
                }else if (binding.serviceDescriptionInput.getText().length() == 0) {
                    binding.serviceDescriptionInputLayout.setError("Το πεδίο πρέπει να συμπληρωθεί!");
                    input_erros = true;
                }else{
                    binding.serviceDescriptionInputLayout.setError(null);
                    input_erros = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(input_erros){
                    Toast.makeText(getActivity(), "Πρέπει να συμπληρώσετε σωστά όλα τα πεδία", Toast.LENGTH_SHORT).show();
                } else{

                    for(int i = 0; i< all_inputs.size(); i++){
                        if(all_inputs.get(i).getText().length() == 0){
                            Toast.makeText(getActivity(), "Πρέπει να συμπληρώσετε σωστά όλα τα πεδία", Toast.LENGTH_SHORT).show();
                            all_inputs_layouts.get(i).setError("Το πεδίο πρέπει να συμπληρωθεί!");
                            input_erros = true;
                        }
                    }
                }
            }
        });

        return binding.getRoot();
    }

}