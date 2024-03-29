package com.mobile.physiolink.ui.doctor.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.physiolink.databinding.ItemDoctorServicesBinding;
import com.mobile.physiolink.model.service.Service;
import com.mobile.physiolink.ui.doctor.OnLongItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class AdapterForDoctorServices extends RecyclerView.Adapter<AdapterForDoctorServices.MyViewHolder> implements Filterable
{
    private List<Service> services;
    private List<Service> servicesFull;

    private boolean[] isExpanded;
    private OnLongItemClickListener<Service> listener;

    public AdapterForDoctorServices()
    {
        services = new ArrayList<>();
        isExpanded = new boolean[services.size()];
    }

    public void setOnLongItemClickListener(OnLongItemClickListener<Service> listener){
        this.listener=listener;
    }
    public void setServices(List<Service> services)
    {
        this.services = services;
        this.servicesFull = new ArrayList<>(services);
        isExpanded = new boolean[services.size()];
        Arrays.fill(isExpanded, false);
        notifyDataSetChanged();
    }

    public List<Service> getServices() {
        return services;
    }

    @NonNull
    @Override
    public AdapterForDoctorServices.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDoctorServicesBinding itemDoctorServicesBinding = ItemDoctorServicesBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AdapterForDoctorServices.MyViewHolder(itemDoctorServicesBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForDoctorServices.MyViewHolder holder, int position)
    {
        holder.itemDoctorServicesBinding.serviceNameDoctor
                .setText(services.get(position).getTitle());
        holder.itemDoctorServicesBinding.serviceDescriptionDoctor
                .setText(services.get(position).getDescription());
        holder.itemDoctorServicesBinding.servicePriceDoctor
                .setText(String.valueOf((int) services.get(position).getPrice()));

        boolean isItemExpanded = isExpanded[position];

        // Set the initial state based on the expanded flag
        if (isItemExpanded) {
            holder.itemDoctorServicesBinding.serviceDescriptionDoctor.setMaxLines(Integer.MAX_VALUE);
            holder.itemDoctorServicesBinding.serviceDescriptionDoctor.setEllipsize(null);
        } else {
            holder.itemDoctorServicesBinding.serviceDescriptionDoctor.setMaxLines(2);
            holder.itemDoctorServicesBinding.serviceDescriptionDoctor.setEllipsize(TextUtils.TruncateAt.END);
        }
    }

    private void toggleExpansion(int position) {
        isExpanded[position] = !isExpanded[position];
        notifyItemChanged(position);
    }


    @Override
    public int getItemCount() {
        return services.size();
    }

    @Override
    public Filter getFilter() {
        return servicesFilter;
    }

    private Filter servicesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Service> filteredList = new ArrayList<>();
            if(charSequence == null || charSequence.length() == 0){
                filteredList.addAll(Optional.ofNullable(servicesFull)
                        .orElse(new ArrayList<>(0)));
            }
            else{
                String filterPattern = charSequence.toString().toUpperCase().trim();
                for(Service service: servicesFull){
                    if(service.getTitle().toUpperCase().contains(filterPattern) ||
                       service.getDescription().toUpperCase().contains(filterPattern)){
                        filteredList.add(service);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            services.clear();
            services.addAll(Optional.ofNullable((List) filterResults.values)
                    .orElse(new ArrayList<>(0)));
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemDoctorServicesBinding itemDoctorServicesBinding;

        public MyViewHolder(ItemDoctorServicesBinding itemDoctorServicesBinding) {
            super(itemDoctorServicesBinding.getRoot());
            this.itemDoctorServicesBinding = itemDoctorServicesBinding;

            // Set click listener on the entire item view
            itemDoctorServicesBinding.getRoot().setOnClickListener(view ->
                    toggleExpansion(getBindingAdapterPosition()));

            itemDoctorServicesBinding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = getAbsoluteAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null)
                    {
                        listener.onLongItemClick(services.get(position));
                    }
                    return true;
                }
            });
        }
    }
}
