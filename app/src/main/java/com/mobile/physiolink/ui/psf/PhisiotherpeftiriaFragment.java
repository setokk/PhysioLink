package com.mobile.physiolink.ui.psf;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import com.mobile.physiolink.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mobile.physiolink.R;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.mobile.physiolink.databinding.FragmentPhisiotherpeftiriaBinding;


public class PhisiotherpeftiriaFragment extends Fragment {

    String doctorNameList[]={"Γιώργος Παπαδόπουλος","Μαρία Παπανικολάου","Ιωάννης Δημητρίου","Μιχάλης Κατσουλάκης","Δήμητρα Κυριακοπούλου","Χρήστος Παπανδρέου"};
    String physiotherapeftiria[]={"Φυσιοθεραπευτήριο 'Αίολος'","Φυσιοθεραπευτήριο 'Optimum'","Φυσιοθεραπευτήριο 'Fantastic'","Φυσιοθεραπευτήριο 'Cool'","Φυσιοθεραπευτήριο 'Wow'","Φυσιοθεραπευτήριο 'Amazing'"};
    String physiotherapeftiriaAddress[]={"Αγίου Δημητρίου 45, Αττική, 23892","Κρατερού 13, Θεσσαλονίκη, 56823","Κρατερού 13, Θεσσαλονίκη, 56823","Κρατερού 13, Θεσσαλονίκη, 56823","Κρατερού 13, Θεσσαλονίκη, 56823","Κρατερού 13, Θεσσαλονίκη, 56823"};
    int doctorImages[] = {R.drawable.giorgos,R.drawable.maria,R.drawable.ioannis,R.drawable.michalis,R.drawable.dimitra,R.drawable.christos};

    ListView listView;


    private FragmentPhisiotherpeftiriaBinding binding;

    public PhisiotherpeftiriaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPhisiotherpeftiriaBinding.inflate(inflater, container, false);

        //create pishiotherapeftiria button
        binding.newPfisiotherapeftirioButton.setOnClickListener((v)->
        {
            Navigation.findNavController(getActivity(), R.id.fragmentContainerView)
                    .navigate(R.id.action_phisiotherpeftiriaFragment_to_createPhisiotherapeftiriaFragment);
        });
        super.onCreate(savedInstanceState);
        binding = FragmentPhisiotherpeftiriaBinding.inflate(inflater, container, false);
        listView = binding.customListView;
        PhysiotherapeftiriaCustomBaseAdapter customBaseAdapter = new PhysiotherapeftiriaCustomBaseAdapter(this,doctorNameList,physiotherapeftiria,physiotherapeftiriaAddress,doctorImages);

        listView.setAdapter(customBaseAdapter);


        return binding.getRoot();
    }



}