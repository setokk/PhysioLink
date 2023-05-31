package com.mobile.physiolink.ui.psf.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.physiolink.databinding.ItemServicesBinding;
import com.mobile.physiolink.model.service.Service;
import com.mobile.physiolink.ui.doctor.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class AdapterForServices extends RecyclerView.Adapter<AdapterForServices.MyViewHolder>
{
    private List<Service> services;
    private OnItemClickListener<Service> listener;

    public AdapterForServices()
    {
        services = new ArrayList<>(0);
    }

    public void setServices(List<Service> services)
    {
        this.services = services;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener<Service> listener)
    {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        ItemServicesBinding itemServicesBinding = ItemServicesBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,false);
        return new MyViewHolder(itemServicesBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        holder.itemServicesBinding.serviceId.setText(services.get(position).getId());
        holder.itemServicesBinding.serviceName.setText(services.get(position).getTitle());
        holder.itemServicesBinding.serviceDescription.setText(services.get(position).getDescription());
    }

    @Override
    public int getItemCount()
    {
        return services.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener
    {
        ItemServicesBinding itemServicesBinding;

        public MyViewHolder(ItemServicesBinding itemServicesBinding)
        {
            super(itemServicesBinding.getRoot());
            this.itemServicesBinding = itemServicesBinding;
            this.itemServicesBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAbsoluteAdapterPosition();
            if (position != RecyclerView.NO_POSITION && listener != null)
            {
                listener.onItemClick(services.get(position));
            }
        }
    }
}