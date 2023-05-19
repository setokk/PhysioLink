package com.mobile.physiolink.ui.patient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.physiolink.R;

public class AdapterForDropdown extends RecyclerView.Adapter<AdapterForDropdown.ViewHolder> {
    private String[] items;
    private OnItemClickListener listener;

    public AdapterForDropdown(@ArrayRes int arrayResId, OnItemClickListener listener, Context context) {
        this.items = context.getResources().getStringArray(arrayResId);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list_time, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = items[position];
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    public interface OnItemClickListener {
        void onItemClick(String item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        String item = items[position];
                        listener.onItemClick(item);
                    }
                }
            });
        }

        public void bind(String item) {
            textView.setText(item);
        }
    }
}
