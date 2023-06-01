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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentDoctorInformationBinding;
import com.mobile.physiolink.model.user.Doctor;
import com.mobile.physiolink.service.dao.DoctorDAO;
import com.mobile.physiolink.service.schemas.DoctorSchema;
import com.mobile.physiolink.ui.decoration.DecorationSpacingItem;
import com.mobile.physiolink.ui.doctor.adapter.AdapterForDoctorServices;
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

    private boolean edit;

    private ArrayList<TextInputEditText> all_inputs = new ArrayList<>();

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
            binding.profilImage.setImageResource(
                    ProfileImageProvider.getProfileImage(doctor.getName()));

            binding.nameTextView.setText(doctor.getName());
            binding.surnameTextView.setText(doctor.getSurname());
            binding.emailInput.setText(doctor.getEmail());
            binding.phoneInput.setText(doctor.getPhoneNumber());
            binding.cityInput.setText(doctor.getCity());
            binding.addressInput.setText(doctor.getAddress());
            binding.tkInput.setText(doctor.getPostalCode());
            binding.afmInput.setText(doctor.getAfm());
            binding.clinicNameInput.setText(doctor.getPhysioName());
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
                binding.editButton.setText("Αποθήκευση Αλλαγών");
                Toast.makeText(getActivity(), "Μπορείται να επεξεργαστείται τα πεδία.", Toast.LENGTH_SHORT).show();
                for(int i = 0; i < all_inputs.size(); i++){
                    all_inputs.get(i).setEnabled(true);
                    all_inputs.get(i).setTextColor(getResources().getColor(R.color.black));
                    all_inputs.get(i).setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                }
                edit = true;
            }
            else
            {
                binding.editButton.setText("Επεξεργασία");
                for(int i = 0; i < all_inputs.size(); i++) {
                    all_inputs.get(i).setEnabled(false);
                    all_inputs.get(i).setTextColor(getResources().getColor(R.color.white));
                    all_inputs.get(i).setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.transparent)));
                    all_inputs.get(i).setText(all_inputs.get(i).getText().toString().trim());
                }

                ConfirmationPopUp confirmation = new ConfirmationPopUp("Αποθήκευση αλλαγών",
                        "Είστε σίγουροι για τις αλλαγές σας;", "ΝΑΙ", "ΌΧΙ");
                confirmation.setNegativeOnClick(((dialog, which) ->
                {
                    Toast.makeText(getActivity(), "Δεν έγιναν οι αλλαγές!", Toast.LENGTH_SHORT).show();
                }));
                confirmation.setPositiveOnClick(((dialog, which) ->
                {
                    Doctor doctor = viewModel.getDoctor().getValue();
                    DoctorSchema schema = new DoctorSchema(doctor.getUsername(),
                            "", doctor.getName(), doctor.getSurname(),
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
                                Toast.makeText(context, "Έγινε επιτυχής ενημέρωση στοιχείων φυσιοθεραπευτηρίου/γιατρού",
                                        Toast.LENGTH_SHORT).show();
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
        all_inputs.add(binding.afmInput);
        all_inputs.add(binding.phoneInput);
        all_inputs.add(binding.clinicNameInput);
        all_inputs.add(binding.cityInput);
        all_inputs.add(binding.addressInput);
        all_inputs.add(binding.tkInput);
        all_inputs.add(binding.emailInput);
    }
}