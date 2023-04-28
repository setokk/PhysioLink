package com.mobile.physiolink.ui.doctor;
// TODO Na ginei katallhlh allagh tou arxeiou molis ginei h diasyndesh me ta dedomena ths bashs

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.physiolink.R;

public class AdapterForPatientList extends RecyclerView.Adapter<AdapterForPatientList.MyViewHolder> {
    String data1[],data2[],data3[],data4[];

    public AdapterForPatientList(Fragment doctorPatientsFragment, String s1[], String s2[], String s3[], String s4[]){
        Context ct = doctorPatientsFragment.getContext();
        data1=s1;
        data2=s2;
        data3=s3;
        data4=s4;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_doctor_patients,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.onoma.setText(data1[position]);
        holder.epitheto.setText(data2[position]);
        holder.amka.setText(data3[position]);
        holder.kinito.setText(data4[position]);
    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView onoma, epitheto, amka, kinito;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            onoma= itemView.findViewById(R.id.patientNameDoctor);
            epitheto= itemView.findViewById(R.id.patientSurnameDoctor);
            amka= itemView.findViewById(R.id.AMKApatientDoctor);
            kinito=itemView.findViewById(R.id.phoneNumberPatientDoctor);
        }
    }
}
