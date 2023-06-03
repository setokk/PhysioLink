package com.mobile.physiolink.ui.doctor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobile.physiolink.databinding.FragmentChangePasswordBinding;
import com.mobile.physiolink.ui.popup.ConfirmationPopUp;


import java.util.ArrayList;



public class DoctorChangePasswordFragment extends Fragment {

    private FragmentChangePasswordBinding binding;
    private final ArrayList<TextInputLayout> allInputsLayouts = new ArrayList<>();
    private final ArrayList<TextInputEditText> allInputs = new ArrayList<>();
    boolean inputError;
    boolean isSamePassword;

    public DoctorChangePasswordFragment() {}

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
        binding = FragmentChangePasswordBinding.inflate(inflater, container, false);

        allInputsLayouts.add(binding.newChangePassword);
        allInputsLayouts.add(binding.newRepeatChangePassword);

        allInputs.add(binding.newInputChangePassword);
        allInputs.add(binding.newRepeatInputChangePassword);

        binding.saveNewPasswordBtnDoctor.setOnClickListener(view -> {
            for(int i = 0; i< allInputs.size(); i++) {
                if (allInputs.get(i).getText().length() == 0) {
                    allInputsLayouts.get(i).setHelperText("Το πεδίο πρέπει να συμπληρωθεί!");
                    inputError = true;
                } else{
                    inputError = false;
                }
            }
            isSamePassword = binding.newInputChangePassword.getText().toString().equals(binding.newRepeatInputChangePassword.getText().toString());
            if(!isSamePassword && !inputError){
                Toast.makeText(this.getContext(), "Ο κωδικός δεν είναι ίδιος και στα δύο πεδία!", Toast.LENGTH_SHORT).show();
            }

            if(isSamePassword && !inputError){

                ConfirmationPopUp confirmation = new ConfirmationPopUp("Αποθήκευση",
                        "Είστε σίγουρος/η πως θέλετε να αλλάξετε τον κωδικό σας;",
                        "Ναι", "Οχι");
                confirmation.setPositiveOnClick((dialog, which) ->
                {
                    Toast.makeText(this.getContext(), "Έγινε η αλλαγή!",
                            Toast.LENGTH_SHORT).show();
                });
                confirmation.setNegativeOnClick(((dialog, which) ->
                {
                    Toast.makeText(this.getContext(), "Δεν έγινε η αλλαγή!",
                            Toast.LENGTH_SHORT).show();
                }));

                confirmation.show(getActivity().getSupportFragmentManager(), "Confirmation pop up");
            }

        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);

    }
}
