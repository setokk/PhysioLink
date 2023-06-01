package com.mobile.physiolink.ui.patient.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.physiolink.databinding.ItemPatientHistoryBinding;
import com.mobile.physiolink.model.appointment.Appointment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class AdapterForHistoryPatient extends RecyclerView.Adapter<AdapterForHistoryPatient.MyViewHolder> implements Filterable
{
    private List<Appointment> appointments;
    private List<Appointment> appointmentsFull;

    private boolean[] isExpanded;

    public AdapterForHistoryPatient()
    {
        this.appointments = new ArrayList<>();
        isExpanded = new boolean[appointments.size()];
    }

    public void setAppointments(List<Appointment> appointments)
    {
        this.appointments = appointments;
        appointmentsFull = new ArrayList<>(appointments);
        isExpanded = new boolean[appointments.size()];
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
                .setText(appointments.get(position).getServiceTitle());
        holder.itemHistoryBinding.appointmentDatePatientHistory
                .setText(appointments.get(position).getDate());

        String pmAm;
        double hour = Double.parseDouble(appointments.get(position).getHour());
        if (hour < 12)
            pmAm = "πμ";
        else
            pmAm = "μμ";

        holder.itemHistoryBinding.appointmentTimePatientHistory
                .setText(String.format("%s:00 " + pmAm, appointments.get(position).getHour()));
        holder.itemHistoryBinding.appointmentDescriptionPatientHistory
                .setText(appointments.get(position).getServiceDescription());
        holder.itemHistoryBinding.servicePricePatientHistory
                .setText(String.format("%s€", appointments.get(position).getServicePrice()));

        boolean isItemExpanded = isExpanded[position];
        boolean hasContent = holder.itemHistoryBinding.appointmentDescriptionPatientHistory.length() > 0;

        holder.itemHistoryBinding.appointmentDescriptionPatientHistory
                .setText(hasContent ? appointments.get(position).getServiceDescription() : "-");

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
        return appointments.size();
    }

    @Override
    public Filter getFilter() {
        return appointmentsFilter;
    }

    private Filter appointmentsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Appointment> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(appointmentsFull);
            }
            else {
                String filterPattern = charSequence.toString().toUpperCase().trim();
                for(Appointment appoint: appointmentsFull){
                    if(appoint.getMessage().toUpperCase().contains(filterPattern) ||
                        appoint.getDate().contains(filterPattern) ||
                        appoint.getServiceTitle().toUpperCase().contains(filterPattern)){
                        filteredList.add(appoint);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            appointments.clear();
            appointments.addAll(Optional.ofNullable((List) filterResults.values)
                    .orElse(new ArrayList<>(0)));
            notifyDataSetChanged();
        }
    };

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


