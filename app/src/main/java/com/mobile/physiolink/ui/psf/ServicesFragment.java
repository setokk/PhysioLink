package com.mobile.physiolink.ui.psf;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentServicesBinding;
import com.mobile.physiolink.ui.decoration.DecorationSpacingItem;
import com.mobile.physiolink.ui.psf.adapter.AdapterForServices;
import com.mobile.physiolink.ui.psf.viewmodel.ServicesViewModel;


public class ServicesFragment extends Fragment
{
    private FragmentServicesBinding binding;
    private ServicesViewModel viewModel;
    private AdapterForServices adapter;


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
        binding = FragmentServicesBinding.inflate(inflater, container, false);

        adapter = new AdapterForServices();

        viewModel = new ViewModelProvider(this).get(ServicesViewModel.class);
        viewModel.getServices().observe(getViewLifecycleOwner(), services ->
        {
            adapter.setServices(services);
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        DecorationSpacingItem itemDecoration = new DecorationSpacingItem(20); // 20px spacing
        binding.customListViewParoxes.addItemDecoration(itemDecoration);

        binding.customListViewParoxes.setAdapter(adapter);
        binding.customListViewParoxes.setLayoutManager(new LinearLayoutManager(this.getContext()));

        binding.newParoxesButton.setOnClickListener(v ->
                Navigation.findNavController(getActivity(), R.id.fragmentContainerView)
                .navigate(R.id.action_parohesFragment_to_createParoxesFragment));

        viewModel.loadServices();
    }
}
