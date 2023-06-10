package com.example.thalir_app;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    private List<AllSchedulesQuery.AllSchedule> schedules;

    public void setSchedules(List<AllSchedulesQuery.AllSchedule> schedules) {
        this.schedules = schedules;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_file, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AllSchedulesQuery.AllSchedule schedule = schedules.get(position);

        // Bind the schedule data to the views in the item layout
        holder.dateId.setText(schedule.date());
        holder.destinationId.setText(schedule.destinationLocation());
    }

    @Override
    public int getItemCount() {
        return schedules != null ? schedules.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView dateId;
        public TextView destinationId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateId = itemView.findViewById(R.id.dateId);
            destinationId = itemView.findViewById(R.id.destinationId);
        }
    }
}
