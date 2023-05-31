package com.mobile.physiolink.ui.psf.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.physiolink.databinding.ItemDocBinding;
import com.mobile.physiolink.model.user.Doctor;
import com.mobile.physiolink.ui.doctor.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class AdapterForClinics extends RecyclerView.Adapter<AdapterForClinics.MyViewHolder>
{
    private List<Doctor> doctors;
    private OnItemClickListener<Doctor> listener;

    public AdapterForClinics()
    {
        doctors = new ArrayList<>(0);
    }

    public void setDoctors(List<Doctor> doctors)
    {
        this.doctors = doctors;
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