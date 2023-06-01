package com.mobile.physiolink.ui.doctor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentDoctorPatientsBinding;
import com.mobile.physiolink.model.user.singleton.UserHolder;
import com.mobile.physiolink.ui.decoration.DecorationSpacingItem;
import com.mobile.physiolink.ui.doctor.adapter.AdapterForPatients;
import com.mobile.physiolink.ui.doctor.viewmodel.DoctorPatientsViewModel;

public class DoctorPatientsFragment extends Fragment
{
    private FragmentDoctorPatientsBinding binding;
    private DoctorPatientsViewModel viewModel;
    private AdapterForPatients adapter;

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
                NavController navController = Navigation.findNavController(getActivity(), R.id.container);
                navController.navigate(R.id.action_doctorPatientsFragment_to_doctorHomeFragment);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        binding = FragmentDoctorPatientsBinding.inflate(inflater, container, false);

        adapter = new AdapterForPatients();
        adapter.setOnItemClickListener(patient ->
        {
            com.mobile.physiolink.ui.doctor.DoctorPatientsFragmentDirections.ActionDoctorPatientsFragmentToDoctorPatientHistoryFragment
                    action = DoctorPatientsFragmentDirections
                    .actionDoctorPatientsFragmentToDoctorPatientHistoryFragment(patient.getId());
            Navigation.findNavController(getActivity(), R.id.container).navigate(action);
        });

        viewModel = new ViewModelProvider(this).get(DoctorPatientsViewModel.class);
        viewModel.getDoctorPatients().observe(getViewLifecycleOwner(), patients ->
        {
            adapter.setPatients(patients);
        });

        binding.searchViewPatientsDoctor.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        DecorationSpacingItem itemDecoration = new DecorationSpacingItem(40); // 20px spacing
        binding.patientsListDoctor.addItemDecoration(itemDecoration);

        binding.patientsListDoctor.setAdapter(adapter);
        binding.patientsListDoctor.setLayoutManager(new LinearLayoutManager(this.getContext()));

        viewModel.loadDoctorPatients(UserHolder.doctor().getId());

        binding.newPatientBtn.setOnClickListener(v ->
                Navigation.findNavController(getActivity(), R.id.container)
                        .navigate(R.id.action_doctorPatientsFragment_to_doctorNewPatientFragment));
    }
}