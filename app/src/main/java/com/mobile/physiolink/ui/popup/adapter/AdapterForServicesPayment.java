package com.mobile.physiolink.ui.popup.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.physiolink.databinding.ItemListServicePaymentBinding;
import com.mobile.physiolink.model.service.Service;

import java.util.ArrayList;
import java.util.List;


public class AdapterForServicesPayment extends RecyclerView.Adapter<AdapterForServicesPayment.MyViewHolder> {

    List<Service> services;

    public AdapterForServicesPayment(List<Service> servicesList) {
        this.services=new ArrayList<>(servicesList);
    }

    public void setServices(List<Service> services) {
        this.services = services;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterForServicesPayment.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListServicePaymentBinding itemListServicePaymentBinding=ItemListServicePaymentBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new AdapterForServicesPayment.MyViewHolder(itemListServicePaymentBinding);
    }



    @Override
    public void onBindViewHolder(@NonNull AdapterForServicesPayment.MyViewHolder holder, int position) {
        holder.itemListServicePaymentBinding.appointmentService.setText(services.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public List<Service> getServices()
    {
        return services;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ItemListServicePaymentBinding itemListServicePaymentBinding;

        public MyViewHolder(ItemListServicePaymentBinding itemListServicePaymentBinding)
        {
            super(itemListServicePaymentBinding.getRoot());
            this.itemListServicePaymentBinding= itemListServicePaymentBinding;

        }
    }
}