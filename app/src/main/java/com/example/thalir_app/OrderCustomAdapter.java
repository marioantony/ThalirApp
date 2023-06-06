package com.example.thalir_app;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderCustomAdapter extends RecyclerView.Adapter<OrderCustomAdapter.MyViewHolder> {
    List<OrderModal> mList;
    Context context;

    public OrderCustomAdapter(List<OrderModal> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view =layoutInflater.inflate(R.layout.order_layout,parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.farmerName.setText(mList.get(position).getFarmerName());
        holder.location.setText(mList.get(position).getFarmerLocation());
//        holder.bagCount.setText(mList.get(position).getRequestedBag());
//        holder.farmerName.setText(mList.get(position).getFarmerName());
//        holder.location.setText(mList.get(position).getFarmerLocation());
//        holder.bagCount.setText(mList.get(position).getRequestedBag());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView farmerName;
        TextView location;
        TextView bagCount;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            farmerName = itemView.findViewById(R.id.farmerNameId);
            location = itemView.findViewById(R.id.locationId);
            bagCount = itemView.findViewById(R.id.bags);
        }
    }
}
