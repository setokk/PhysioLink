package com.mobile.physiolink.ui.patient.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.ItemPatientHistoryBinding;

public class AdapterForHistoryPatient extends RecyclerView.Adapter<AdapterForHistoryPatient.MyViewHolder> {

    String date[];
    String time[];
    String description[];
    String service[];
    String price[];

    private boolean displayAllItems;



    public AdapterForHistoryPatient(String dateH[], String timeH[], String descriptionH[],
                                    String serviceH[], String priceH[], int recyclerViewId){

        date = dateH;
        time = timeH;
        description = descriptionH;
        service = serviceH;
        price = priceH;
        this.displayAllItems= recyclerViewId== R.id.historyRecyclerview;

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
        holder.itemHistoryBinding.appointmentDatePatientHistory.setText(date[position]);
        holder.itemHistoryBinding.appointmentTimePatientHistory.setText(time[position]);
        holder.itemHistoryBinding.appointmentDescriptionPatientHistory.setText(description[position]);
        holder.itemHistoryBinding.servicePricePatientHistory.setText(price[position]);

    }

    @Override
    public int getItemCount() {
        if (displayAllItems) {
            return service.length;
        } else {
            return Math.min(service.length, 1);
        }
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


