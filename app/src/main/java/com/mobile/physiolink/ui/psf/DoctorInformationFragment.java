package com.mobile.physiolink.ui.psf;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentDoctorInformationBinding;
import com.mobile.physiolink.model.user.Doctor;
import com.mobile.physiolink.service.dao.DoctorDAO;
import com.mobile.physiolink.service.schemas.DoctorSchema;
import com.mobile.physiolink.ui.decoration.DecorationSpacingItem;
import com.mobile.physiolink.ui.popup.ConfirmationPopUp;
import com.mobile.physiolink.ui.psf.adapter.AdapterForServices;
import com.mobile.physiolink.ui.psf.viewmodel.DoctorInformationViewModel;
import com.mobile.physiolink.util.image.ProfileImageProvider;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DoctorInformationFragment extends Fragment {

    private FragmentDoctorInformationBinding binding;
    private DoctorInformationViewModel viewModel;
    private AdapterForServices adapter;
    private String prev_name;
    private String prev_surname;
    private String prev_afm;
    private String prev_phone;
    private String prev_email;
    private String prev_physio_name;
    private String prev_city;
    private String prev_tk;
    private String prev_address;
    private final ArrayList<TextInputEditText> all_inputs = new ArrayList<>();
    private final ArrayList<TextInputLayout> all_inputs_layouts = new ArrayList<>();
    private boolean input_errors;
    private boolean edit;


    public DoctorInformationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDoctorInformationBinding.inflate(inflater, container, false);

        adapter = new AdapterForServices();

        viewModel = new ViewModelProvider(this).get(DoctorInformationViewModel.class);
        viewModel.getDoctor().observe(getViewLifecycleOwner(), doctor ->
        {
            ProfileImageProvider.setImageForUser(binding.profilImage,
                    doctor);
            binding.nameInput.setText(doctor.getName());
            binding.surnameInput.setText(doctor.getSurname());
            binding.emailInput.setText(doctor.getEmail());
            binding.phoneInput.setText(doctor.getPhoneNumber());
            binding.cityInput.setText(doctor.getCity());
            binding.addressInput.setText(doctor.getAddress());
            binding.tkInput.setText(doctor.getPostalCode());
            binding.afmInput.setText(doctor.getAfm());
            binding.clinicNameInput.setText(doctor.getPhysioName());

            populatePrevTexts();
        });
        viewModel.getDoctorServices().observe(getViewLifecycleOwner(), services ->
        {
            adapter.setServices(services);
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        long doctorId = DoctorInformationFragmentArgs.fromBundle(getArguments()).getDoctorId();
        viewModel.loadDoctor(doctorId);
        viewModel.loadDoctorServices(doctorId);

        populateAllInputs();

        binding.servicesRecyclerView.addItemDecoration(new DecorationSpacingItem(20)); // 20px spacing
        binding.servicesRecyclerView.setAdapter(adapter);
        binding.servicesRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        binding.editButton.setOnClickListener(v ->
        {
            if(!edit)
            {
                Toast.makeText(getActivity(), "Μπορείτε να επεξεργαστείτε τα πεδία.", Toast.LENGTH_SHORT).show();
                edit = true;
                for(int i = 0; i < all_inputs.size(); i++) {
                    all_inputs.get(i).setEnabled(true);
                    all_inputs.get(i).setTextColor(getResources().getColor(R.color.black));
                    all_inputs.get(i).setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                }
                    for(int j = 0; j < all_inputs.size(); j++)
                    {
                        TextInputEditText current = all_inputs.get(j);
                        TextInputLayout current_layout = all_inputs_layouts.get(j);

                        current.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                if (current.getText().length() == 0) {
                                    input_errors = true;
                                } else {
                                    current_layout.setError(null);
                                    input_errors = false;
                                }
                            }
                            @Override
                            public void afterTextChanged(Editable editable) {}
                        });
                    }
                    binding.editButton.setText("Τέλος Επεξεργασίας");
            }
            else
            {
                for(int i = 0; i< all_inputs.size(); i++){
                    if(all_inputs.get(i).getText().length() == 0){
                        input_errors = true;
                    }
                }
                if(input_errors){
                    Toast.makeText(getActivity(), "Πρέπει να συμπληρώσετε σωστά όλα τα υποχρεωτικά πεδία", Toast.LENGTH_SHORT).show();
                }

                ConfirmationPopUp confirmation = new ConfirmationPopUp("Αποθήκευση αλλαγών",
                        "Είστε σίγουρος/η για τις αλλαγές σας;", "Ναι", "Όχι");
                confirmation.setNegativeOnClick(((dialog, which) ->
                {
                    Toast.makeText(getActivity(), "Δεν έγινε αποθήκευση!", Toast.LENGTH_SHORT).show();

                    binding.editButton.setText("Επεξεργασία");
                    edit = false;

                    for(int k = 0; k < all_inputs.size(); k++) {
                        all_inputs.get(k).setEnabled(false);
                        all_inputs.get(k).setTextColor(getResources().getColor(R.color.white));
                        all_inputs.get(k).setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.transparent)));
                    }

                    binding.nameInput.setText(prev_name);
                    binding.surnameInput.setText(prev_surname);
                    binding.afmInput.setText(prev_afm);
                    binding.phoneInput.setText(prev_phone);
                    binding.emailInput.setText(prev_email);
                    binding.clinicNameInput.setText(prev_physio_name);
                    binding.cityInput.setText(prev_city);
                    binding.tkInput.setText(prev_tk);
                    binding.addressInput.setText(prev_address);

                }));
                confirmation.setPositiveOnClick(((dialog, which) ->
                {
                    Doctor doctor = viewModel.getDoctor().getValue();
                    DoctorSchema schema = new DoctorSchema(doctor.getUsername(),
                            "", binding.nameInput.getText().toString(),
                            binding.surnameInput.getText().toString(),
                            binding.emailInput.getText().toString(),
                            binding.phoneInput.getText().toString(),
                            binding.afmInput.getText().toString(),
                            binding.cityInput.getText().toString(),
                            binding.addressInput.getText().toString(),
                            binding.tkInput.getText().toString(),
                            binding.clinicNameInput.getText().toString());
                    FragmentActivity context = getActivity();
                    DoctorDAO.getInstance().update(doctorId, schema, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            call.cancel();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            context.runOnUiThread(() ->
                            {
                                Toast.makeText(context, "Έγινε αποθήκευση των αλλαγών!",
                                        Toast.LENGTH_SHORT).show();

                                for(int i = 0; i < all_inputs.size(); i++){
                                    all_inputs.get(i).setEnabled(false);
                                    all_inputs.get(i).setTextColor(getResources().getColor(R.color.white));
                                    all_inputs.get(i).setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.transparent)));
                                }

                                binding.editButton.setText("Επεξεργασία");
                                edit = false;
                            });
                        }
                    });
                }));

                confirmation.show(getActivity().getSupportFragmentManager(), "Confirmation pop up");
            }
        });
    }

    private void populateAllInputs()
    {
        all_inputs.add(binding.nameInput);
        all_inputs.add(binding.surnameInput);
        all_inputs.add(binding.afmInput);
        all_inputs.add(binding.phoneInput);
        all_inputs.add(binding.clinicNameInput);
        all_inputs.add(binding.cityInput);
        all_inputs.add(binding.addressInput);
        all_inputs.add(binding.tkInput);
        all_inputs.add(binding.emailInput);

        all_inputs_layouts.add(binding.nameInputLayout);
        all_inputs_layouts.add(binding.surnameInputLayout);
        all_inputs_layouts.add(binding.afmInputLayout);
        all_inputs_layouts.add(binding.phoneInputLayout);
        all_inputs_layouts.add(binding.clinicNameInputLayout);
        all_inputs_layouts.add(binding.cityInputLayout);
        all_inputs_layouts.add(binding.addressInputLayout);
        all_inputs_layouts.add(binding.tkInputLayout);
        all_inputs_layouts.add(binding.emailInputLayout);
    }

    private void populatePrevTexts()
    {
        prev_name = binding.nameInput.getText().toString();
        prev_surname = binding.surnameInput.getText().toString();
        prev_afm = binding.afmInput.getText().toString();
        prev_phone = binding.phoneInput.getText().toString();
        prev_email = binding.emailInput.getText().toString();
        prev_physio_name = binding.clinicNameInput.getText().toString();
        prev_city = binding.cityInput.getText().toString();
        prev_tk = binding.tkInput.getText().toString();
        prev_address = binding.addressInput.getText().toString();
    }
}