package com.mobile.physiolink.ui.psf;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentServicesBinding;
import com.mobile.physiolink.ui.decoration.DecorationSpacingItem;
import com.mobile.physiolink.ui.psf.adapter.AdapterForServices;


public class ServicesFragment extends Fragment {

    private FragmentServicesBinding binding;

    String k1[],k2[],k3[],k4[];


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
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        k1=getResources().getStringArray(R.array.paroxesNameExample);
        k2=getResources().getStringArray(R.array.paroxesIdExamle);
        k3=getResources().getStringArray(R.array.paroxesCostExamle);
        k4=getResources().getStringArray(R.array.paroxesDescriptionExamle);

        DecorationSpacingItem itemDecoration = new DecorationSpacingItem(20); // 20px spacing
        binding.customListViewParoxes.addItemDecoration(itemDecoration);


        AdapterForServices adapter = new AdapterForServices(k1,k2,k3,k4);
        binding.customListViewParoxes.setAdapter(adapter);
        binding.customListViewParoxes.setLayoutManager(new LinearLayoutManager(this.getContext()));

        binding.newParoxesButton.setOnClickListener(v ->
                Navigation.findNavController(getActivity(), R.id.fragmentContainerView)
                .navigate(R.id.action_parohesFragment_to_createParoxesFragment));
    }
}
