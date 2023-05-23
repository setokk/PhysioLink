package com.mobile.physiolink.ui.patient.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.ItemPatientHistoryBinding;

import java.util.Arrays;

public class AdapterForHistoryPatient extends RecyclerView.Adapter<AdapterForHistoryPatient.MyViewHolder> {

    String date[];
    String time[];
    String description[];
    String service[];
    String price[];

    private boolean displayAllItems;

    private Boolean[] isExpanded;



    public AdapterForHistoryPatient(String dateH[], String timeH[], String descriptionH[],
                                    String serviceH[], String priceH[], int recyclerViewId){

        date = dateH;
        time = timeH;
        description = descriptionH;
        service = serviceH;
        price = priceH;
        this.displayAllItems= recyclerViewId== R.id.historyRecyclerview;

        isExpanded = new Boolean[date.length];
        Arrays.fill(isExpanded, false);

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

        boolean isItemExpanded = isExpanded[position];
        boolean hasContent = holder.itemHistoryBinding.appointmentDescriptionPatientHistory.length() > 0;

        holder.itemHistoryBinding.appointmentDescriptionPatientHistory.setText(hasContent ? description[position] : "-");

        // Set the initial state based on the expanded flag
            if (isItemExpanded) {
                holder.itemHistoryBinding.appointmentDescriptionPatientHistory.setMaxLines(Integer.MAX_VALUE);
                holder.itemHistoryBinding.appointmentDescriptionPatientHistory.setEllipsize(null);
            } else {
                holder.itemHistoryBinding.appointmentDescriptionPatientHistory.setMaxLines(1);
                holder.itemHistoryBinding.appointmentDescriptionPatientHistory.setEllipsize(TextUtils.TruncateAt.END);
            }

    }

    private void toggleExpansion(int position) {
        isExpanded[position] = !isExpanded[position];
        notifyItemChanged(position);
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


            // Set click listener on the entire item view
            itemHistoryBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggleExpansion(getBindingAdapterPosition());
                }
            });
        }
    }
}


