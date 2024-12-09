package com.example.smartproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Adapter for RecyclerView
public class DoorActivityAdapter extends RecyclerView.Adapter<DoorActivityAdapter.DoorActivityViewHolder> {

    private List<DoorActivity> doorActivityList; // Data source for RecyclerView

    // Constructor to pass the data list
    public DoorActivityAdapter(List<DoorActivity> doorActivityList) {
        this.doorActivityList = doorActivityList;
    }

    @NonNull
    @Override
    public DoorActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate each item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item, parent, false);
        return new DoorActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoorActivityViewHolder holder, int position) {
        // Get the current activity from the list
        DoorActivity activity = doorActivityList.get(position);

        // Bind the data to the item views
        holder.dateTextView.setText(activity.getDate());
        holder.statusTextView.setText(activity.getStatus());
    }

    @Override
    public int getItemCount() {
        return doorActivityList.size(); // Return the total number of items
    }

    // ViewHolder class to represent each RecyclerView item
    public static class DoorActivityViewHolder extends RecyclerView.ViewHolder {

        TextView dateTextView, statusTextView;

        public DoorActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            // Bind views with their IDs
            dateTextView = itemView.findViewById(R.id.dateTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
        }
    }
}
