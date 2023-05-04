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
import com.mobile.physiolink.ui.doctor.adapter.AdapterForPatientList;
import com.mobile.physiolink.ui.doctor.decoration.DecorationDoctorItem;

public class DoctorPatientsFragment extends Fragment
{
    RecyclerView patientList;
    String s1[],s2[],s3[],s4[];
    private FragmentDoctorPatientsBinding binding;

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
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        patientList = view.findViewById(R.id.patientsListDoctor);

        s1=getResources().getStringArray(R.array.patientListExampleName);
        s2=getResources().getStringArray(R.array.patientListExampleSurnmaeame);
        s3=getResources().getStringArray(R.array.patientListExampleAMKA);
        s4=getResources().getStringArray(R.array.patientListExamplePhone);

        DecorationDoctorItem itemDecoration = new DecorationDoctorItem(20); // 20px spacing
        patientList.addItemDecoration(itemDecoration);

        AdapterForPatientList myAdapter = new AdapterForPatientList(this.getContext(),s1,s2,s3,s4);
        patientList.setAdapter(myAdapter);
        patientList.setLayoutManager(new LinearLayoutManager(this.getContext()));

        binding.newPatientBtn.setOnClickListener(v ->
                Navigation.findNavController(getActivity(), R.id.container)
                        .navigate(R.id.action_doctorPatientsFragment_to_doctorNewPatientFragment));

    }
}