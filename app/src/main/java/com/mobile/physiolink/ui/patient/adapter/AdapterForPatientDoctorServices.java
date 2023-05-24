package com.mobile.physiolink.ui.patient.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.physiolink.databinding.ItemPatientDoctorServicesBinding;

import java.util.Arrays;

public class AdapterForPatientDoctorServices extends RecyclerView.Adapter<AdapterForPatientDoctorServices.MyViewHolder>{

    String service[];
    String description[];
    String price[];
    private Boolean[] isExpanded;

    public AdapterForPatientDoctorServices(String s1[], String s2[], String s3[]){
        this.service=s1;
        this.description=s2;
        this.price=s3;

        isExpanded = new Boolean[service.length];
        Arrays.fill(isExpanded, false);
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

        boolean isItemExpanded = isExpanded[position];
        boolean hasContent = holder.itemDoctorServicesBinding.patientDoctorServiceDescription.length() > 0;

        holder.itemDoctorServicesBinding.patientDoctorServiceDescription.setText(hasContent ? description[position] : "-");
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
        return service.length;
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
