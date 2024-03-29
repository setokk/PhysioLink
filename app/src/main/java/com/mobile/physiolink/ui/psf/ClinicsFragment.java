
package com.mobile.physiolink.ui.psf;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.mobile.physiolink.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mobile.physiolink.databinding.FragmentClinicsBinding;
import com.mobile.physiolink.ui.decoration.DecorationSpacingItem;
import com.mobile.physiolink.ui.psf.adapter.AdapterForClinics;
import com.mobile.physiolink.ui.psf.viewmodel.ClinicsViewModel;


public class ClinicsFragment extends Fragment
{
    private FragmentClinicsBinding binding;
    private ClinicsViewModel viewModel;
    private AdapterForClinics adapter;


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
        binding = FragmentClinicsBinding.inflate(inflater, container, false);

        adapter = new AdapterForClinics();
        adapter.setOnItemClickListener((doctor) ->
        {
            com.mobile.physiolink.ui.psf.ClinicsFragmentDirections
                    .ActionFragmentClinicsToDoctorInformationFragment
                    action = ClinicsFragmentDirections
                    .actionFragmentClinicsToDoctorInformationFragment(doctor.getId());
            Navigation.findNavController(getActivity(), R.id.fragmentContainerView)
                    .navigate(action);
        });

        viewModel = new ViewModelProvider(this).get(ClinicsViewModel.class);
        viewModel.getDoctors().observe(getViewLifecycleOwner(), doctors ->
        {
            adapter.setDoctors(doctors);
        });

        binding.searchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        binding.customListView.setAdapter(adapter);
        binding.customListView.addItemDecoration(new DecorationSpacingItem(20));
        binding.customListView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        binding.newClinicButton.setOnClickListener(v ->
                Navigation.findNavController(getActivity(), R.id.fragmentContainerView)
                .navigate(R.id.action_phisiotherpeftiriaFragment_to_createPhisiotherapeftiriaFragment));

        viewModel.loadDoctors();
    }
}





