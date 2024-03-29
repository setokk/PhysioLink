package com.mobile.physiolink.ui.doctor.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.physiolink.databinding.ItemDoctorPatientsBinding;
import com.mobile.physiolink.model.appointment.Appointment;
import com.mobile.physiolink.model.user.Patient;
import com.mobile.physiolink.ui.doctor.OnItemClickListener;
import com.mobile.physiolink.util.image.ProfileImageProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdapterForPatients extends RecyclerView.Adapter<AdapterForPatients.MyViewHolder> implements Filterable
{
    private List<Patient> patients;
    private List<Patient> patientsFull;
    private OnItemClickListener<Patient> listener;

    public AdapterForPatients()
    {
        patients = new ArrayList<>();
    }

    public void setPatients(List<Patient> patients)
    {
        this.patients = patients;
        this.patientsFull = new ArrayList<>(patients);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener<Patient> listener)
    {
        this.listener = listener;
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
        ProfileImageProvider.setImageForUser(holder.binding.patientDoctorProfilePic,
                patients.get(position));
        holder.binding.patientNameDoctor
                .setText(patients.get(position).getName());
        holder.binding.patientSurnameDoctor
                .setText(patients.get(position).getSurname());
        holder.binding.AMKApatientDoctor
                .setText(patients.get(position).getAmka());
        holder.binding.phoneNumberPatientDoctor
                .setText(patients.get(position).getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }

    @Override
    public Filter getFilter() {
        return patientsFilter;
    }

    private final Filter patientsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Patient> filteredList = new ArrayList<>();
            if(charSequence == null || charSequence.length() == 0){
                filteredList.addAll(Optional.ofNullable(patientsFull)
                        .orElse(new ArrayList<>(0)));
            }
            else{
                String filterPattern = charSequence.toString().toUpperCase().trim();
                for(Patient patient: patientsFull){
                    if(patient.getName().toUpperCase().contains(filterPattern) ||
                       patient.getSurname().toUpperCase().contains(filterPattern) ||
                       patient.getAmka().toUpperCase().contains(filterPattern) ||
                       patient.getPhoneNumber().toUpperCase().contains(filterPattern)){
                        filteredList.add(patient);
                    }

                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            patients.clear();
            patients.addAll(Optional.ofNullable((List) filterResults.values)
                    .orElse(new ArrayList<>(0)));
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener
    {
        ItemDoctorPatientsBinding binding;

        public MyViewHolder(ItemDoctorPatientsBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            int position = getAbsoluteAdapterPosition();
            if (position != RecyclerView.NO_POSITION && listener != null)
            {
                listener.onItemClick(patients.get(position));
            }
        }
    }
}
