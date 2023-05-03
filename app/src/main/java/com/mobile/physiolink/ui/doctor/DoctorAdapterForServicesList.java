package com.mobile.physiolink.ui.doctor;
// TODO Na ginei katallhlh allagh tou arxeiou molis ginei h diasyndesh me ta dedomena ths bashs

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.physiolink.R;

public class DoctorAdapterForServicesList extends RecyclerView.Adapter<DoctorAdapterForServicesList.MyViewHolder> {
    String data1[],data2[],data3[];

    public DoctorAdapterForServicesList(Context ct, String s1[], String s2[], String s3[]){
        Context context = ct;
        data1=s1;
        data2=s2;
        data3=s3;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_doctor_services,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.onoma.setText(data1[position]);
        holder.perigrafh.setText(data2[position]);
        holder.timh.setText(data3[position]);
    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView onoma, perigrafh, timh;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            onoma= itemView.findViewById(R.id.serviceNameDoctor);
            perigrafh= itemView.findViewById(R.id.serviceDescriptionDoctor);
            timh= itemView.findViewById(R.id.servicePriceDoctor);
        }
    }
}
