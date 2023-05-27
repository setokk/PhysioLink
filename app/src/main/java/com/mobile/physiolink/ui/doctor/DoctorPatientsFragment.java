package com.mobile.physiolink.ui.doctor;
// TODO: Na ginei katallhlh allagh tou arxeiou molis ginei h diasyndesh me ta dedomena ths bashs
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentDoctorPatientsBinding;
import com.mobile.physiolink.ui.doctor.adapter.AdapterForPatients;
import com.mobile.physiolink.ui.decoration.DecorationSpacingItem;

public class DoctorPatientsFragment extends Fragment
{
    private FragmentDoctorPatientsBinding binding;
    private AdapterForPatients adapter;

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
        binding = FragmentDoctorPatientsBinding.inflate(inflater, container, false);

        adapter = new AdapterForPatients();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        DecorationSpacingItem itemDecoration = new DecorationSpacingItem(20); // 20px spacing
        binding.patientsListDoctor.addItemDecoration(itemDecoration);

        binding.patientsListDoctor.setAdapter(adapter);
        binding.patientsListDoctor.setLayoutManager(new LinearLayoutManager(this.getContext()));

        binding.newPatientBtn.setOnClickListener(v ->
                Navigation.findNavController(getActivity(), R.id.container)
                        .navigate(R.id.action_doctorPatientsFragment_to_doctorNewPatientFragment));

        binding.tempHistoryBtn.setOnClickListener(v ->
                Navigation.findNavController(getActivity(), R.id.container)
                        .navigate(R.id.action_doctorPatientsFragment_to_doctorPatientHistoryFragment));
    }
}