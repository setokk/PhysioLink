package com.mobile.physiolink.ui.psf.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.physiolink.databinding.ItemDocBinding;
import com.mobile.physiolink.model.user.Doctor;
import com.mobile.physiolink.ui.doctor.OnItemClickListener;
import com.mobile.physiolink.util.image.ProfileImageProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdapterForClinics extends RecyclerView.Adapter<AdapterForClinics.MyViewHolder> implements Filterable
{
    private List<Doctor> doctors;
    private List<Doctor> doctorsFull;
    private OnItemClickListener<Doctor> listener;

    public AdapterForClinics()
    {
        doctors = new ArrayList<>(0);
    }

    public void setDoctors(List<Doctor> doctors)
    {
        this.doctors = doctors;
        this.doctorsFull = new ArrayList<>(doctors);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener<Doctor> listener)
    {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        ItemDocBinding itemDocBinding = ItemDocBinding.inflate(LayoutInflater.from(parent.getContext()), parent,false);
        return new MyViewHolder(itemDocBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        int imageID = ProfileImageProvider
                .getProfileImage(doctors.get(position).getName());
        holder.itemDocBinding.doctorImage.setImageResource(imageID);
        holder.itemDocBinding.doctorName.setText(new StringBuilder()
                .append(doctors.get(position).getName())
                .append(" ")
                .append(doctors.get(position).getSurname())
                .toString());
        holder.itemDocBinding.doctorClinic.setText(doctors.get(position).getPhysioName());
        holder.itemDocBinding.doctorCity.setText(doctors.get(position).getCity());
    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    @Override
    public Filter getFilter() {
        return doctorsFilter;
    }

    private Filter doctorsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Doctor> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(doctorsFull);
            } else {
                String filterPattern = charSequence.toString().toUpperCase().trim();
                for(Doctor doctor: doctorsFull){
                    if(doctor.getName().toUpperCase().contains(filterPattern) ||
                        doctor.getSurname().toUpperCase().contains(filterPattern) ||
                        doctor.getCity().toUpperCase().contains(filterPattern)){
                        filteredList.add(doctor);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            doctors.clear();
            doctors.addAll(Optional.ofNullable((List) filterResults.values)
                    .orElse(new ArrayList<>(0)));
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener
    {
        ItemDocBinding itemDocBinding;

        public MyViewHolder(ItemDocBinding itemDocBinding)
        {
            super(itemDocBinding.getRoot());
            this.itemDocBinding = itemDocBinding;
            this.itemDocBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAbsoluteAdapterPosition();
            if (position != RecyclerView.NO_POSITION && listener != null)
            {
                listener.onItemClick(doctors.get(position));
            }
        }
    }
}