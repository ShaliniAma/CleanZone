package com.s23010457.cleanzone.ui.pickups;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.s23010457.cleanzone.R;

import java.util.List;

public class PickupAdapter extends RecyclerView.Adapter<PickupAdapter.VH> {

    public interface OnItemClickListener {
        void onItemClick(PickupLocation location, int position);
    }

    private final List<PickupLocation> locations;
    private final OnItemClickListener listener;
    private int selectedPosition = -1;

    public PickupAdapter(List<PickupLocation> locations, OnItemClickListener listener) {
        this.locations = locations;
        this.listener = listener;
    }

    public void setSelectedPosition(int position) {
        int old = selectedPosition;
        selectedPosition = position;
        if (old >= 0) notifyItemChanged(old);
        if (position >= 0) notifyItemChanged(position);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pickup, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        PickupLocation loc = locations.get(position);
        holder.name.setText(loc.getName());
        holder.type.setText(loc.getType());
        holder.schedule.setText("\uD83D\uDD52 " + loc.getSchedule());
        holder.address.setText("\uD83D\uDCCD " + loc.getAddress());

        // Color the type badge based on waste type
        int badgeColor;
        switch (loc.getType()) {
            case "Recyclables":
                badgeColor = Color.parseColor("#2196F3");
                break;
            case "Organic Waste":
                badgeColor = Color.parseColor("#FF9800");
                break;
            case "E-Waste":
                badgeColor = Color.parseColor("#9C27B0");
                break;
            default:
                badgeColor = Color.parseColor("#4CAF50");
                break;
        }
        holder.type.getBackground().setTint(badgeColor);

        // Highlight selected item
        if (position == selectedPosition) {
            holder.itemView.setAlpha(1.0f);
            holder.itemView.setScaleX(1.02f);
            holder.itemView.setScaleY(1.02f);
        } else {
            holder.itemView.setAlpha(0.85f);
            holder.itemView.setScaleX(1.0f);
            holder.itemView.setScaleY(1.0f);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(loc, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        final TextView name, type, schedule, address;

        VH(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_pickup_name);
            type = itemView.findViewById(R.id.tv_pickup_type);
            schedule = itemView.findViewById(R.id.tv_pickup_schedule);
            address = itemView.findViewById(R.id.tv_pickup_address);
        }
    }
}
