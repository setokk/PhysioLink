package com.mobile.physiolink.ui.doctor.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.physiolink.databinding.ItemDoctorRequestBinding;
import com.mobile.physiolink.model.appointment.Appointment;
import com.mobile.physiolink.model.user.singleton.UserHolder;
import com.mobile.physiolink.service.api.API;
import com.mobile.physiolink.service.api.RequestFacade;
import com.mobile.physiolink.ui.doctor.OnButtonClickListener;
import com.mobile.physiolink.util.date.TimeFormatter;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AdapterForRequests extends RecyclerView.Adapter<AdapterForRequests.MyViewHolder>
        implements OnButtonClickListener
{
    // Constant variables to check if the accept or the reject button is pressed
    public static final int ACCEPT=1;
    public static final int REJECT=2;

    private Appointment[] appointments;

    public AdapterForRequests()
    {
        appointments = new Appointment[0];
    }

    public void setAppointments(Appointment[] appointments)
    {
        this.appointments = appointments;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        ItemDoctorRequestBinding binding = ItemDoctorRequestBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding, this);
    }

    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        holder.binding.requestNameDoctor
                .setText(appointments[position].getPatName());
        holder.binding.requestSurnameDoctor
                .setText(appointments[position].getPatSurname());
        holder.binding.requestAMKA
                .setText(appointments[position].getPatAmka());
        holder.binding.requestAppointmentDate
                .setText(appointments[position].getDate().replace('-', '/'));
        holder.binding.requestAppointmentTime
                .setText(TimeFormatter.formatToPM_AM(appointments[position].getHour()));
        holder.binding.requestProblem
                .setText(appointments[position].getMessage());
        holder.binding.requestProblemExpanded
                .setText(appointments[position].getMessage());
        holder.isExpanded = false;
    }

    @Override
    public int getItemCount() {
        return appointments.length;
    }

    // Function used to remove items from the requestList when a request is either accepted or rejected
    public void remove(int position)
    {
        Appointment[] newAppointments = new Appointment[appointments.length-1];
        for(int i=0; i<position; i++){
            newAppointments[i]=appointments[i];
        }
        for(int i=position+1; i<appointments.length; i++){
            newAppointments[i-1]=appointments[i];
        }
        appointments = newAppointments;
    }

    @Override
    public void onButtonClicked(int position,int id)
    {
        if(id==ACCEPT)
        {
            HashMap<String, String> keyValues = new HashMap<>(5);
            keyValues.put("appointment_id", String.valueOf(appointments[position].getId()));
            keyValues.put("date", appointments[position].getDate());
            keyValues.put("doctor_name", UserHolder.doctor().getName());
            keyValues.put("doctor_surname", UserHolder.doctor().getSurname());
            keyValues.put("doctor_phone_number", UserHolder.doctor().getPhoneNumber());

            RequestFacade.postRequest(API.ACCEPT_APPOINTMENT, keyValues, new Callback()
            {
                @Override
                public void onFailure(Call call, IOException e) {
                    call.cancel();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                }
            });
        }
        else
        {
            HashMap<String, String> keyValues = new HashMap<>(6);
            keyValues.put("appointment_id", String.valueOf(appointments[position].getId()));
            keyValues.put("date", appointments[position].getDate());
            keyValues.put("reason", "reason");
            keyValues.put("doctor_name", UserHolder.doctor().getName());
            keyValues.put("doctor_surname", UserHolder.doctor().getSurname());
            keyValues.put("doctor_phone_number", UserHolder.doctor().getPhoneNumber());

            RequestFacade.postRequest(API.DECLINE_PAYMENT, keyValues, new Callback()
            {
                @Override
                public void onFailure(Call call, IOException e) {
                    call.cancel();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                }
            });
        }

        remove(position);
        notifyItemRemoved(position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ItemDoctorRequestBinding binding;
        boolean isExpanded = false;

        public MyViewHolder(ItemDoctorRequestBinding binding, OnButtonClickListener listener)
        {
            super(binding.getRoot());
            this.binding = binding;

            //Switch to extended on click
            binding.getRoot().setOnClickListener(view ->
            {
                if(isExpanded)
                {
                    binding.requestProblemExpanded.setVisibility(View.GONE);
                    binding.requestProblem.setVisibility(View.VISIBLE);
                }else
                {
                    binding.requestProblemExpanded.setVisibility(View.VISIBLE);
                    binding.requestProblem.setVisibility(View.INVISIBLE);
                }
                isExpanded = !isExpanded;
            });

            binding.requestAcceptButton.setOnClickListener(view ->
            {
                Toast.makeText(binding.getRoot().getContext(),"Το ραντεβού αποδέχθηκε!"
                        ,Toast.LENGTH_SHORT).show();

                System.out.println(listener != null);
                System.out.println(getBindingAdapterPosition());
                if (listener != null) {
                    int position = getAbsoluteAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onButtonClicked(position,ACCEPT);
                    }
                }
            });
            binding.requestRejectButton.setOnClickListener(view ->
            {
                Toast.makeText(binding.getRoot().getContext(),"Το ραντεβού απορρίφθηκε!"
                        ,Toast.LENGTH_SHORT).show();
                if (listener != null) {
                    int position = getAbsoluteAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onButtonClicked(position,REJECT);
                    }
                }

            });
        }
    }
}
