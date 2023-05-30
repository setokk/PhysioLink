package com.mobile.physiolink.ui.doctor;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
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

import com.mobile.physiolink.databinding.FragmentDoctorServicesBinding;
import com.mobile.physiolink.model.user.singleton.UserHolder;
import com.mobile.physiolink.service.dao.ServiceDAO;
import com.mobile.physiolink.ui.doctor.adapter.AdapterForDoctorServices;
import com.mobile.physiolink.ui.decoration.DecorationSpacingItem;
import com.mobile.physiolink.ui.doctor.viewmodel.DoctorServicesViewModel;
import com.mobile.physiolink.ui.popup.ConfirmationPopUp;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DoctorServicesFragment extends Fragment{
    private FragmentDoctorServicesBinding binding;
    private DoctorServicesViewModel viewModel;
    private AdapterForDoctorServices adapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        /* On back button pressed, Go back to home fragment */
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true)
        {
            @Override
            public void handleOnBackPressed()
            {
                NavController navController = Navigation.findNavController(getActivity(), R.id.container);
                navController.navigate(R.id.action_doctorServicesFragment_to_doctorHomeFragment);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        binding = FragmentDoctorServicesBinding.inflate(inflater, container, false);

        adapter = new AdapterForDoctorServices();
        viewModel = new ViewModelProvider(this).get(DoctorServicesViewModel.class);
        viewModel.getDoctorServices().observe(getViewLifecycleOwner(), services ->
        {
            adapter.setServices(services);
        });

        adapter.setOnLongItemClickListener(service -> {
            ConfirmationPopUp confirmation = new ConfirmationPopUp("Διαγραφή Παροχής",
                    "Είστε σίγουρος πως θέλετε να διαγράψετε αυτή την παροχή;",
                    "Ναι", "Όχι");

            FragmentActivity context = getActivity();
            confirmation.setPositiveOnClick((dialog, which) ->
            {
                ServiceDAO.getInstance().deleteServiceFromDoctor(service.getId(),
                        UserHolder.doctor().getId(), new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) { call.cancel(); }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                context.runOnUiThread(() ->
                                {
                                    Toast.makeText(getActivity(), "Έγινε επιτυχώς η διαγραφή!",
                                            Toast.LENGTH_SHORT).show();
                                });
                                viewModel.loadDoctorServices(UserHolder.doctor().getId());
                            }
                        });
            });
            confirmation.setNegativeOnClick(((dialog, which) ->
            {
                Toast.makeText(getActivity(), "Δεν έγινε η διαγραφή!",
                        Toast.LENGTH_SHORT).show();
            }));

            confirmation.show(getActivity().getSupportFragmentManager(), "Confirmation pop up");
        });

        binding.searchViewServicesDoctor.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        DecorationSpacingItem itemDecoration = new DecorationSpacingItem(20); // 20px spacing
        binding.servicesListDoctor.addItemDecoration(itemDecoration);

        binding.servicesListDoctor.setAdapter(adapter);
        binding.servicesListDoctor.setLayoutManager(new LinearLayoutManager(this.getContext()));

        binding.addServiceBtn.setOnClickListener( v ->{
            Navigation.findNavController(getActivity(), R.id.container)
                    .navigate(R.id.action_doctorServicesFragment_to_doctorAddServiceFragment);
        });

        viewModel.loadDoctorServices(UserHolder.doctor().getId());
    }

}