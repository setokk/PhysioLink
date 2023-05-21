package com.mobile.physiolink.ui.patient.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.physiolink.databinding.ItemDoctorServicesBinding;
import com.mobile.physiolink.databinding.ItemPatientDoctorServicesBinding;
import com.mobile.physiolink.databinding.ItemPatientHistoryBinding;

public class AdapterForPatientDoctorServices extends RecyclerView.Adapter<AdapterForPatientDoctorServices.MyViewHolder>{

    String service[];
    String description[];
    String price[];

    public AdapterForPatientDoctorServices(String s1[], String s2[], String s3[]){
        this.service=s1;
        this.description=s2;
        this.price=s3;
    }

    @NonNull
    @Override
    public AdapterForPatientDoctorServices.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPatientDoctorServicesBinding itemDoctorServicesBinding= ItemPatientDoctorServicesBinding.inflate(LayoutInflater.from(parent.getContext()), parent,false);
        return new AdapterForPatientDoctorServices.MyViewHolder(itemDoctorServicesBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForPatientDoctorServices.MyViewHolder holder, int position) {
        holder.itemDoctorServicesBinding.patientDoctorServiceName.setText(service[position]);
        holder.itemDoctorServicesBinding.patientDoctorServiceDescription.setText(description[position]);
        holder.itemDoctorServicesBinding.patientDoctorServicePrice.setText(price[position]);
    }

    @Override
    public int getItemCount() {
        return service.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ItemPatientDoctorServicesBinding itemDoctorServicesBinding;

        public MyViewHolder(ItemPatientDoctorServicesBinding itemDoctorServicesBinding)
        {
            super(itemDoctorServicesBinding.getRoot());
            this.itemDoctorServicesBinding= itemDoctorServicesBinding;
        }
    }
}
