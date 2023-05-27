package com.mobile.physiolink.ui.doctor.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.physiolink.databinding.ItemDoctorPatientsBinding;
import com.mobile.physiolink.model.user.Patient;

public class AdapterForPatients extends RecyclerView.Adapter<AdapterForPatients.MyViewHolder>
{
    private Patient[] patients;

    public AdapterForPatients()
    {
        patients = new Patient[0];
    }

    public void setPatients(Patient[] patients)
    {
        this.patients = patients;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        ItemDoctorPatientsBinding binding = ItemDoctorPatientsBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        holder.binding.patientNameDoctor
                .setText(patients[position].getName());
        holder.binding.patientSurnameDoctor
                .setText(patients[position].getSurname());
        holder.binding.AMKApatientDoctor
                .setText(patients[position].getAmka());
        holder.binding.phoneNumberPatientDoctor
                .setText(patients[position].getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return patients.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ItemDoctorPatientsBinding binding;

        public MyViewHolder(ItemDoctorPatientsBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
