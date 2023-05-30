package com.mobile.physiolink.ui.patient;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.physiolink.model.user.singleton.UserHolder;
import com.mobile.physiolink.ui.decoration.DecorationSpacingItem;
import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentPatientHistoryBinding;
import com.mobile.physiolink.ui.patient.adapter.AdapterForHistoryPatient;
import com.mobile.physiolink.ui.patient.viewmodel.PatientHistoryViewModel;

public class PatientHistoryFragment extends Fragment
{
    private FragmentPatientHistoryBinding binding;

    private AdapterForHistoryPatient adapter;
    private PatientHistoryViewModel viewModel;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        /* On back button pressed, Go back to home fragment */
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true)
        {
            @Override
            public void handleOnBackPressed()
            {
                NavController navController = Navigation.findNavController(getActivity(), R.id.containerPatient);
                navController.navigate(R.id.action_patientHistoryFragment_to_fragmentPatientHome);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        binding = FragmentPatientHistoryBinding.inflate(inflater, container, false);

        adapter = new AdapterForHistoryPatient();

        viewModel = new ViewModelProvider(this).get(PatientHistoryViewModel.class);
        viewModel.getAppointments().observe(getViewLifecycleOwner(), appointments -> {
            adapter.setAppointments(appointments);
        });
        viewModel.getTotalPayment().observe(getViewLifecycleOwner(), totalPayment -> {
            binding.sumCost.setText(new StringBuilder()
                    .append(totalPayment)
                    .append("€").toString());
        });

        binding.searchViewPatientHistory.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        viewModel.loadAppointments(UserHolder.patient().getId());

        binding.historyRecyclerview.addItemDecoration(new DecorationSpacingItem(20));
        binding.historyRecyclerview.setAdapter(adapter);
        binding.historyRecyclerview.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }
}