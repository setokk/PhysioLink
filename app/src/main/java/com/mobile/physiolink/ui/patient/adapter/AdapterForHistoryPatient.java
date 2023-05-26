package com.mobile.physiolink.ui.patient.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.ItemPatientHistoryBinding;
import com.mobile.physiolink.model.appointment.Appointment;

import java.util.Arrays;

public class AdapterForHistoryPatient extends RecyclerView.Adapter<AdapterForHistoryPatient.MyViewHolder>
{
    private Appointment[] appointments = new Appointment[0];

    private boolean[] isExpanded;

    public AdapterForHistoryPatient()
    {
        isExpanded = new boolean[0];
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
    public AdapterForHistoryPatient.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        ItemPatientHistoryBinding itemHistoryBinding = ItemPatientHistoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent,false);
        return new AdapterForHistoryPatient.MyViewHolder(itemHistoryBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterForHistoryPatient.MyViewHolder holder, int position)
    {
        holder.itemHistoryBinding.serviceNamePatientHistory
                .setText(appointments[position].getServiceTitle());
        holder.itemHistoryBinding.appointmentDatePatientHistory
                .setText(appointments[position].getDate());

        String pmAm;
        double hour = Double.parseDouble(appointments[position].getHour());
        if (hour < 12)
            pmAm = "πμ";
        else
            pmAm = "μμ";

        holder.itemHistoryBinding.appointmentTimePatientHistory
                .setText(String.format("%s:00 " + pmAm, appointments[position].getHour()));
        holder.itemHistoryBinding.appointmentDescriptionPatientHistory
                .setText(appointments[position].getServiceDescription());
        holder.itemHistoryBinding.servicePricePatientHistory
                .setText(String.format("%s€", appointments[position].getServicePrice()));

        boolean isItemExpanded = isExpanded[position];
        boolean hasContent = holder.itemHistoryBinding.appointmentDescriptionPatientHistory.length() > 0;

        holder.itemHistoryBinding.appointmentDescriptionPatientHistory
                .setText(hasContent ? appointments[position].getServiceDescription() : "-");

        // Set the initial state based on the expanded flag
            if (isItemExpanded) {
                holder.itemHistoryBinding.appointmentDescriptionPatientHistory.setMaxLines(Integer.MAX_VALUE);
                holder.itemHistoryBinding.appointmentDescriptionPatientHistory.setEllipsize(null);
            } else {
                holder.itemHistoryBinding.appointmentDescriptionPatientHistory.setMaxLines(1);
                holder.itemHistoryBinding.appointmentDescriptionPatientHistory.setEllipsize(TextUtils.TruncateAt.END);
            }
    }

    private void toggleExpansion(int position) {
        isExpanded[position] = !isExpanded[position];
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return appointments.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ItemPatientHistoryBinding itemHistoryBinding;
        public MyViewHolder(ItemPatientHistoryBinding itemHistoryBinding)
        {
            super(itemHistoryBinding.getRoot());
            this.itemHistoryBinding = itemHistoryBinding;

            // Set click listener on the entire item view
            itemHistoryBinding.getRoot().setOnClickListener(view ->
                    toggleExpansion(getBindingAdapterPosition()));
        }
    }
}


