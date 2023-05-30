package com.mobile.physiolink.ui.doctor;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
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
import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentDoctorNewPatientBinding;
import com.mobile.physiolink.model.user.singleton.UserHolder;
import com.mobile.physiolink.service.api.error.Error;
import com.mobile.physiolink.service.dao.PatientDAO;
import com.mobile.physiolink.service.schemas.PatientSchema;
import com.mobile.physiolink.ui.popup.ConfirmationPopUp;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DoctorNewPatientFragment extends Fragment
{
    private FragmentDoctorNewPatientBinding binding;

    private boolean inputError;
    private boolean phoneError;
    private boolean AmkaError;
    private boolean passwordError;
    private boolean emailError;
    private boolean postalCodeError;
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

        allInputsLayouts.add(binding.patientCityInputLayout);
        allInputs.add(binding.patientCityInput);

        allInputsLayouts.add(binding.patientAddressInputLayout);
        allInputs.add(binding.patientAddressInput);

        allInputsLayouts.add(binding.patientPostalCodeInputLayout);
        allInputs.add(binding.patientPostalCodeInput);

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

            String passwordPattern = "(?=.*\\d)(?=.*[\\{\\.\\}])";
            Pattern PasswordRegex = Pattern.compile(passwordPattern);

            String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
            Pattern emailRegex = Pattern.compile(emailPattern);

//            currentInput.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    currentInput.requestFocus();
//                }
//            });

            currentInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    if (currentInput.getText().length() == 0) {
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
                        Matcher matcher = PasswordRegex.matcher(binding.patientPasswordInput.getText().toString());
                        if(!matcher.find()){
                            currentInputLayout.setError("Ο κωδικός πρέπει να περιέχει τουλάχιστον έναν αριθμό και έναν ειδικό χαρακτήρα '{' ή '.'");
                            passwordError = true;
                        } else{
                            passwordError = false;
                            currentInputLayout.setError(null);
                        }
                    } else if (currentInputLayout.equals(binding.patientEmailInputLayout)) {
                        Matcher matcher = emailRegex.matcher(binding.patientEmailInput.getText().toString());
                        if(!matcher.find()){
                            currentInputLayout.setError("Μη έγκυρο email");
                            emailError = true;
                        }else{
                            emailError = false;
                            currentInputLayout.setError(null);
                        }
                    } else if (currentInputLayout.equals(binding.patientPostalCodeInputLayout)){
                        if(currentInput.getText().toString().startsWith("0")){
                            currentInputLayout.setError("Ο ταχυδρομικός κώδικας δεν είναι έγκυρος!");
                            postalCodeError = true;
                        } else {
                            currentInputLayout.setError(null);
                            postalCodeError = false;
                        }
                        if (currentInput.getText().length() != 5) {
                            currentInputLayout.setError("Ο ταχυδρομικός κώδικας πρέπει να έχει 5 ψηφία!");
                            postalCodeError = true;
                        } else {
                            currentInputLayout.setError(null);
                            postalCodeError = false;
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
                    if(allInputs.get(i).getText().length() == 0){
                        allInputsLayouts.get(i).setError("Το πεδίο πρέπει να συμπληρωθεί!");
                        inputError = true;
                    }
                }
                if(inputError || phoneError || AmkaError || passwordError || emailError || postalCodeError){
                    Toast.makeText(getActivity(), "Πρέπει να συμπληρώσετε σωστά όλα τα υποχρεωτικά πεδία", Toast.LENGTH_SHORT).show();
                }
                else{
                    ConfirmationPopUp confirmation = new ConfirmationPopUp("Αποθήκευση",
                            "Είστε σίγουρος για την επιλογή σας;",
                            "Ναι", "Οχι");
                    confirmation.setPositiveOnClick((dialog, which) ->
                    {

                        PatientSchema schema = new PatientSchema(binding.patientUsernameInput.getText().toString(),
                                binding.patientPasswordInput.getText().toString(),
                                binding.patientNameInput.getText().toString(),
                                binding.patientSurnameInput.getText().toString(),
                                binding.patientEmailInput.getText().toString(),
                                binding.patientPhoneInput.getText().toString(),
                                binding.patientCityInput.getText().toString(),
                                binding.patientAddressInput.getText().toString(),
                                binding.patientPostalCodeInput.getText().toString(),
                                binding.patientAmkaInput.getText().toString(),
                                UserHolder.doctor().getId());

                        FragmentActivity context = getActivity();
                        PatientDAO.getInstance().create(schema, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {call.cancel();}
                            @Override
                            public void onResponse(Call call, Response response) throws IOException
                            {
                                String res = response.body().string();
                                context.runOnUiThread(() ->
                                {
                                    if (res.contains(Error.RESOURCE_EXISTS))
                                    {
                                        Toast.makeText(getActivity(), "Το όνομα χρήστη υπάρχει ήδη",
                                                Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                        Toast.makeText(getActivity(), "Έγινε αποθήκευση Ασθενή!",
                                            Toast.LENGTH_SHORT).show();
                                });
                            }
                        });
                    });
                    confirmation.setNegativeOnClick(((dialog, which) ->
                    {
                        Toast.makeText(getActivity(), "Δεν έγινε αποθήκευση!",
                                Toast.LENGTH_SHORT).show();
                    }));

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