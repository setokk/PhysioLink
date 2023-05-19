package com.mobile.physiolink.ui.doctor.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.mobile.physiolink.R;
import com.mobile.physiolink.ui.doctor.OnButtonClickListener;

public class AdapterForRequests extends RecyclerView.Adapter<AdapterForRequests.MyViewHolder> implements OnButtonClickListener {

    //constant variables to check if the accept or the reject button is pressed
    public static final int ACCEPT=1;
    public static final int REJECT=2;

    //problemExtended is used in order to be able to see the whole problem of the patient
    String[] name,surname,amka,date,time,problem,requestTime,problemExtended;


    public AdapterForRequests(Fragment fragment, String[] s1, String[] s2, String[] s3, String[] s4, String[] s5,
                              String[] s6, String[] s7){
        Context context = fragment.getContext();

        name=s1;
        surname=s2;
        amka=s3;
        date=s4;
        time=s5;
        problem=s6;
        problemExtended=s6;
        requestTime=s7;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_doctor_request,parent,false);
        return new MyViewHolder(itemView, this);
    }

    public void onBindViewHolder(MyViewHolder holder, int position){
        holder.onoma.setText(name[position]);
        holder.epitheto.setText(surname[position]);
        holder.amka.setText(this.amka[position]);
        holder.imerominia.setText(date[position]);
        holder.ora.setText(time[position]);
        holder.provlima.setText(problem[position]);
        holder.oraAitimatos.setText(requestTime[position]);
        holder.provlimaExtended.setText(problemExtended[position]);
        holder.isExpanded = false;
    }

    @Override
    public int getItemCount() {
        return name.length;
    }

    //function used to remove items from the requestList when a request is either accepted or rejected
    public void remove(String s1[],int position){
        String[] s2 = new String[s1.length-1];
        for(int i=0; i<position;i++){
            s2[i]=s1[i];
        }
        for(int i=position+1;i<s1.length;i++){
            s2[i-1]=s1[i];
        }
        s1=new String[s2.length];
        s1=s2;
    }

    //function which handles what happens when a button is clicked
    //TODO handle what happens when each button is pressed
    @Override
    public void onButtonClicked(int position,int id) {
        if(id==ACCEPT){
            System.out.println("accept button pressed");
        }else{
            System.out.println("reject button pressed");
        }
        remove(name,position);
        remove(surname,position);
        remove(amka,position);
        remove(date,position);
        remove(problem,position);
        remove(problemExtended,position);
        remove(time,position);
        remove(requestTime,position);
        notifyItemRemoved(position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private Button acceptButton;
        private Button rejectButton;
        boolean isExpanded = false;
        TextView onoma,epitheto,amka,imerominia,ora,provlima,oraAitimatos,provlimaExtended;
        public MyViewHolder(View itemView, OnButtonClickListener listener){
            super(itemView);

            onoma=itemView.findViewById(R.id.requestNameDoctor);
            epitheto=itemView.findViewById(R.id.requestSurnameDoctor);
            amka=itemView.findViewById(R.id.requestAMKA);
            imerominia=itemView.findViewById(R.id.requestAppointmentDate);
            ora=itemView.findViewById(R.id.requestAppointmentTime);
            provlima=itemView.findViewById(R.id.requestProblem);
            oraAitimatos=itemView.findViewById(R.id.requestTime);
            provlimaExtended=itemView.findViewById(R.id.requestProblemExpanded);

            //Switch to extended on click
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(isExpanded){
                        provlimaExtended.setVisibility(View.GONE);
                        provlima.setVisibility(View.VISIBLE);
                    }else{
                        provlimaExtended.setVisibility(View.VISIBLE);
                        provlima.setVisibility(View.INVISIBLE);
                    }
                    isExpanded = !isExpanded;
                }
            });

            itemView.findViewById(R.id.requestAcceptButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(itemView.getContext(),"Appointment Accepted! " + getAbsoluteAdapterPosition(),Toast.LENGTH_SHORT).show();
                    if (listener != null) {
                        int position = getBindingAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onButtonClicked(position,ACCEPT);
                        }
                    }


                }
            });
            itemView.findViewById(R.id.requestRejectButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(itemView.getContext(),"Appointment Rejected! " + getAbsoluteAdapterPosition(),Toast.LENGTH_SHORT).show();
                    if (listener != null) {
                        int position = getAbsoluteAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onButtonClicked(position,REJECT);
                        }
                    }

                }
            });
        }
    }
}
