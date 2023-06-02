package com.mobile.physiolink.ui.popup;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.ItemDoctorPaymentPopUpBinding;
import com.mobile.physiolink.ui.doctor.viewmodel.DoctorServicesViewModel;
import com.mobile.physiolink.ui.patient.RecyclerItemClickListener;
import com.mobile.physiolink.ui.popup.adapter.AdapterForServicesPayment;

import java.util.ArrayList;

public class AppointmentPaymentPopUp extends AppCompatDialogFragment {
    private DoctorServicesViewModel servicesViewmodel;



    private final String title;
    private ItemDoctorPaymentPopUpBinding binding;
    private AdapterForServicesPayment adapter;
    private ImageView patientProfilePic, newPatientProfilePic;
    private TextView appointmentHour;
    private TextView appointmentName;
    private String newAppointmentHour;
    private String newAppointmentName;
    private AppCompatButton serviceBtn;
    private RecyclerView availableServicesList;
    private TextView servicePrice;
    private CardView chooseServiceCardView;

    private DoctorServicesViewModel doctorServicesViewModel;



    private DialogInterface.OnClickListener positiveListener;
    private DialogInterface.OnClickListener negativeListener;

    public AppointmentPaymentPopUp(ImageView patientProfilePic, String appointmentHour, String appointmentName) {
        title = "Καταχώρηση ραντεβού";

        this.patientProfilePic = patientProfilePic;
        this.newAppointmentHour = appointmentHour;
        this.newAppointmentName = appointmentName;

    }

    public void setPositiveOnClick(DialogInterface.OnClickListener listener) {
        this.positiveListener = listener;
    }

    public void setNegativeOnClick(DialogInterface.OnClickListener listener) {
        this.negativeListener = listener;
    }

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.item_doctor_payment_pop_up, null);

        builder.setTitle(title)
                .setView(view)
                .setPositiveButton("Καταχώρηση", positiveListener)
                .setNegativeButton("Ακύρωση", negativeListener);

        appointmentHour = view.findViewById(R.id.appointmentTimeDoctorPatient);
        appointmentHour.setText(newAppointmentHour);

        appointmentName = view.findViewById(R.id.appointmentNameDoctorPatient);
        appointmentName.setText(newAppointmentName);

        return builder.create();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ItemDoctorPaymentPopUpBinding.inflate(inflater, container, false);



        servicesViewmodel = new ViewModelProvider(this).get(DoctorServicesViewModel.class);
        servicesViewmodel.getDoctorServices().observe(getViewLifecycleOwner(), services ->
        {

                adapter.setServices(services);


        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        adapter = new AdapterForServicesPayment(new ArrayList<>());
        binding.servicesPaymentList.setAdapter(adapter);
        binding.servicesPaymentList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.serviceBtn.setOnClickListener(view1 ->
        {
            if (binding.servicesPaymentList.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(binding.chooseServicePaymentCardView, new AutoTransition());
                binding.servicesPaymentList.setVisibility(View.VISIBLE);
            } else {
                TransitionManager.beginDelayedTransition(binding.chooseServicePaymentCardView, new AutoTransition());
                binding.servicesPaymentList.setVisibility(View.GONE);
            }
        });

        binding.servicesPaymentList.addOnItemTouchListener(
                new RecyclerItemClickListener(this.getContext(), binding.servicesPaymentList, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String text = String.valueOf(adapter.getServices().get(position));
                        binding.serviceBtn.setText(text);
                        binding.servicesPaymentList.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );
    }
}