package com.mobile.physiolink.ui.doctor.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.physiolink.databinding.ItemDoctorAppointmentBinding;
import com.mobile.physiolink.model.appointment.Appointment;
import com.mobile.physiolink.model.user.singleton.UserHolder;
import com.mobile.physiolink.service.api.API;
import com.mobile.physiolink.service.api.RequestFacade;
import com.mobile.physiolink.ui.doctor.OnButtonClickListener;
import com.mobile.physiolink.ui.popup.AppointmentPaymentPopUp;
import com.mobile.physiolink.ui.popup.AppointmentRejectPopUp;
import com.mobile.physiolink.util.date.TimeFormatter;
import com.mobile.physiolink.util.image.ProfileImageProvider;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AdapterForAppointments extends RecyclerView.Adapter <AdapterForAppointments.MyViewHolder> implements OnButtonClickListener {

    private static final int ACCEPT=1;
    private static final int REJECT=2;
    private final FragmentManager fm;
    private Appointment[] appointments;

    private boolean[] isExpanded;

    private FragmentActivity context;

    public AdapterForAppointments(FragmentManager fm)
    {
        appointments = new Appointment[0];
        isExpanded = new boolean[appointments.length];
        this.fm = fm;
    }

    public void setAppointments(Appointment[] appointments)
    {
        this.appointments = appointments;
        isExpanded = new boolean[appointments.length];
        Arrays.fill(isExpanded, false);
        notifyDataSetChanged();
    }

    public void setContext(FragmentActivity context)
    {
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterForAppointments.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDoctorAppointmentBinding binding = ItemDoctorAppointmentBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AdapterForAppointments.MyViewHolder(binding, this);
    }

    @Override
    public int getItemCount() {
        return appointments.length;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForAppointments.MyViewHolder holder, int position)
    {
        ProfileImageProvider.setImageOfAppointment(holder.binding.patientImageDoctorAppointment,
                appointments[position]);
        holder.binding.appointmentNameDoctorPatient
                .setText(new StringBuilder()
                        .append(appointments[position].getPatName())
                        .append(" ")
                        .append(appointments[position].getPatSurname()).toString());
        holder.binding.appointmentTimeDoctorPatient
                .setText(TimeFormatter.formatToPM_AM(appointments[position].getHour()));

        boolean hasContent = holder.binding.appointmentCommentsDoctorPatient.length() > 0;

            holder.binding.appointmentCommentsDoctorPatient
                    .setText(hasContent ? appointments[position].getMessage() : "-");

        boolean isItemExpanded = isExpanded[position];

        //Set the initial state based on the expanded flag
        if (isItemExpanded) {
            holder.binding.appointmentCommentsDoctorPatient.setMaxLines(Integer.MAX_VALUE);
            holder.binding.appointmentCommentsDoctorPatient.setEllipsize(null);
        } else {
            holder.binding.appointmentCommentsDoctorPatient.setMaxLines(1);
            holder.binding.appointmentCommentsDoctorPatient.setEllipsize(TextUtils.TruncateAt.END);


        }
    }

    public void remove(int position)
    {
        Appointment[] newAppointments = new Appointment[appointments.length-1];
        for(int i=0; i<position; i++){
            newAppointments[i]=appointments[i];
        }
        for(int i=position+1; i<appointments.length; i++){
            newAppointments[i-1]=appointments[i];
        }
        appointments = newAppointments;
    }

    private void toggleExpansion(int position) {
        isExpanded[position] = !isExpanded[position];
        notifyItemChanged(position);
    }

    @Override
    public void onButtonClicked(int position, int buttonId) {

    }


    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ItemDoctorAppointmentBinding binding;

        public MyViewHolder(ItemDoctorAppointmentBinding binding,
                            AdapterForAppointments adapter) {
            super(binding.getRoot());
            this.binding = binding;

            // Set click listener on the entire item view
            binding.getRoot().setOnClickListener(view ->
            {
                toggleExpansion(getBindingAdapterPosition());
            });


            binding.tickAppointmentButton.setOnClickListener(view ->
            {
                AppointmentPaymentPopUp paymentPopUp = new AppointmentPaymentPopUp(
                        appointments[getAbsoluteAdapterPosition()],
                        binding.appointmentTimeDoctorPatient.getText().toString(),
                        binding.appointmentNameDoctorPatient.getText().toString(),
                        context, adapter, getBindingAdapterPosition());
                paymentPopUp.setNegativeOnClick((dialog, which) ->
                {
                    Toast.makeText(binding.getRoot().getContext(), "Δεν έγινε πληρωμή ραντεβού!",
                            Toast.LENGTH_SHORT).show();
                });
                paymentPopUp.show(fm,"Payment pop up");
            });

            binding.exAppointmentButton.setOnClickListener(view ->
            {
                AppointmentRejectPopUp deletePopUp = new AppointmentRejectPopUp("Διαγραφή Ραντεβού",
                        "Ναι", "Όχι");
                deletePopUp.setPositiveOnClick((dialog, which) ->
                {
                    int position = getBindingAdapterPosition();

                    HashMap<String, String> keyValues = new HashMap<>(6);
                    keyValues.put("appointment_id", String.valueOf(appointments[position].getId()));
                    keyValues.put("reason", deletePopUp.getReason());
                    keyValues.put("date", appointments[position].getDate().replace('-', '/'));
                    keyValues.put("doctor_name", UserHolder.doctor().getName());
                    keyValues.put("doctor_surname", UserHolder.doctor().getSurname());
                    keyValues.put("doctor_phone_number", UserHolder.doctor().getPhoneNumber());

                    RequestFacade.postRequest(API.DECLINE_PAYMENT, keyValues, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            call.cancel();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {}
                    });

                    Toast.makeText(binding.getRoot().getContext(), "Έγινε απόρριψη ραντεβού!",
                            Toast.LENGTH_SHORT).show();

                    adapter.remove(position);
                    adapter.notifyItemRemoved(position);
                });
                deletePopUp.setNegativeOnClick((dialog, which) ->
                {
                    Toast.makeText(binding.getRoot().getContext(), "Δεν έγινε απόρριψη ραντεβού!",
                            Toast.LENGTH_SHORT).show();
                });
                deletePopUp.show(fm,"Payment pop up");
            });
        }
    }
}
