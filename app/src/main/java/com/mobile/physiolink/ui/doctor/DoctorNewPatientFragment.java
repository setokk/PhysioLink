package com.mobile.physiolink.ui.doctor;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobile.physiolink.ConfirmationPatientPopUp;
import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentDoctorNewPatientBinding;

import java.util.ArrayList;

public class DoctorNewPatientFragment extends Fragment
{
    private FragmentDoctorNewPatientBinding binding;

    private boolean inputError;
    private boolean phoneError;
    private boolean AmkaError;
    private final ArrayList<TextInputLayout> allInputsLayouts = new ArrayList<>();
    private final ArrayList<TextInputEditText> allInputs = new ArrayList<>();

    public DoctorNewPatientFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        /* On back button pressed, Go back to patients fragment */
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true)
        {
            @Override
            public void handleOnBackPressed()
            {
                NavController navController = Navigation.findNavController(getActivity(), R.id.container);
                navController.navigate(R.id.action_doctorNewPatientFragment_to_doctorPatientsFragment);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        binding = FragmentDoctorNewPatientBinding.inflate(inflater, container, false);

        allInputsLayouts.add(binding.patientNameInputLayout);
        allInputs.add(binding.patientNameInput);

        allInputsLayouts.add(binding.patientSurnameInputLayout);
        allInputs.add(binding.patientSurnameInput);

        allInputsLayouts.add(binding.patientAmkaInputLayout);
        allInputs.add(binding.patientAmkaInput);

        allInputsLayouts.add(binding.patientPhoneInputLayout);
        allInputs.add(binding.patientPhoneInput);

        allInputsLayouts.add(binding.patientEmailInputLayout);
        allInputs.add(binding.patientEmailInput);

        allInputsLayouts.add(binding.patientUsernameInputLayout);
        allInputs.add(binding.patientUsernameInput);

        allInputsLayouts.add(binding.patientPasswordInputLayout);
        allInputs.add(binding.patientPasswordInput);

        for(int j =0; j<allInputsLayouts.size(); j++) {
            TextInputLayout currentInputLayout = allInputsLayouts.get(j);
            TextInputEditText currentInput = allInputs.get(j);

            currentInput.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentInput.requestFocus();
                }
            });

            currentInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    if (currentInput.getText().length() == 0 && !currentInputLayout.equals(binding.patientPhoneInputLayout)) {
                        currentInputLayout.setError("Το πεδίο πρέπει να συμπληρωθεί!");
                        inputError = true;
                    } else {
                        currentInputLayout.setError(null);
                        currentInputLayout.setHelperText(null);
                        inputError = false;
                    }

                    if (currentInputLayout.equals(binding.patientPhoneInputLayout)) {
                        if (currentInput.getText().length() != 10 && currentInput.getText().length() != 0) {
                            currentInputLayout.setError("Ο αριθμός πρέπει να έχει 10 ψηφία!");
                            phoneError = true;
                        }else {
                            currentInputLayout.setError(null);
                            phoneError = false;
                        }
                    } else if (currentInputLayout.equals(binding.patientAmkaInputLayout)) {
                        if (currentInput.getText().length() != 11) {
                            currentInputLayout.setError("Το ΑΜΚΑ πρέπει να έχει 11 ψηφία!");
                            AmkaError = true;
                        } else {
                            currentInputLayout.setError(null);
                            AmkaError = false;
                        }
                    } else if (currentInputLayout.equals(binding.patientPasswordInputLayout)){
                        if(!(currentInput.getText().toString().contains("0") || currentInput.getText().toString().contains("1") || currentInput.getText().toString().contains("2") || currentInput.getText().toString().contains("3") || currentInput.getText().toString().contains("4") || currentInput.getText().toString().contains("5") || currentInput.getText().toString().contains("6") || currentInput.getText().toString().contains("7") || currentInput.getText().toString().contains("8") || currentInput.getText().toString().contains("9"))){
                            currentInputLayout.setHelperText("Ο κωδικός σας δεν είναι αρκετά ισχυρός");
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }

        binding.saveNewPatientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i< allInputs.size(); i++){
                    if(allInputs.get(i).getText().length() == 0 && !allInputsLayouts.get(i).equals(binding.patientPhoneInputLayout)){
                        allInputsLayouts.get(i).setError("Το πεδίο πρέπει να συμπληρωθεί!");
                        inputError = true;
                    }
                    if(allInputs.get(i).getText().length() > allInputsLayouts.get(i).getCounterMaxLength()){
                        inputError = true;
                    }
                }
                if(inputError || phoneError || AmkaError){
                    Toast.makeText(getActivity(), "Πρέπει να συμπληρώσετε σωστά όλα τα υποχρεωτικά πεδία", Toast.LENGTH_SHORT).show();
                }
                else{
                    ConfirmationPatientPopUp confirmation = new ConfirmationPatientPopUp(
                            binding.patientNameInput.getText().toString(),
                            binding.patientSurnameInput.getText().toString(),
                            binding.patientAddressInput.getText().toString(),
                            binding.patientAmkaInput.getText().toString(),
                            binding.patientPhoneInput.getText().toString(),
                            binding.patientEmailInput.getText().toString(),
                            binding.patientUsernameInput.getText().toString(),
                            binding.patientPasswordInput.getText().toString());
                    confirmation.show(getActivity().getSupportFragmentManager(), "Confirmation pop up");
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
    }
}