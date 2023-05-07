package com.mobile.physiolink.ui.psf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.physiolink.R;

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
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_doc,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        holder.name.setText(listDoctorName[position]);
        holder.office.setText(listDoctorOffice[position]);
        holder.address.setText(listDoctorAddress[position]);
    }

    @Override
    public int getItemCount() {
        return listDoctorName.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name, office, address;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            name= itemView.findViewById(R.id.doctorName);
            office= itemView.findViewById(R.id.doctorOffice);
            address= itemView.findViewById(R.id.doctorAddress);
        }
    }
}