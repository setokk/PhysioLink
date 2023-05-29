package com.mobile.physiolink.ui.psf.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.physiolink.databinding.ItemDocBinding;
import com.mobile.physiolink.model.user.Doctor;

public class AdapterForClinics extends RecyclerView.Adapter<AdapterForClinics.MyViewHolder>
{
    private Doctor[] doctors;

    public AdapterForClinics()
    {
        doctors = new Doctor[0];
    }

    public void setDoctors(Doctor[] doctors)
    {
        this.doctors = doctors;
        notifyDataSetChanged();
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
        holder.itemDocBinding.doctorName.setText(new StringBuilder()
                .append(doctors[position].getName())
                .append(" ")
                .append(doctors[position].getSurname())
                .toString());
        holder.itemDocBinding.doctorClinic.setText(doctors[position].getPhysioName());
        holder.itemDocBinding.doctorAddress.setText(doctors[position].getAddress());
    }

    @Override
    public int getItemCount() {
        return doctors.length;
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