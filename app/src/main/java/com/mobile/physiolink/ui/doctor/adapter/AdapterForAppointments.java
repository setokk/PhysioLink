package com.mobile.physiolink.ui.doctor.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.ItemDoctorAppointmentBinding;
import com.mobile.physiolink.model.appointment.Appointment;
import com.mobile.physiolink.util.date.TimeFormatter;

import java.util.Arrays;

public class AdapterForAppointments extends RecyclerView.Adapter <AdapterForAppointments.MyViewHolder>
{
    private Appointment[] appointments;

    private boolean[] isExpanded;

    public AdapterForAppointments()
    {
        appointments = new Appointment[0];
        isExpanded = new boolean[appointments.length];
    }

    public void setAppointments(Appointment[] appointments)
    {
        this.appointments = appointments;
        isExpanded = new boolean[appointments.length];
        Arrays.fill(isExpanded, false);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterForAppointments.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDoctorAppointmentBinding binding = ItemDoctorAppointmentBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AdapterForAppointments.MyViewHolder(binding);
    }

    @Override
    public int getItemCount() {
        return appointments.length;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForAppointments.MyViewHolder holder, int position)
    {
        holder.binding.appointmentNameDoctorPatient
                .setText(new StringBuilder()
                        .append(appointments[position].getPatName())
                        .append(" ")
                        .append(appointments[position].getPatSurname()).toString());
        holder.binding.appointmentTimeDoctorPatient
                .setText(TimeFormatter.formatToPM_AM(appointments[position].getHour()));
        holder.binding.patientImageDoctorAppointment.setImageResource(R.drawable.boy);
        /*holder.binding.appointmentCommentsDoctorPatient
                .setText(appointments[position].getMessage());*/


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
    private void toggleExpansion(int position) {
        isExpanded[position] = !isExpanded[position];
        notifyItemChanged(position);
    }



    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ItemDoctorAppointmentBinding binding;



        public MyViewHolder(ItemDoctorAppointmentBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;

            // Set click listener on the entire item view
            binding.getRoot().setOnClickListener(view ->
                    toggleExpansion(getBindingAdapterPosition()));
        }
    }
}
