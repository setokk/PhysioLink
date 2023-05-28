package com.mobile.physiolink.ui.doctor.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.physiolink.databinding.ItemDoctorServicesBinding;
import com.mobile.physiolink.model.service.Service;
import com.mobile.physiolink.model.user.Patient;
import com.mobile.physiolink.ui.doctor.OnItemClickListener;
import com.mobile.physiolink.ui.doctor.OnLongItemClickListener;

import java.util.Arrays;

public class AdapterForDoctorServices extends RecyclerView.Adapter<AdapterForDoctorServices.MyViewHolder>
{
    private Service[] services;

    private boolean[] isExpanded;
    private OnLongItemClickListener<Service> listener;

    public AdapterForDoctorServices()
    {
        services = new Service[0];
        isExpanded = new boolean[services.length];
    }

    public void setOnLongItemClickListener(OnLongItemClickListener<Service> listener){
        this.listener=listener;
    }
    public void setServices(Service[] services)
    {
        this.services = services;
        isExpanded = new boolean[services.length];
        Arrays.fill(isExpanded, false);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterForDoctorServices.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDoctorServicesBinding itemDoctorServicesBinding = ItemDoctorServicesBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AdapterForDoctorServices.MyViewHolder(itemDoctorServicesBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForDoctorServices.MyViewHolder holder, int position)
    {
        holder.itemDoctorServicesBinding.serviceNameDoctor
                .setText(services[position].getTitle());
        holder.itemDoctorServicesBinding.serviceDescriptionDoctor
                .setText(services[position].getDescription());
        holder.itemDoctorServicesBinding.servicePriceDoctor
                .setText(String.valueOf(services[position].getPrice()));

        boolean isItemExpanded = isExpanded[position];

        // Set the initial state based on the expanded flag
        if (isItemExpanded) {
            holder.itemDoctorServicesBinding.serviceDescriptionDoctor.setMaxLines(Integer.MAX_VALUE);
            holder.itemDoctorServicesBinding.serviceDescriptionDoctor.setEllipsize(null);
        } else {
            holder.itemDoctorServicesBinding.serviceDescriptionDoctor.setMaxLines(2);
            holder.itemDoctorServicesBinding.serviceDescriptionDoctor.setEllipsize(TextUtils.TruncateAt.END);
        }
    }

    private void toggleExpansion(int position) {
        isExpanded[position] = !isExpanded[position];
        notifyItemChanged(position);
    }


    @Override
    public int getItemCount() {
        return services.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemDoctorServicesBinding itemDoctorServicesBinding;

        public MyViewHolder(ItemDoctorServicesBinding itemDoctorServicesBinding) {
            super(itemDoctorServicesBinding.getRoot());
            this.itemDoctorServicesBinding = itemDoctorServicesBinding;

            // Set click listener on the entire item view
            itemDoctorServicesBinding.getRoot().setOnClickListener(view ->
                    toggleExpansion(getBindingAdapterPosition()));

            itemDoctorServicesBinding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = getAbsoluteAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null)
                    {
                        listener.onLongItemClick(services[position]);
                    }
                    return true;
                }
            });
        }
    }
}
