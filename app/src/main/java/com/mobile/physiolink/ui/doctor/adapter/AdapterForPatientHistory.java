package com.mobile.physiolink.ui.doctor.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.physiolink.databinding.ItemDoctorPatientHistoryServicesBinding;
import com.mobile.physiolink.model.appointment.Appointment;

public class AdapterForPatientHistory extends RecyclerView.Adapter<AdapterForPatientHistory.MyViewHolder>
{
    private Appointment[] appointments;

    public AdapterForPatientHistory()
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
    public AdapterForPatientHistory.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        ItemDoctorPatientHistoryServicesBinding binding = ItemDoctorPatientHistoryServicesBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AdapterForPatientHistory.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForPatientHistory.MyViewHolder holder, int position)
    {
        holder.binding.servicePatientHistoryNameDoctor
                .setText(appointments[position].getServiceTitle());
        holder.binding.servicePatientHistoryDateDoctor
                .setText(appointments[position].getDate().replace('-', '/'));
        holder.binding.servicePatientHistoryPriceDoctor
                .setText(String.valueOf(appointments[position].getServicePrice()));
    }

    @Override
    public int getItemCount() { return appointments.length; }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ItemDoctorPatientHistoryServicesBinding binding;

        public MyViewHolder(ItemDoctorPatientHistoryServicesBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
