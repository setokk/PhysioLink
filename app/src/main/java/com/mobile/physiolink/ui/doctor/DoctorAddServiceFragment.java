package com.mobile.physiolink.ui.doctor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mobile.physiolink.databinding.FragmentDoctorAddServiceBinding;
import com.mobile.physiolink.model.user.singleton.UserHolder;
import com.mobile.physiolink.ui.decoration.DecorationSpacingItem;
import com.mobile.physiolink.ui.doctor.adapter.AdapterForNewDoctorServices;
import com.mobile.physiolink.ui.doctor.viewmodel.DoctorAddServicesViewModel;
import com.mobile.physiolink.ui.popup.ConfirmationPopUp;


public class DoctorAddServiceFragment extends Fragment {

    private FragmentDoctorAddServiceBinding binding;
    private AdapterForNewDoctorServices adapter;
    private DoctorAddServicesViewModel viewModel;


    public DoctorAddServiceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        adapter = new AdapterForNewDoctorServices();
        viewModel = new ViewModelProvider(this).get(DoctorAddServicesViewModel.class);
        viewModel.getNewDoctorServices().observe(getViewLifecycleOwner(), newDoctorServices ->{
            adapter.setServices(newDoctorServices);
        });

        adapter.setOnLongItemClickListener(service -> {
            ConfirmationPopUp confirmation = new ConfirmationPopUp("Προσθήκη Παροχής",
                    "Είστε σίγουρος πως θέλετε να προσθέσετε αυτή την παροχή στις δικές σας παροχές σας;",
                    "Ναι", "Όχι");
            confirmation.setPositiveOnClick((dialog, which) ->
            {
                // TODO: API CALL
                Toast.makeText(getActivity(), "Έγινε επιτυχώς η προσθήκη!",
                        Toast.LENGTH_SHORT).show();
            });
            confirmation.setNegativeOnClick(((dialog, which) ->
            {
                Toast.makeText(getActivity(), "Δεν έγινε η προσθήκη!",
                        Toast.LENGTH_SHORT).show();
            }));
            confirmation.show(getActivity().getSupportFragmentManager(), "Confirmation pop up");
        });
        // Inflate the layout for this fragment
        binding = FragmentDoctorAddServiceBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        binding.doctorAddServicesList.setAdapter(adapter);
        binding.doctorAddServicesList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        DecorationSpacingItem itemDecoration = new DecorationSpacingItem(20); // 20px spacing
        binding.doctorAddServicesList.addItemDecoration(itemDecoration);

        viewModel.loadNewDoctorServices(UserHolder.doctor().getId());
    }
}