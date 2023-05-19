package com.mobile.physiolink.ui.patient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentPatientHomeBinding;
import com.mobile.physiolink.ui.patient.adapter.AdapterForHistoryPatient;


public class PatientHomeFragment extends Fragment
{
    boolean hasAppointment=true;

    RecyclerView lastHistoryItem;
    String h1[],h2[],h3[],h4[],h5[];
    Fragment AppointmentFragment = new UpcomingAppointmentFragment();
    Fragment NoAppointmentFragment= new NoUpcomingAppointmentFragment();
    private FragmentPatientHomeBinding binding;

    public PatientHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        binding = FragmentPatientHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        if(hasAppointment){
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.upcomingAppointmentFragmentContainer, AppointmentFragment);
            transaction.commit();
        }
        else{
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.upcomingAppointmentFragmentContainer, NoAppointmentFragment);
            transaction.commit();
        }

        h1=getResources().getStringArray(R.array.appointmentsService);
        h2=getResources().getStringArray(R.array.servicesPatientHistoryListExampleDate);
        h3=getResources().getStringArray(R.array.timePatientHistory);
        h4=getResources().getStringArray(R.array.servicesListExampleDescription);
        h5=getResources().getStringArray(R.array.paroxesCostExamle);

        binding.patientHistoryLastItemPatient.setAdapter(new AdapterForHistoryPatient(h2,h3,h4,h1,h5,R.id.patientHistoryLastItemPatient));
        binding.patientHistoryLastItemPatient.setLayoutManager(new LinearLayoutManager(this.getContext()));

    }
}

