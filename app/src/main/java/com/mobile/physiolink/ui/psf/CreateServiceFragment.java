package com.mobile.physiolink.ui.psf;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.mobile.physiolink.databinding.FragmentCreateServiceBinding;
import com.mobile.physiolink.model.service.Service;
import com.mobile.physiolink.service.api.error.Error;
import com.mobile.physiolink.service.dao.ServiceDAO;
import com.mobile.physiolink.ui.popup.ConfirmationPopUp;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class CreateServiceFragment extends Fragment {

    private FragmentCreateServiceBinding binding;
    private boolean input_erros;
    private final ArrayList<TextInputLayout> all_inputs_layouts = new ArrayList<>();
    private final ArrayList<TextInputEditText> all_inputs = new ArrayList<>();
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
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        populateAllInputs();

        // Σε αυτή τη λούπα δημιουργήτε ένας onchange listener για κάθε στοιχείο της λίστας
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
                        current_layout.setHelperText(null);
                        input_erros = false;
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
        binding.saveButton.setOnClickListener(v ->
        {
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
                ConfirmationPopUp confirmation = new ConfirmationPopUp("Αποθήκευση",
                        "Είστε σίγουρος για την επιλογή σας;",
                        "Ναι", "Οχι");
                confirmation.setPositiveOnClick((dialog, which) ->
                {
                    Service service = new Service(binding.serviceIdInput.getText().toString(),
                            binding.serviceNameInput.getText().toString(),
                            binding.serviceDescriptionInput.getText().toString(),
                            Double.parseDouble(binding.serviceCostInput.getText().toString()));
                    try {
                        ServiceDAO.getInstance().create(service, new Callback() {
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
                                        Toast.makeText(getActivity(), "Υπάρχει παροχή με το ίδιο όνομα/κωδικό",
                                                Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    Toast.makeText(getActivity(), "Έγινε αποθήκευση Παροχής!",
                                            Toast.LENGTH_SHORT).show();
                                    Navigation.findNavController(getActivity(), R.id.fragmentContainerView)
                                            .navigate(R.id.action_fragment_create_service_to_fragment_services);
                                });
                            }
                        });
                    } catch (IOException ignored) {}
                });
                confirmation.setNegativeOnClick(((dialog, which) ->
                {
                    Toast.makeText(getActivity(), "Δεν έγινε αποθήκευση Παροχής!",
                            Toast.LENGTH_SHORT).show();
                }));

                confirmation.show(getActivity().getSupportFragmentManager(), "Confirmation pop up");
            }
        });
    }

    private void populateAllInputs()
    {
        all_inputs_layouts.add(binding.serviceCostInputLayout);
        all_inputs.add(binding.serviceCostInput);

        all_inputs_layouts.add(binding.serviceNameInputLayout);
        all_inputs.add(binding.serviceNameInput);

        all_inputs_layouts.add(binding.serviceIdInputLayout);
        all_inputs.add(binding.serviceIdInput);

        all_inputs_layouts.add(binding.serviceDescriptionInputLayout);
        all_inputs.add(binding.serviceDescriptionInput);
    }
}