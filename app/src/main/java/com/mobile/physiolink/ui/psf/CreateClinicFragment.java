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
import com.mobile.physiolink.ConfirmationClinicPopUp;
import com.mobile.physiolink.databinding.FragmentCreateClinicBinding;

import java.util.ArrayList;


public class CreateClinicFragment extends Fragment
{
    private FragmentCreateClinicBinding binding;

    private boolean input_erros;
    private boolean afm_error;
    private boolean phone_error;

    private boolean code_error;
    private final ArrayList<TextInputLayout> all_inputs_layouts = new ArrayList<>();
    private final ArrayList<TextInputEditText> all_inputs = new ArrayList<>();



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


//        Θα ψάξω να δω αν μπορώ να το κάνω σε μια λούπα αυτό.
//        Μπαίνουν όλα τα input layouts και input edit text σε 2 διαφορετικές λίστες για
//        να προσπελαύνονται εύκολα
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

//        Σε αυτή τη λούπα δημιουργήτε ένας onchange listener για κάθε στοιχείο της λίστας
        for(int j =0; j<all_inputs.size(); j++){
            TextInputEditText current = all_inputs.get(j);
            TextInputLayout current_layout = all_inputs_layouts.get(j);

//            current.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    current.requestFocus();
//                }
//            });
            current.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    if (current.getText().length() == 0 && !current_layout.equals(binding.phonenumberInputLayout)) {
                        current_layout.setError("Το πεδίο πρέπει να συμπληρωθεί!");
                        input_erros = true;
                    } else {
                        current_layout.setError(null);
                        current_layout.setHelperText(null);
                        input_erros = false;
                    }

                    if (current_layout.equals(binding.phonenumberInputLayout)) {
                        if (current.getText().length() != 10 && current.getText().length() != 0) {
                            current_layout.setError("Ο αριθμός πρέπει να έχει 10 ψηφία!");
                            phone_error = true;
                        } else {
                            current_layout.setError(null);
                            phone_error = false;
                        }
                    } else if (current_layout.equals(binding.afmInputLayout)) {
                        if (current.getText().length() != 9) {
                            current_layout.setError("Το ΑΦΜ πρέπει να έχει 9 ψηφία!");
                            afm_error = true;
                        } else {
                            current_layout.setError(null);
                            afm_error = false;
                        }
                    } else if (current_layout.equals(binding.docPasswardInputLayout)) {
                        if (!(current.getText().toString().contains("0") || current.getText().toString().contains("1") || current.getText().toString().contains("2") || current.getText().toString().contains("3") || current.getText().toString().contains("4") || current.getText().toString().contains("5") || current.getText().toString().contains("6") || current.getText().toString().contains("7") || current.getText().toString().contains("8") || current.getText().toString().contains("9"))) {
                            current_layout.setError("Ο κωδικός Πρέπει να περιέζει τουλάχιστον έναν αριθμό");
                            code_error = true;
                        } else {
                            code_error = false;
                            current_layout.setError(null);
                        }
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
                if(input_erros || phone_error || afm_error || code_error){
                    Toast.makeText(getActivity(), "Πρέπει να συμπληρώσετε σωστά όλα τα υποχρεωτικά πεδία", Toast.LENGTH_SHORT).show();
                }
                else{
                    ConfirmationClinicPopUp confirmation = new ConfirmationClinicPopUp(
                            binding.docUsernameInput.getText().toString(),
                            binding.docPasswardInput.getText().toString(),
                            binding.docNameInput.getText().toString(),
                            binding.docSurnameInput.getText().toString(),
                            binding.afmInput.getText().toString(),
                            binding.phonenumberInput.getText().toString(),
                            binding.clinicNameInput.getText().toString(),
                            binding.cityInput.getText().toString(),
                            binding.addressInput.getText().toString());
                    confirmation.show(getActivity().getSupportFragmentManager(), "Confirmation pop up");
                }
            }
        });


        return binding.getRoot();
    }
}