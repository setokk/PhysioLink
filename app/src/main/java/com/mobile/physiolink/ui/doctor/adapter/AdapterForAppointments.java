package com.mobile.physiolink.ui.doctor.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.ItemDoctorAppointmentBinding;
import com.mobile.physiolink.model.appointment.Appointment;
import com.mobile.physiolink.util.date.TimeFormatter;

public class AdapterForAppointments extends RecyclerView.Adapter <AdapterForAppointments.MyViewHolder>
{
    private Appointment[] appointments;

    public AdapterForAppointments()
    {
        appointments = new Appointment[0];
    }

    public void setAppointments(Appointment[] appointments)
    {
        this.appointments = appointments;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterForAppointments.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDoctorAppointmentBinding binding = ItemDoctorAppointmentBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
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
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        ItemDoctorAppointmentBinding binding;

        public MyViewHolder(ItemDoctorAppointmentBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
