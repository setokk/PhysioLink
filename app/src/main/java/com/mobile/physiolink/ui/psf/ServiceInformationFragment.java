package com.mobile.physiolink.ui.psf;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobile.physiolink.databinding.FragmentServiceInformationBinding;
import com.mobile.physiolink.ui.popup.ConfirmationPopUp;
import com.mobile.physiolink.ui.psf.viewmodel.ServiceInformationViewModel;

import java.util.ArrayList;


public class ServiceInformationFragment extends Fragment {

    private FragmentServiceInformationBinding binding;
    private ServiceInformationViewModel viewModel;

    private boolean edit = false;
    private boolean input_erros;

    private String prev_name;
    private String prev_price;
    private String prev_code;
    private String prev_description;
    private final ArrayList<TextInputEditText> all_inputs = new ArrayList<>();
    private final ArrayList<TextInputLayout> all_inputs_layouts = new ArrayList<>();

    public ServiceInformationFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentServiceInformationBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(this).get(ServiceInformationViewModel.class);
        viewModel.getService().observe(getViewLifecycleOwner(), service ->
        {
            binding.codeInput.setText(service.getId());
            binding.nameInput.setText(service.getTitle());
            binding.descriptionInput.setText(service.getDescription());
            binding.priceInput.setText(new StringBuilder()
                    .append(service.getPrice())
                    .append("€").toString());
        });

        all_inputs.add(binding.nameInput);
        all_inputs_layouts.add(binding.nameInputLayout);
        prev_name = binding.nameInput.getText().toString();

        all_inputs.add(binding.priceInput);
        all_inputs_layouts.add(binding.priceInputLayout);
        prev_price = binding.priceInput.getText().toString();

        all_inputs.add(binding.codeInput);
        all_inputs_layouts.add(binding.codeInputLayout);
        prev_code = binding.codeInput.getText().toString();

        all_inputs.add(binding.descriptionInput);
        all_inputs_layouts.add(binding.descriptionInputLayout);
        prev_description = binding.descriptionInput.getText().toString();

        binding.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edit){
                    Toast.makeText(getActivity(), "Μπορείται να επεξεργαστείται τα πεδία.", Toast.LENGTH_SHORT).show();
                    edit = true;
                    for(int i = 0; i < all_inputs.size(); i++){
                        all_inputs.get(i).setEnabled(true);
                    }

                    for(int j = 0; j < all_inputs.size(); j++){
                        TextInputEditText current = all_inputs.get(j);
                        TextInputLayout current_layout = all_inputs_layouts.get(j);

                        current.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                if (current.getText().length() == 0) {
                                    current_layout.setError("*");
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

                    binding.editButton.setText("Τέλος Επεξεργασίας");

                }
                else{

                    for(int i = 0; i< all_inputs.size(); i++){
                        if(all_inputs.get(i).getText().length() == 0){
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
                            // TODO: API CALL
                            Toast.makeText(getActivity(), "Έγινε αποθήκευση των αλλαγών!",
                                    Toast.LENGTH_SHORT).show();

                            for(int i = 0; i < all_inputs.size(); i++){
                                all_inputs.get(i).setEnabled(false);
                            }

                            edit = false;
                            binding.editButton.setText("Επεξεργασία");
                        });
                        confirmation.setNegativeOnClick(((dialog, which) ->
                        {
                            Toast.makeText(getActivity(), "Δεν έγινε αποθήκευση!",
                                    Toast.LENGTH_SHORT).show();

                            for(int i = 0; i < all_inputs.size(); i++){
                                all_inputs.get(i).setEnabled(false);
                            }

                            edit = false;
                            binding.editButton.setText("Επεξεργασία");

                            binding.nameInput.setText(prev_name);
                            binding.codeInput.setText(prev_code);
                            binding.priceInput.setText(prev_price);
                            binding.descriptionInput.setText(prev_description);
                        }));

                        confirmation.show(getActivity().getSupportFragmentManager(), "Confirmation pop up");
                    }
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        String serviceId = ServiceInformationFragmentArgs.fromBundle(getArguments()).getServiceId();
        viewModel.loadService(serviceId);
    }
}