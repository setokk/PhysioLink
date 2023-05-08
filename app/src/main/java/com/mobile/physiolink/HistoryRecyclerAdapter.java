package com.mobile.physiolink;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryRecyclerAdapter extends RecyclerView.Adapter<HistoryRecyclerAdapter.MyViewHolder> {

    String date[];
    String time[];
    String fysio[];
    String doc[];
    String service[];
    String price[];



    public  HistoryRecyclerAdapter(String history_date[],String history_time[],String history_fysio[],
                                   String history_doc[], String history_service[], String history_price[]){

        date = history_date;
        time = history_time;
        fysio = history_fysio;
        doc = history_doc;
        service = history_service;
        price = history_price;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate item layout-create new viewholder
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_custom_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    //bind data to the viewholder's views
    //some of them might not be necessary

       /* String dateItem = date[position];
        String timeItem = time[position];
        String fysioItem = fysio[position];
        String docItem = doc[position];
        String serviceItem = service[position];
        String priceItem = price[position];*/

        holder.dateText.setText(date[position]);
        holder.timeText.setText(time[position]);
        holder.fysioText.setText(fysio[position]);
        holder.serviceText.setText(service[position]);
        holder.priceText.setText(price[position]);


    }

    @Override
    public int getItemCount() {
        //return the size of the data array?
        //TODO add a counter for each item(in RecyclerView) change date.length
        return date.length;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView dateText, timeText ,fysioText,docText,serviceText,priceText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            dateText = itemView.findViewById(R.id.text_date);
            timeText = itemView.findViewById(R.id.text_time);
            fysioText = itemView.findViewById(R.id.fysio_text);
            serviceText = itemView.findViewById(R.id.service_text);
            priceText = itemView.findViewById(R.id.price_text);
        }
    }


}
