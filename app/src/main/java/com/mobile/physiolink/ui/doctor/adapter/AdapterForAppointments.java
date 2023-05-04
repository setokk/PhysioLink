package com.mobile.physiolink.ui.doctor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.physiolink.R;
import com.mobile.physiolink.ui.doctor.DoctorHomeFragment;

public class AdapterForAppointments extends RecyclerView.Adapter <AdapterForAppointments.MyViewHolder> {

    String d1[], d2[], d3[], d4[];

    public AdapterForAppointments(DoctorHomeFragment fragment_doctor_home, String[] d1, String[] d2, String[] d3, String[] d4) {

        Context context = fragment_doctor_home.getContext();
        this.d1 = d1;
        this.d2 = d2;
        this.d3 = d3;
        this.d4 = d4;
    }

    @NonNull
    @Override
    public AdapterForAppointments.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_doctor_appointment,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return d1.length;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForAppointments.MyViewHolder holder, int position) {


        holder.tvName.setText(d1[position]);
        holder.tvTime.setText(d2[position]);
        holder.tvService.setText(d3[position]);
        holder.imageview.setImageResource(R.drawable.boy);
        //holder.tvSurname.setText(d4[position]);

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageview;
        TextView tvName, tvSurname, tvTime, tvService;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageview = itemView.findViewById(R.id.profile_image);
            tvName = itemView.findViewById(R.id.appointment_patient_text);
            tvTime = itemView.findViewById(R.id.appointment_time_text);
            tvService = itemView.findViewById(R.id.appointment_service_text);
        }
    }
}
