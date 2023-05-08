package com.mobile.physiolink.ui.psf.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.physiolink.databinding.ItemDocBinding;

public class AdapterForClinics extends RecyclerView.Adapter<AdapterForClinics.MyViewHolder>
{
    String listDoctorName[];
    String listDoctorOffice[];
    String listDoctorAddress[];

    public AdapterForClinics(String n1[], String n2[], String n3[])
    {
        listDoctorName = n1;
        listDoctorOffice = n2;
        listDoctorAddress = n3;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        ItemDocBinding itemDocBinding = ItemDocBinding.inflate(LayoutInflater.from(parent.getContext()), parent,false);
        return new MyViewHolder(itemDocBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        holder.itemDocBinding.doctorName.setText(listDoctorName[position]);
        holder.itemDocBinding.doctorClinic.setText(listDoctorOffice[position]);
        holder.itemDocBinding.doctorAddress.setText(listDoctorAddress[position]);
    }

    @Override
    public int getItemCount() {
        return listDoctorName.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ItemDocBinding itemDocBinding;

        public MyViewHolder(ItemDocBinding itemDocBinding)
        {
            super(itemDocBinding.getRoot());
            this.itemDocBinding = itemDocBinding;
        }
    }
}