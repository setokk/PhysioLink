package com.mobile.physiolink.ui.patient.adaptor;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.ItemPatientHistoryBinding;

public class AdaptorForHistoryPatient extends RecyclerView.Adapter<AdaptorForHistoryPatient.MyViewHolder> {

    String date[];
    String time[];
    String description[];
    String service[];
    String price[];

    private boolean displayAllItems;



    public AdaptorForHistoryPatient(String dateH[], String timeH[], String descriptionH[],
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
    public AdaptorForHistoryPatient.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        ItemPatientHistoryBinding itemHistoryBinding = ItemPatientHistoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent,false);
        return new AdaptorForHistoryPatient.MyViewHolder(itemHistoryBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptorForHistoryPatient.MyViewHolder holder, int position)
    {
        holder.itemHistoryBinding.serviceNamePatientHistory.setText(service[position]);
        holder.itemHistoryBinding.apointmentDatePatientHistory.setText(date[position]);
        holder.itemHistoryBinding.apointmentTimePatientHistory.setText(time[position]);
        holder.itemHistoryBinding.apointmentDescriptionPatientHistory.setText(description[position]);
        holder.itemHistoryBinding.servicePrisePatientHistory.setText(price[position]);

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


