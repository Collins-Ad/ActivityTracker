package com.example.activitytracker;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.function.Consumer;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {

    private final List<com.example.activitytracker.TrackedActivity> activities;
    private final Consumer<com.example.activitytracker.TrackedActivity> onClick;

    public ActivityAdapter(List<com.example.activitytracker.TrackedActivity> activities, Consumer<com.example.activitytracker.TrackedActivity> onClick) {
        this.activities = activities;
        this.onClick = onClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_activity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        com.example.activitytracker.TrackedActivity activity = activities.get(position);
        holder.tvName.setText(activity.name);
        holder.colorDot.setColorFilter(parseColor(activity.color));
        holder.itemView.setOnClickListener(v -> onClick.accept(activity));
    }

    @Override
    public int getItemCount() { return activities.size(); }

    private int parseColor(String color) {
        switch (color) {
            case "Red":    return Color.parseColor("#FF5252");
            case "Blue":   return Color.parseColor("#448AFF");
            case "Green":  return Color.parseColor("#69F0AE");
            case "Orange": return Color.parseColor("#FFD740");
            case "Purple": return Color.parseColor("#E040FB");
            default:       return Color.GRAY;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView colorDot;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            colorDot = itemView.findViewById(R.id.colorDot);
        }
    }
}