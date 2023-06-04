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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mobile.physiolink.databinding.ItemDoctorPaymentPopUpBinding;
import com.mobile.physiolink.model.appointment.Appointment;
import com.mobile.physiolink.model.user.singleton.UserHolder;
import com.mobile.physiolink.service.api.API;
import com.mobile.physiolink.service.api.RequestFacade;
import com.mobile.physiolink.ui.doctor.adapter.AdapterForAppointments;
import com.mobile.physiolink.ui.doctor.viewmodel.DoctorServicesViewModel;
import com.mobile.physiolink.ui.patient.RecyclerItemClickListener;
import com.mobile.physiolink.ui.popup.adapter.AdapterForServicesPayment;
import com.mobile.physiolink.util.image.ProfileImageProvider;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class AppointmentPaymentPopUp extends AppCompatDialogFragment {
    private DoctorServicesViewModel servicesViewmodel;
    private final String title;
    private ItemDoctorPaymentPopUpBinding binding;
    private AdapterForServicesPayment adapter;
    private Appointment appointment;
    private String newAppointmentHour;
    private String newAppointmentName;

    private DialogInterface.OnClickListener positiveListener;
    private DialogInterface.OnClickListener negativeListener;

    public AppointmentPaymentPopUp(Appointment appointment,
                                   String appointmentHour,
                                   String appointmentName,
                                   FragmentActivity context,
                                   AdapterForAppointments adapter, int position) {
        title = "Καταχώρηση ραντεβού";
        this.appointment = appointment;
        this.newAppointmentHour = appointmentHour;
        this.newAppointmentName = appointmentName;

        setPositiveOnClick(((dialog, which) ->
        {
            HashMap<String, String> keyValues = new HashMap<>(3);
            keyValues.put("appointment_id", String.valueOf(appointment.getId()));
            keyValues.put("service_title", binding.serviceBtn.getText().toString());
            keyValues.put("date", appointment.getDate());

            RequestFacade.postRequest(API.ACCEPT_PAYMENT, keyValues, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    call.cancel();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (context == null)
                        return;

                    context.runOnUiThread(() ->
                    {
                        adapter.remove(position);
                        adapter.notifyItemRemoved(position);

                        Toast.makeText(context, "Έγινε επιτυχής καταχώρηση ραντεβού!",
                                Toast.LENGTH_SHORT).show();
                    });
                }
            });
        }));

    }

    public void setPositiveOnClick(DialogInterface.OnClickListener listener) {
        this.positiveListener = listener;
    }

    public void setNegativeOnClick(DialogInterface.OnClickListener listener) {
        this.negativeListener = listener;
    }

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        binding = ItemDoctorPaymentPopUpBinding.inflate(requireActivity().getLayoutInflater());

        builder.setTitle(title)
        .setView(binding.getRoot())
        .setPositiveButton("Καταχώρηση", positiveListener)
        .setNegativeButton("Ακύρωση", negativeListener);


        binding.appointmentTimeDoctorPatient.setText(newAppointmentHour);
        binding.appointmentNameDoctorPatient.setText(newAppointmentName);

        return builder.create();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        servicesViewmodel = new ViewModelProvider(this).get(DoctorServicesViewModel.class);
        servicesViewmodel.getDoctorServices().observe(getViewLifecycleOwner(), services ->
                adapter.setServices(services));

        ProfileImageProvider.setImageOfAppointment(binding.patientImageDoctorAppointment,
                appointment);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        servicesViewmodel.loadDoctorServices(UserHolder.doctor().getId());
        adapter = new AdapterForServicesPayment();
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
                        binding.serviceBtn.setText(adapter.getServices().get(position).getTitle());
                        binding.servicePricePayment.setText(String.valueOf((int) adapter.getServices().get(position).getPrice())+"€");
                        binding.servicesPaymentList.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );
    }
}