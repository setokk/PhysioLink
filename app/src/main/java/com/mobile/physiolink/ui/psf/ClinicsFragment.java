
package com.mobile.physiolink.ui.psf;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.mobile.physiolink.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mobile.physiolink.databinding.FragmentClinicsBinding;
import com.mobile.physiolink.ui.decoration.DecorationSpacingItem;
import com.mobile.physiolink.ui.psf.adapter.AdapterForClinics;


public class ClinicsFragment extends Fragment
{
    private FragmentClinicsBinding binding;

    String n1[],n2[],n3[];

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
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        n1=getResources().getStringArray(R.array.doctoNameListExample);
        n2=getResources().getStringArray(R.array.doctorOfficeListExample);
        n3=getResources().getStringArray(R.array.doctorAddressListExample);

        binding.customListView.addItemDecoration(new DecorationSpacingItem(20));
        binding.customListView.setAdapter(new AdapterForClinics(n1,n2,n3));
        binding.customListView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        binding.newClinicButton.setOnClickListener(v ->
                Navigation.findNavController(getActivity(), R.id.fragmentContainerView)
                .navigate(R.id.action_fragment_clinics_to_doctorInformationFragment));
    }
}





