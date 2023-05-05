package com.mobile.physiolink.ui.doctor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.physiolink.R;

public class AdapterForPatientHistory extends RecyclerView.Adapter<AdapterForPatientHistory.MyViewHolder> {

    String data1[],data2[],data3[];

    public AdapterForPatientHistory(Context ct, String s1[], String s2[], String s3[]){
        Context context = ct;
        data1=s1;
        data2=s2;
        data3=s3;
    }

    @NonNull
    @Override
    public AdapterForPatientHistory.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_doctor_patient_history_services,parent,false);
        return new AdapterForPatientHistory.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForPatientHistory.MyViewHolder holder, int position) {
        holder.onoma.setText(data1[position]);
        holder.date.setText(data2[position]);
        holder.timh.setText(data3[position]);
    }

    @Override
    public int getItemCount() { return data1.length; }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView onoma, date, timh;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            onoma= itemView.findViewById(R.id.servicePatientHistoryNameDoctor);
            date= itemView.findViewById(R.id.servicePatientHistoryDateDoctor);
            timh= itemView.findViewById(R.id.servicePatientHistoryPriceDoctor);
        }
    }
}
