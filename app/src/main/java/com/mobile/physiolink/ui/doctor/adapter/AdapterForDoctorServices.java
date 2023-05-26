package com.mobile.physiolink.ui.doctor.adapter;
// TODO Na ginei katallhlh allagh tou arxeiou molis ginei h diasyndesh me ta dedomena ths bashs

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.ItemDoctorServicesBinding;
import com.mobile.physiolink.databinding.ItemPatientHistoryBinding;
import com.mobile.physiolink.ui.patient.adapter.AdapterForHistoryPatient;

import java.util.Arrays;

public class AdapterForDoctorServices extends RecyclerView.Adapter<AdapterForDoctorServices.MyViewHolder> {
    String data1[], data2[], data3[];

    private boolean displayAllItems;

    private Boolean[] isExpanded;

    public AdapterForDoctorServices(String s1[], String s2[], String s3[], int recyclerDoctorServiceView) {

        data1 = s1;
        data2 = s2;
        data3 = s3;

        this.displayAllItems = recyclerDoctorServiceView == R.id.servicesListDoctor;

        isExpanded = new Boolean[data1.length];
        Arrays.fill(isExpanded, false);
    }

    @NonNull
    @Override
    public AdapterForDoctorServices.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDoctorServicesBinding itemDoctorServicesBinding = ItemDoctorServicesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AdapterForDoctorServices.MyViewHolder(itemDoctorServicesBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForDoctorServices.MyViewHolder holder, int position) {
        holder.itemDoctorServicesBinding.serviceNameDoctor.setText(data1[position]);
        holder.itemDoctorServicesBinding.serviceDescriptionDoctor.setText(data2[position]);
        holder.itemDoctorServicesBinding.servicePriceDoctor.setText(data3[position]);

        boolean isItemExpanded = isExpanded[position];
        boolean hasContent = holder.itemDoctorServicesBinding.serviceDescriptionDoctor.length() > 0;

        holder.itemDoctorServicesBinding.serviceDescriptionDoctor.setText(hasContent ? data2[position] : "-");

        // Set the initial state based on the expanded flag
        if (isItemExpanded) {
            holder.itemDoctorServicesBinding.serviceDescriptionDoctor.setMaxLines(Integer.MAX_VALUE);
            holder.itemDoctorServicesBinding.serviceDescriptionDoctor.setEllipsize(null);
        } else {
            holder.itemDoctorServicesBinding.serviceDescriptionDoctor.setMaxLines(1);
            holder.itemDoctorServicesBinding.serviceDescriptionDoctor.setEllipsize(TextUtils.TruncateAt.END);
        }


    }

    private void toggleExpansion(int position) {
        isExpanded[position] = !isExpanded[position];
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ItemDoctorServicesBinding itemDoctorServicesBinding;

        public MyViewHolder(ItemDoctorServicesBinding itemDoctorServicesBinding) {
            super(itemDoctorServicesBinding.getRoot());
            this.itemDoctorServicesBinding = itemDoctorServicesBinding;

            // Set click listener on the entire item view
            itemDoctorServicesBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggleExpansion(getBindingAdapterPosition());
                }
            });

        }
    }
}
