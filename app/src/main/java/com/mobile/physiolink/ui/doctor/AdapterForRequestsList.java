package com.mobile.physiolink.ui.doctor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.physiolink.R;

public class AdapterForRequestsList extends RecyclerView.Adapter<AdapterForRequestsList.MyViewHolder> {

    String name[],surname[],amka[],date[],time[],problem[],requestTime[];

    public AdapterForRequestsList(Fragment fragment, String s1[],String s2[],String s3[],String s4[],String s5[],
                                  String s6[],String s7[]){
        Context context = fragment.getContext();
        name=s1;
        surname=s2;
        amka=s3;
        date=s4;
        time=s5;
        problem=s6;
        requestTime=s7;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_doctor_request,parent,false);
        return new MyViewHolder(itemView);
    }

    public void onBindViewHolder(MyViewHolder holder, int position){
        holder.onoma.setText(name[position]);
        holder.epitheto.setText(surname[position]);
        holder.amka.setText(this.amka[position]);
        holder.imerominia.setText(date[position]);
        holder.ora.setText(time[position]);
        holder.provlima.setText(problem[position]);
        holder.oraAitimatos.setText(requestTime[position]);
    }

    @Override
    public int getItemCount() {
        return name.length;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView onoma,epitheto,amka,imerominia,ora,provlima,oraAitimatos;
        public MyViewHolder(View itemView){
            super(itemView);
            onoma=itemView.findViewById(R.id.requestNameDoctor);
            epitheto=itemView.findViewById(R.id.requestSurnameDoctor);
            amka=itemView.findViewById(R.id.requestAMKA);
            imerominia=itemView.findViewById(R.id.requestAppointmentDate);
            ora=itemView.findViewById(R.id.requestAppointmentTime);
            provlima=itemView.findViewById(R.id.requestProblem);
            oraAitimatos=itemView.findViewById(R.id.requestTime);
        }
    }
}
