package com.mobile.physiolink.ui.doctor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentDoctorPatientHistoryBinding;
import com.mobile.physiolink.ui.doctor.adapter.AdapterForDoctorServices;
import com.mobile.physiolink.ui.doctor.adapter.AdapterForPatientHistory;
import com.mobile.physiolink.ui.doctor.decoration.DecorationDoctorItem;

public class DoctorPatientHistoryFragment extends Fragment {

    private FragmentDoctorPatientHistoryBinding binding;

    ConstraintLayout morePatientInfoView;
    Button arrowBtn;
    CardView patientInfo;
    RecyclerView servicesPatientHistoryList;
    String s1[],s2[],s3[];

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
        binding = FragmentDoctorPatientHistoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        morePatientInfoView=view.findViewById(R.id.patientHistoryMoreInfoDoctorConstraint);
        arrowBtn=view.findViewById(R.id.patientHistoryInfoDownBtn);
        patientInfo=view.findViewById(R.id.patientHistoryInfoDoctor);

        arrowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(morePatientInfoView.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(patientInfo, new AutoTransition());
                    morePatientInfoView.setVisibility(View.VISIBLE);
                    arrowBtn.setBackgroundResource(R.drawable.baseline_arrow_drop_up_24);
                }
                else{
                    TransitionManager.beginDelayedTransition(patientInfo, new AutoTransition());
                    morePatientInfoView.setVisibility(View.GONE);
                    arrowBtn.setBackgroundResource(R.drawable.baseline_arrow_drop_down_24);
                }
            }
        });

        servicesPatientHistoryList = view.findViewById(R.id.servicesListPatientHistoryDoctor);

        s1=getResources().getStringArray(R.array.patientListExampleName);
        s2=getResources().getStringArray(R.array.servicesPatientHistoryListExampleDate);
        s3=getResources().getStringArray(R.array.servicesListExamplePrices);

        DecorationDoctorItem itemDecoration = new DecorationDoctorItem(20); // 20px spacing
        servicesPatientHistoryList.addItemDecoration(itemDecoration);

        AdapterForPatientHistory myAdapter = new AdapterForPatientHistory(this.getContext(),s1,s2,s3);
        servicesPatientHistoryList.setAdapter(myAdapter);
        servicesPatientHistoryList.setLayoutManager(new LinearLayoutManager(this.getContext()));

    }


}