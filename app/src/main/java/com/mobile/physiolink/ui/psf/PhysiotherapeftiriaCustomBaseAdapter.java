package com.mobile.physiolink.ui.psf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.physiolink.R;

public class PhysiotherapeftiriaCustomBaseAdapter extends RecyclerView.Adapter<PhysiotherapeftiriaCustomBaseAdapter.ViewHolder> {

    Context context;
    String[] listDoctorName;
    String[] listDoctorOffice;
    String[] listDoctorAddress;
    int[] listDoctorImg;
    LayoutInflater inflater;

    public PhysiotherapeftiriaCustomBaseAdapter(Context context, String[] listDoctorName, String[] listDoctorOffice, String[] listDoctorAddress, int[] listDoctorImg) {
        this.context = context;
        this.listDoctorName = listDoctorName;
        this.listDoctorOffice = listDoctorOffice;
        this.listDoctorAddress = listDoctorAddress;
        this.listDoctorImg = listDoctorImg;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_doc, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.doctorNameView.setText(listDoctorName[position]);
        holder.doctorOfficeView.setText(listDoctorOffice[position]);
        holder.doctorAddressView.setText(listDoctorAddress[position]);
        holder.doctorImg.setImageResource(listDoctorImg[position]);
    }

    @Override
    public int getItemCount() {
        return listDoctorName.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView doctorNameView;
        TextView doctorOfficeView;
        TextView doctorAddressView;
        ImageView doctorImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            doctorNameView = itemView.findViewById(R.id.doctorName);
            doctorOfficeView = itemView.findViewById(R.id.doctorOffice);
            doctorAddressView = itemView.findViewById(R.id.doctorAddress);
            doctorImg = itemView.findViewById(R.id.doctorImage);
        }
    }
}
