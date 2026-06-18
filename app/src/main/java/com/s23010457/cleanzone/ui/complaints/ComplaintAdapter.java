package com.s23010457.cleanzone.ui.complaints;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.s23010457.cleanzone.R;

import java.util.List;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.VH> {

    private final List<Complaint> complaints;

    public ComplaintAdapter(List<Complaint> complaints) {
        this.complaints = complaints;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_complaint, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Complaint c = complaints.get(position);
        holder.title.setText(c.getTitle());
        holder.category.setText(c.getCategory());
        holder.description.setText(c.getDescription());
        holder.location.setText("\uD83D\uDCCD " + c.getLocation());
    }

    @Override
    public int getItemCount() {
        return complaints.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        final TextView title;
        final TextView category;
        final TextView description;
        final TextView location;

        VH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_complaint_title);
            category = itemView.findViewById(R.id.tv_complaint_category);
            description = itemView.findViewById(R.id.tv_complaint_description);
            location = itemView.findViewById(R.id.tv_complaint_location);
        }
    }
}
