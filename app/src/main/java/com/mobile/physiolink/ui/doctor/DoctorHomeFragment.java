package com.mobile.physiolink.ui.doctor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentDoctorHomeBinding;
import com.mobile.physiolink.model.user.singleton.UserHolder;
import com.mobile.physiolink.service.dao.DoctorDAO;
import com.mobile.physiolink.ui.doctor.adapter.AdapterForAppointments;
import com.mobile.physiolink.ui.decoration.DecorationSpacingItem;


public class DoctorHomeFragment extends Fragment
{
    RecyclerView appointmentList;
    String[] sN, sS, sT, sService;

    private FragmentDoctorHomeBinding binding;

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
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        appointmentList = view.findViewById(R.id.recyclerViewApp);
        sN = getResources().getStringArray(R.array.patientListExampleName);
        sS = getResources().getStringArray(R.array.patientListExampleSurnmaeame);
        sT = getResources().getStringArray(R.array.appointmentTime);
        sService = getResources().getStringArray(R.array.appointmentsService);

        DecorationSpacingItem itemDecoration = new DecorationSpacingItem(20); // 20px spacing
        appointmentList.addItemDecoration(itemDecoration);

        AdapterForAppointments adapter = new AdapterForAppointments(this.getContext(),sN,sS,sT,sService,R.id.recyclerViewApp);
        appointmentList.setAdapter(adapter);
        appointmentList.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }
}