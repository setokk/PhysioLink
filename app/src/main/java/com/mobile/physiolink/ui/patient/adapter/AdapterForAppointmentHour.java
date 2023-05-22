package com.mobile.physiolink.ui.patient.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.physiolink.databinding.ItemListTimeBinding;

public class AdapterForAppointmentHour extends RecyclerView.Adapter<AdapterForAppointmentHour.MyViewHolder> {

    String hours[];

    public AdapterForAppointmentHour(String hourList[]){
        this.hours=hourList;
    }
    @NonNull
    @Override
    public AdapterForAppointmentHour.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListTimeBinding itemListTimeBinding=ItemListTimeBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new AdapterForAppointmentHour.MyViewHolder(itemListTimeBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForAppointmentHour.MyViewHolder holder, int position) {
        holder.itemListTimeBinding.appointmentHour.setText(hours[position]);
    }

    @Override
    public int getItemCount() {
        return hours.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ItemListTimeBinding itemListTimeBinding;

        public MyViewHolder(ItemListTimeBinding itemListTimeBinding)
        {
            super(itemListTimeBinding.getRoot());
            this.itemListTimeBinding= itemListTimeBinding;

        }
    }
}
