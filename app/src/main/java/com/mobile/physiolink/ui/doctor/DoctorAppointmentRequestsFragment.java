package com.mobile.physiolink.ui.doctor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentDoctorAppointmentRequestsBinding;
import com.mobile.physiolink.model.user.singleton.UserHolder;
import com.mobile.physiolink.ui.decoration.DecorationSpacingItem;
import com.mobile.physiolink.ui.doctor.adapter.AdapterForRequests;
import com.mobile.physiolink.ui.doctor.viewmodel.DoctorRequestsViewModel;

public class DoctorAppointmentRequestsFragment extends Fragment
{
    private FragmentDoctorAppointmentRequestsBinding binding;
    private DoctorRequestsViewModel viewModel;
    private AdapterForRequests adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        /* On back button pressed, Go back to home fragment */
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true)
        {
            @Override
            public void handleOnBackPressed()
            {
                NavController navController = Navigation.findNavController(getActivity(), R.id.container);
                navController.navigate(R.id.action_doctorAppointmentRequestsFragment_to_doctorHomeFragment);
            }
        });

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDoctorAppointmentRequestsBinding.inflate(inflater,container,false);

        adapter = new AdapterForRequests(this.requireActivity().getSupportFragmentManager());
        viewModel = new ViewModelProvider(this).get(DoctorRequestsViewModel.class);
        viewModel.getRequestAppointments().observe(getViewLifecycleOwner(), appointments ->
        {
            if (appointments.length == 0)
                binding.noRequestsTextView.setVisibility(View.VISIBLE);
            else
                binding.noRequestsTextView.setVisibility(View.GONE);

            adapter.setAppointments(appointments);
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DecorationSpacingItem spacingItem = new DecorationSpacingItem(20);
        binding.requestListDoctor.addItemDecoration(spacingItem);

        binding.requestListDoctor.setAdapter(adapter);
        binding.requestListDoctor.setLayoutManager(new LinearLayoutManager(this.getContext()));

        viewModel.loadRequestAppointments(UserHolder.doctor().getId());
    }
}
