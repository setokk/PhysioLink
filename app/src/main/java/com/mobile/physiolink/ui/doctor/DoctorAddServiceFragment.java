package com.mobile.physiolink.ui.doctor;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentDoctorAddServiceBinding;
import com.mobile.physiolink.model.user.singleton.UserHolder;
import com.mobile.physiolink.service.dao.ServiceDAO;
import com.mobile.physiolink.ui.decoration.DecorationSpacingItem;
import com.mobile.physiolink.ui.doctor.adapter.AdapterForNewDoctorServices;
import com.mobile.physiolink.ui.doctor.viewmodel.DoctorAddServicesViewModel;
import com.mobile.physiolink.ui.popup.ConfirmationPopUp;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class DoctorAddServiceFragment extends Fragment {

    private FragmentDoctorAddServiceBinding binding;
    private AdapterForNewDoctorServices adapter;
    private DoctorAddServicesViewModel viewModel;


    public DoctorAddServiceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        /* On back button pressed, Go back to services fragment */
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true)
        {
            @Override
            public void handleOnBackPressed()
            {
                NavController navController = Navigation.findNavController(getActivity(), R.id.container);
                navController.navigate(R.id.action_doctorAddServiceFragment_to_doctorServicesFragment);
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        adapter = new AdapterForNewDoctorServices();
        viewModel = new ViewModelProvider(this).get(DoctorAddServicesViewModel.class);
        viewModel.getNewDoctorServices().observe(getViewLifecycleOwner(), newDoctorServices ->{
            adapter.setServices(newDoctorServices);
        });

        adapter.setOnLongItemClickListener(service -> {
            ConfirmationPopUp confirmation = new ConfirmationPopUp("Προσθήκη Παροχής",
                    "Είστε σίγουρος πως θέλετε να προσθέσετε αυτή την παροχή στις δικές σας παροχές σας;",
                    "Ναι", "Όχι");

            FragmentActivity context = getActivity();
            confirmation.setPositiveOnClick((dialog, which) ->
            {
                ServiceDAO.getInstance().linkServiceToDoctor(service.getId(),
                        UserHolder.doctor().getId(), new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                call.cancel();
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                context.runOnUiThread(() ->
                                {
                                    Toast.makeText(context, "Έγινε επιτυχώς η προσθήκη!",
                                            Toast.LENGTH_SHORT).show();
                                });
                                viewModel.loadNewDoctorServices(UserHolder.doctor().getId());
                            }
                        });
            });
            confirmation.setNegativeOnClick(((dialog, which) ->
            {
                Toast.makeText(getActivity(), "Δεν έγινε η προσθήκη!",
                        Toast.LENGTH_SHORT).show();
            }));
            confirmation.show(getActivity().getSupportFragmentManager(), "Confirmation pop up");
        });
        // Inflate the layout for this fragment
        binding = FragmentDoctorAddServiceBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        binding.doctorAddServicesList.setAdapter(adapter);
        binding.doctorAddServicesList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        DecorationSpacingItem itemDecoration = new DecorationSpacingItem(20); // 20px spacing
        binding.doctorAddServicesList.addItemDecoration(itemDecoration);

        viewModel.loadNewDoctorServices(UserHolder.doctor().getId());
    }
}