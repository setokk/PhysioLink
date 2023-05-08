package com.mobile.physiolink.ui.psf.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.physiolink.databinding.ItemServicesBinding;

public class AdapterForServices extends RecyclerView.Adapter<AdapterForServices.MyViewHolder>
{
    String listParoxesName[];
    String listParoxesId[];
    String listParoxesCost[];
    String listParoxesDescription[];

    public AdapterForServices(String k1[], String k2[], String k3[], String k4[])
    {
        listParoxesName = k1;
        listParoxesId = k2;
        listParoxesCost = k3;
        listParoxesDescription = k4;
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
        holder.itemServicesBinding.serviceName.setText(listParoxesName[position]);
        holder.itemServicesBinding.serviceId.setText(listParoxesId[position]);
        holder.itemServicesBinding.serviceCost.setText(listParoxesCost[position]);
        holder.itemServicesBinding.serviceDescription.setText(listParoxesDescription[position]);
    }

    @Override
    public int getItemCount()
    {
        return listParoxesName.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ItemServicesBinding itemServicesBinding;

        public MyViewHolder(ItemServicesBinding itemServicesBinding)
        {
            super(itemServicesBinding.getRoot());
            this.itemServicesBinding = itemServicesBinding;
        }
    }
}