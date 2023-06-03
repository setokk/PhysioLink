package com.mobile.physiolink.ui.doctor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentDoctorHomeBinding;
import com.mobile.physiolink.model.user.singleton.UserHolder;
import com.mobile.physiolink.ui.decoration.DecorationSpacingItem;
import com.mobile.physiolink.ui.doctor.adapter.AdapterForAppointments;
import com.mobile.physiolink.ui.doctor.viewmodel.DoctorHomeViewModel;
import com.mobile.physiolink.util.image.ProfileImageProvider;


public class DoctorHomeFragment extends Fragment
{
    private FragmentDoctorHomeBinding binding;
    private DoctorHomeViewModel viewModel;
    private AdapterForAppointments adapter;

    public DoctorHomeFragment()
    {
        // Required empty public constructor
    }

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
        binding = FragmentDoctorHomeBinding.inflate(inflater, container, false);

        adapter = new AdapterForAppointments(this.requireActivity().getSupportFragmentManager());
        viewModel = new ViewModelProvider(this).get(DoctorHomeViewModel.class);
        viewModel.getLatestAppointments().observe(getViewLifecycleOwner(), appointments ->
        {
            if (appointments.length == 0)
            {
                binding.noAppointmentsImg.setVisibility(View.VISIBLE);
                binding.noAppointmentTextView.setVisibility(View.VISIBLE);
            } else {
                binding.noAppointmentsImg.setVisibility(View.GONE);
                binding.noAppointmentTextView.setVisibility(View.GONE);
                adapter.setAppointments(appointments);
            }
        });
        viewModel.getNewRequestsCounter().observe(getViewLifecycleOwner(), requestsNum ->
        {
            // TODO: MAKE RED CIRCLE APPEAR ON TOP OF REQUESTS BUTTON
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        binding.textView2.setText(new StringBuilder()
                .append(UserHolder.doctor().getName())
                .append(" ")
                .append(UserHolder.doctor().getSurname())
                .toString());

        ProfileImageProvider.setImageForUser(binding.profileImg,
                UserHolder.doctor(), true);

        DecorationSpacingItem itemDecoration = new DecorationSpacingItem(20); // 20px spacing
        binding.recyclerViewApp.addItemDecoration(itemDecoration);

        binding.recyclerViewApp.setAdapter(adapter);
        binding.recyclerViewApp.setLayoutManager(new LinearLayoutManager(this.getContext()));

        viewModel.loadTodaysLatestAppointments(UserHolder.doctor().getId());
        viewModel.loadNewRequestsCounter(UserHolder.doctor().getId());

        binding.appointRequestBtn.setOnClickListener(v ->
                Navigation.findNavController(getActivity(), R.id.container)
                        .navigate(R.id.doctorAppointmentRequestsFragment));
        binding.profileImg.setOnClickListener(v ->
                Navigation.findNavController(getActivity(), R.id.container)
                        .navigate(R.id.action_doctorHomeFragment_to_doctorProfileFragment));
        binding.all.setOnClickListener(v ->
                Navigation.findNavController(getActivity(), R.id.container)
                        .navigate(R.id.action_doctorHomeFragment_to_doctorAppointmentsFragment));

    }
}