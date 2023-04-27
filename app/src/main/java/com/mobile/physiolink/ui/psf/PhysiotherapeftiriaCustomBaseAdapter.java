package com.mobile.physiolink.ui.psf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.mobile.physiolink.R;

public class PhysiotherapeftiriaCustomBaseAdapter extends BaseAdapter {

    Context context;
    String listDoctorName[];
    String listDoctorOffice[];
    String listDoctorAddress[];
    int listDoctorImg[];
    LayoutInflater inflater;

    public PhysiotherapeftiriaCustomBaseAdapter(PhisiotherpeftiriaFragment frgm, String [] doctorNameList, String [] physiotherapeftiria, String [] physiotherapeftiriaAddress, int [] doctorImages){
        Context cxt = frgm.getContext();
        this.listDoctorName=doctorNameList;
        this.listDoctorOffice=physiotherapeftiria;
        this.listDoctorAddress=physiotherapeftiriaAddress;
        this.listDoctorImg=doctorImages;
        inflater= LayoutInflater.from(cxt);
    }

    @Override
    public int getCount() {
        return listDoctorName.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.item_doc, null);
        TextView doctorNameView= (TextView) convertView.findViewById(R.id.doctorName);
        TextView doctorOfficeView= (TextView) convertView.findViewById(R.id.doctorOffice);
        TextView doctorAddressView= (TextView) convertView.findViewById(R.id.doctorAddress);
        ImageView doctorImg = (ImageView) convertView.findViewById(R.id.doctorImage);
        doctorNameView.setText(listDoctorName[position]);
        doctorOfficeView.setText(listDoctorOffice[position]);
        doctorAddressView.setText(listDoctorAddress[position]);
        doctorImg.setImageResource(listDoctorImg[position]);

        return convertView;


    }
}
