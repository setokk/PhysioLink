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
import com.mobile.physiolink.ConfirmationServicePopUp;
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

//        Θα ψάξω να δω αν μπορώ να το κάνω σε μια λούπα αυτό.
//        Μπαίνουν όλα τα input layouts και input edit text σε 2 διαφορετικές λίστες για
//        να προσπελαύνονται εύκολα
        all_inputs_layouts.add(binding.serviceCostInputLayout);
        all_inputs.add(binding.serviceCostInput);

        all_inputs_layouts.add(binding.serviceNameInputLayout);
        all_inputs.add(binding.serviceNameInput);

        all_inputs_layouts.add(binding.serviceIdInputLayout);
        all_inputs.add(binding.serviceIdInput);

        all_inputs_layouts.add(binding.serviceDescriptionInputLayout);
        all_inputs.add(binding.serviceDescriptionInput);

//        Σε αυτή τη λούπα δημιουργήτε ένας onchange listener για κάθε στοιχείο της λίστας
        for(int j =0; j<all_inputs.size(); j++) {
            TextInputEditText current = all_inputs.get(j);
            TextInputLayout current_layout = all_inputs_layouts.get(j);
            current.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (current.getText().length() == 0) {
                        current_layout.setError("Το πεδίο πρέπει να συμπληρωθεί!");
                        input_erros = true;
                    } else {
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
                    if(all_inputs.get(i).getText().length() == 0){
                        all_inputs_layouts.get(i).setError("Το πεδίο πρέπει να συμπληρωθεί!");
                        input_erros = true;
                    }
                    if(all_inputs.get(i).getText().length() > all_inputs_layouts.get(i).getCounterMaxLength()){
                        input_erros = true;
                    }
                }
                if(input_erros){
                    Toast.makeText(getActivity(), "Πρέπει να συμπληρώσετε σωστά όλα τα υποχρεωτικά πεδία", Toast.LENGTH_SHORT).show();
                }
                else{
                    ConfirmationServicePopUp confirmation = new ConfirmationServicePopUp(
                            binding.serviceNameInput.getText().toString(),
                            binding.serviceCostInput.getText().toString(),
                            binding.serviceIdInput.getText().toString(),
                            binding.serviceDescriptionInput.getText().toString());
                    confirmation.show(getActivity().getSupportFragmentManager(), "Confirmation pop up");
                }
            }
        });

        return binding.getRoot();
    }

}