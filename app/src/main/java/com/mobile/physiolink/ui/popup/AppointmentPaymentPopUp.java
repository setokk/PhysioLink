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
import com.mobile.physiolink.ui.doctor.adapter.AdapterForDoctorServices;
import com.mobile.physiolink.ui.doctor.viewmodel.DoctorServicesViewModel;
import com.mobile.physiolink.ui.patient.RecyclerItemClickListener;

public class AppointmentPaymentPopUp extends AppCompatDialogFragment {
    private final String title;
    private ItemDoctorPaymentPopUpBinding binding;
    private AdapterForDoctorServices adapter;
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
    private boolean reopened = false;


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
        View view = inflater.inflate(R.layout.item_doctor_payment_pop_up, container, false);
        binding = ItemDoctorPaymentPopUpBinding.bind(view);

        adapter = new AdapterForDoctorServices();
        binding.availableServicesList.setAdapter(adapter);
        binding.availableServicesList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.serviceBtn.setOnClickListener(view1 -> {
            if (binding.availableServicesList.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(binding.chooseServiceCardView, new AutoTransition());
                binding.availableServicesList.setVisibility(View.VISIBLE);
            } else {
                TransitionManager.beginDelayedTransition(binding.chooseServiceCardView, new AutoTransition());
                binding.availableServicesList.setVisibility(View.GONE);
            }
        });

        binding.availableServicesList.addOnItemTouchListener(
                new RecyclerItemClickListener(requireContext(), binding.availableServicesList, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String serviceText = adapter.getServices().get(position).getTitle();
                        String priceText = Double.toString(adapter.getServices().get(position).getPrice());
                        binding.serviceBtn.setText(serviceText);
                        binding.servicePrice.setText(priceText);
                        binding.availableServicesList.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // Handle long item click if needed
                    }
                })
        );

        return view;
    }
}