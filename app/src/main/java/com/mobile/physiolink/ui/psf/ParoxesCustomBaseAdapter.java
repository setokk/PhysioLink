package com.mobile.physiolink.ui.psf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.physiolink.R;

public class ParoxesCustomBaseAdapter extends RecyclerView.Adapter<ParoxesCustomBaseAdapter.MyViewHolder> {


    String listParoxesName[];
    String listParoxesId[];
    String listParoxesCost[];
    String listParoxesDescription[];



    public ParoxesCustomBaseAdapter(Fragment ParoxesFragment, String k1[], String k2[], String k3[], String k4[]){
        Context ct =ParoxesFragment.getContext();
        listParoxesName=k1;
        listParoxesId=k2;
        listParoxesCost=k3;
        listParoxesDescription=k4;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_paroxes,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nameParoxes.setText(listParoxesName[position]);
        holder.id.setText(listParoxesId[position]);
        holder.cost.setText(listParoxesCost[position]);
        holder.des.setText(listParoxesDescription[position]);


    }

    @Override
    public int getItemCount() {

        return listParoxesName.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameParoxes, id, cost,des;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameParoxes= itemView.findViewById(R.id.paroxiName);
            id= itemView.findViewById(R.id.paroxiId);
            cost= itemView.findViewById(R.id.paroxiCost);
            des= itemView.findViewById(R.id.paroxiDescriptio);


        }
    }
}