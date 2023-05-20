package com.mobile.physiolink;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.mobile.physiolink.databinding.FragmentServiceInformationBinding;

import java.util.ArrayList;


public class ServiceInformationFragment extends Fragment {

    private FragmentServiceInformationBinding binding;

    private boolean edit = false;

    private final ArrayList<EditText> all_inputs = new ArrayList<>();

    public ServiceInformationFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentServiceInformationBinding.inflate(inflater, container, false);

        all_inputs.add(binding.nameInput);
        all_inputs.add(binding.priceInput);
        all_inputs.add(binding.codeInput);
        all_inputs.add(binding.descriptionInput);

        binding.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edit){
                    Toast.makeText(getActivity(), "Μπορείται να επεξεργαστείται τα πεδία.", Toast.LENGTH_SHORT).show();
                    edit = true;
                    for(int i = 0; i < all_inputs.size(); i++){
                        all_inputs.get(i).setEnabled(true);
                    }
                    binding.editButton.setText("Αποθήκευση Αλλαγών");
                }
                else{
                    edit = false;
                    for(int i = 0; i < all_inputs.size(); i++){
                        all_inputs.get(i).setEnabled(false);
                    }
                    binding.editButton.setText("Επεξεργασία");
                }
            }
        });

        return binding.getRoot();
    }
}