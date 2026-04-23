package com.example.activitytracker;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {

    private final List<TrackedActivity> activities;
    private final Consumer<TrackedActivity> onClick;

    public ActivityAdapter(List<TrackedActivity> activities, Consumer<TrackedActivity> onClick) {
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
        TrackedActivity activity = activities.get(position);
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