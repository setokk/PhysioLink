package com.mobile.physiolink.ui.psf;

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
import com.mobile.physiolink.databinding.FragmentPsfChangePasswordBinding;
import com.mobile.physiolink.model.user.singleton.UserHolder;
import com.mobile.physiolink.service.api.API;
import com.mobile.physiolink.service.api.RequestFacade;
import com.mobile.physiolink.ui.popup.ConfirmationPopUp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PsfChangePasswordFragment extends Fragment {

    private FragmentPsfChangePasswordBinding binding;
    private final ArrayList<TextInputLayout> allInputsLayouts = new ArrayList<>();
    private final ArrayList<TextInputEditText> allInputs = new ArrayList<>();
    boolean inputError;
    boolean isSamePassword;

    public PsfChangePasswordFragment() {}

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
        binding = FragmentPsfChangePasswordBinding.inflate(inflater, container, false);

        allInputsLayouts.add(binding.newChangePasswordPsf);
        allInputsLayouts.add(binding.newRepeatChangePasswordPsf);

        allInputs.add(binding.newInputChangePasswordPsf);
        allInputs.add(binding.newRepeatInputChangePasswordPsf);

        binding.saveNewChangePasswordBtnPsf.setOnClickListener(view -> {
            for(int i = 0; i< allInputs.size(); i++) {
                if (allInputs.get(i).getText().length() == 0) {
                    allInputsLayouts.get(i).setHelperText("Το πεδίο πρέπει να συμπληρωθεί!");
                    inputError = true;
                } else{
                    inputError = false;
                }
            }
            isSamePassword = binding.newInputChangePasswordPsf.getText().toString().equals(binding.newRepeatInputChangePasswordPsf.getText().toString());
            if(!isSamePassword && !inputError){
                Toast.makeText(this.getContext(), "Ο κωδικός δεν είναι ίδιος και στα δύο πεδία!", Toast.LENGTH_SHORT).show();
            }

            if(isSamePassword && !inputError){

                ConfirmationPopUp confirmation = new ConfirmationPopUp("Αποθήκευση",
                        "Είστε σίγουρος/η πως θέλετε να αλλάξετε τον κωδικό σας;",
                        "Ναι", "Οχι");
                confirmation.setPositiveOnClick((dialog, which) ->
                {
                    HashMap<String, String> keyValues = new HashMap<>(2);
                    keyValues.put("user_id", String.valueOf(UserHolder.psf().getId()));
                    keyValues.put("password", binding.newRepeatInputChangePasswordPsf.getText().toString());

                    RequestFacade.postRequest(API.CHANGE_PASSWORD, keyValues, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            call.cancel();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            getActivity().runOnUiThread(() ->
                            {
                                Toast.makeText(getContext(), "Έγινε η αλλαγή!",
                                        Toast.LENGTH_SHORT).show();
                            });
                        }
                    });
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);





    }
}