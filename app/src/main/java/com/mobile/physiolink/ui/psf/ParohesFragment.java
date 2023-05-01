package com.mobile.physiolink.ui.psf;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentParohesBinding;
import com.mobile.physiolink.databinding.FragmentPhisiotherpeftiriaBinding;


public class ParohesFragment extends Fragment {

    private FragmentParohesBinding binding;

    RecyclerView paroxesList;
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
        binding = FragmentParohesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        paroxesList = view.findViewById(R.id.customListViewParoxes);

        k1=getResources().getStringArray(R.array.paroxesNameExample);
        k2=getResources().getStringArray(R.array.paroxesIdExamle);
        k3=getResources().getStringArray(R.array.paroxesCostExamle);
        k4=getResources().getStringArray(R.array.paroxesDescriptionExamle);

        MyItemDecoration itemDecoration = new MyItemDecoration(20); // 20px spacing
        paroxesList.addItemDecoration(itemDecoration);


        ParoxesCustomBaseAdapter adapter = new ParoxesCustomBaseAdapter(this,k1,k2,k3,k4);
        paroxesList.setAdapter(adapter);
        paroxesList.setLayoutManager(new LinearLayoutManager(this.getContext()));

        Button goToNewParoxes = view.findViewById(R.id.newParoxesButton);
        goToNewParoxes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity(), R.id.fragmentContainerView)
                        .navigate(R.id.action_parohesFragment_to_createParoxesFragment);
            }
        });
    }
}
