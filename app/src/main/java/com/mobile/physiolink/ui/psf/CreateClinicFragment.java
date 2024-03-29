package com.mobile.physiolink.ui.psf;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import com.mobile.physiolink.databinding.FragmentCreateClinicBinding;
import com.mobile.physiolink.service.api.error.Error;
import com.mobile.physiolink.service.dao.DoctorDAO;
import com.mobile.physiolink.service.schemas.DoctorSchema;
import com.mobile.physiolink.ui.popup.ConfirmationPopUp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class CreateClinicFragment extends Fragment
{
    private FragmentCreateClinicBinding binding;

    private boolean input_erros;
    private boolean afm_error;
    private boolean phone_error;

    private boolean code_error;

    private boolean address_error;

    private boolean postalCodeError;
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

        all_inputs_layouts.add(binding.emailInputLayout);
        all_inputs.add(binding.emailInput);

        all_inputs_layouts.add(binding.clinicNameInputLayout);
        all_inputs.add(binding.clinicNameInput);

        all_inputs_layouts.add(binding.cityInputLayout);
        all_inputs.add(binding.cityInput);

        all_inputs_layouts.add(binding.addressInputLayout);
        all_inputs.add(binding.addressInput);

        all_inputs_layouts.add(binding.tkClinicInputLayout);
        all_inputs.add(binding.tkClinicInput);

//        Σε αυτή τη λούπα δημιουργήτε ένας onchange listener για κάθε στοιχείο της λίστας
        for(int j =0; j<all_inputs.size(); j++){
            TextInputEditText current = all_inputs.get(j);
            TextInputLayout current_layout = all_inputs_layouts.get(j);

            String passwordPattern = "(?=.*\\d)";
            Pattern PasswordRegex = Pattern.compile(passwordPattern);

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

                    if (current.getText().length() == 0) {
                        current_layout.setError("Το πεδίο πρέπει να συμπληρωθεί!");
                        input_erros = true;
                    } else {
                        current_layout.setError(null);
                        current_layout.setHelperText(null);
                        input_erros = false;
                    }

                    if (current_layout.equals(binding.phonenumberInputLayout)) {
                        if (current.getText().length() != 10) {
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
                        Matcher matcher = PasswordRegex.matcher(binding.docPasswardInput.getText().toString());
                        if(!matcher.find()){
                            current_layout.setError("Ο κωδικός πρέπει να περιέχει τουλάχιστον έναν αριθμό!");
                            code_error = true;
                        } else{
                            code_error = false;
                            current_layout.setError(null);
                        }
                    } else if (current_layout.equals(binding.addressInputLayout)){
                        if(current.getText().toString().length() == 0)
                        {
                           current_layout.setError("Η Διεύθυνση δεν μπορεί να είναι κενή");
                           address_error = true;
                        } else {
                            address_error = false;
                            current_layout.setError(null);
                        }
                    } else if (current_layout.equals(binding.tkClinicInputLayout)){
                        if (current.getText().length() != 5) {
                            current_layout.setError("Ο ταχυδρομικός κώδικας πρέπει να έχει 5 ψηφία!");
                            postalCodeError = true;
                        } else {
                            current_layout.setError(null);
                            postalCodeError = false;
                        }
                    }
                }
                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }

        binding.saveButton.setOnClickListener(view ->
        {
            for(int i = 0; i< all_inputs.size(); i++){
                if(all_inputs.get(i).getText().length() == 0 ){
                    all_inputs_layouts.get(i).setError("Το πεδίο πρέπει να συμπληρωθεί!");
                    input_erros = true;
                }
            }
            if(binding.tkClinicInput.getText().toString().startsWith("0")){
                binding.tkClinicInputLayout.setError("Ο ταχυδρομικός κώδικας δεν είναι έγκυρος!");
                postalCodeError = true;
            }
            if(input_erros || phone_error || afm_error || code_error || address_error || postalCodeError){
                Toast.makeText(getActivity(), "Πρέπει να συμπληρώσετε σωστά όλα τα υποχρεωτικά πεδία", Toast.LENGTH_SHORT).show();
            }
            else{
                ConfirmationPopUp confirmation = new ConfirmationPopUp("Αποθήκευση",
                        "Είστε σίγουρος για την επιλογή σας;",
                        "Ναι", "Οχι");
                confirmation.setPositiveOnClick((dialog, which) ->
                {
                    DoctorSchema schema = new DoctorSchema(binding.docUsernameInput.getText().toString(),
                            binding.docPasswardInput.getText().toString(),
                            binding.docNameInput.getText().toString(),
                            binding.docSurnameInput.getText().toString(),
                            binding.emailInput.getText().toString(),
                            binding.phonenumberInput.getText().toString(),
                            binding.afmInput.getText().toString(),
                            binding.cityInput.getText().toString(),
                            binding.addressInput.getText().toString(),
                            binding.tkClinicInput.getText().toString(),
                            binding.clinicNameInput.getText().toString());
                    DoctorDAO.getInstance().create(schema, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            call.cancel();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String res = response.body().string();
                            getActivity().runOnUiThread(() ->
                            {
                                if (res.contains(Error.RESOURCE_EXISTS))
                                {
                                    Toast.makeText(getActivity(), "Υπάρχει ήδη χρήστης με το ίδιο username",
                                            Toast.LENGTH_LONG).show();
                                    return;
                                }

                                Toast.makeText(getActivity(), "Εγινε αποθήκευση Φυσιοθεραπευτηρίου!",
                                        Toast.LENGTH_SHORT).show();
                                Navigation.findNavController(getActivity(), R.id.fragmentContainerView)
                                        .navigate(R.id.action_fragment_create_clinic_to_fragment_clinics);
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
        });


        return binding.getRoot();
    }
}