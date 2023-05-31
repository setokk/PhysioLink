package com.mobile.physiolink.ui.psf.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.physiolink.databinding.ItemDocBinding;
import com.mobile.physiolink.model.user.Doctor;

import java.util.ArrayList;
import java.util.List;

public class AdapterForClinics extends RecyclerView.Adapter<AdapterForClinics.MyViewHolder>
{
    private List<Doctor> doctors;

    public AdapterForClinics()
    {
        doctors = new ArrayList<>(0);
    }

    public void setDoctors(List<Doctor> doctors)
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
                .append(doctors.get(position).getName())
                .append(" ")
                .append(doctors.get(position).getSurname())
                .toString());
        holder.itemDocBinding.doctorClinic.setText(doctors.get(position).getPhysioName());
        holder.itemDocBinding.doctorAddress.setText(doctors.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return doctors.size();
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