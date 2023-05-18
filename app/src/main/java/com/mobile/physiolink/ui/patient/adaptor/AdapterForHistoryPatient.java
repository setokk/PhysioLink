package com.mobile.physiolink.ui.patient.adaptor;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.physiolink.databinding.ItemPatientHistoryBinding;

public class AdapterForHistoryPatient extends RecyclerView.Adapter<AdapterForHistoryPatient.MyViewHolder> {

    String date[];
    String time[];
    String description[];
    String service[];
    String price[];



    public AdapterForHistoryPatient(String dateH[], String timeH[], String descriptionH[],
                                    String serviceH[], String priceH[]){

        date = dateH;
        time = timeH;
        description = descriptionH;
        service = serviceH;
        price = priceH;

    }

    @NonNull
    @Override
    public AdapterForHistoryPatient.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        ItemPatientHistoryBinding itemHistoryBinding = ItemPatientHistoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent,false);
        return new AdapterForHistoryPatient.MyViewHolder(itemHistoryBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForHistoryPatient.MyViewHolder holder, int position)
    {
        holder.itemHistoryBinding.serviceNamePatientHistory.setText(service[position]);
        holder.itemHistoryBinding.apointmentDatePatientHistory.setText(date[position]);
        holder.itemHistoryBinding.apointmentTimePatientHistory.setText(time[position]);
        holder.itemHistoryBinding.apointmentDescriptionPatientHistory.setText(description[position]);
        holder.itemHistoryBinding.servicePrisePatientHistory.setText(price[position]);



    }

    @Override
    public int getItemCount() {
        return service.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ItemPatientHistoryBinding itemHistoryBinding;

        public MyViewHolder(ItemPatientHistoryBinding itemHistoryBinding)
        {
            super(itemHistoryBinding.getRoot());
            this.itemHistoryBinding= itemHistoryBinding;
        }
    }
}


