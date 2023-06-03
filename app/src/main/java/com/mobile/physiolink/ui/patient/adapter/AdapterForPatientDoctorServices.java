package com.mobile.physiolink.ui.patient.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.physiolink.databinding.ItemPatientDoctorServicesBinding;
import com.mobile.physiolink.model.service.Service;

import java.util.Arrays;

public class AdapterForPatientDoctorServices extends RecyclerView.Adapter<AdapterForPatientDoctorServices.MyViewHolder>{

    private Service[] services;
    private Boolean[] isExpanded;

    public AdapterForPatientDoctorServices()
    {
        this.services = new Service[0];
        isExpanded = new Boolean[0];
        Arrays.fill(isExpanded, false);
    }

    public void setServices(Service[] services)
    {
        this.services = services;
        isExpanded = new Boolean[services.length];
        Arrays.fill(isExpanded, false);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterForPatientDoctorServices.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPatientDoctorServicesBinding itemDoctorServicesBinding= ItemPatientDoctorServicesBinding.inflate(LayoutInflater.from(parent.getContext()), parent,false);
        return new AdapterForPatientDoctorServices.MyViewHolder(itemDoctorServicesBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForPatientDoctorServices.MyViewHolder holder, int position) {
        holder.itemDoctorServicesBinding.patientDoctorServiceName.setText(services[position].getTitle());
        holder.itemDoctorServicesBinding.patientDoctorServiceDescription.setText(services[position].getDescription());
        holder.itemDoctorServicesBinding.patientDoctorServicePrice.setText(String.valueOf((int) services[position].getPrice()));

        boolean isItemExpanded = isExpanded[position];
        boolean hasContent = holder.itemDoctorServicesBinding.patientDoctorServiceDescription.length() > 0;

        if(isItemExpanded){
            holder.itemDoctorServicesBinding.patientDoctorServiceDescription.setMaxLines(Integer.MAX_VALUE);
            holder.itemDoctorServicesBinding.patientDoctorServiceDescription.setEllipsize(null);
        }
        else{
            holder.itemDoctorServicesBinding.patientDoctorServiceDescription.setMaxLines(2);
            holder.itemDoctorServicesBinding.patientDoctorServiceDescription.setEllipsize(TextUtils.TruncateAt.END);
        }
    }

    @Override
    public int getItemCount() {
        return services.length;
    }

    private void toggleExpansion(int position) {
        isExpanded[position] = !isExpanded[position];
        notifyItemChanged(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ItemPatientDoctorServicesBinding itemDoctorServicesBinding;

        public MyViewHolder(ItemPatientDoctorServicesBinding itemDoctorServicesBinding)
        {
            super(itemDoctorServicesBinding.getRoot());
            this.itemDoctorServicesBinding= itemDoctorServicesBinding;

            itemDoctorServicesBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggleExpansion(getBindingAdapterPosition());
                }
            });
        }

    }
}
