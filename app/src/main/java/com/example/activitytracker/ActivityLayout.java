package com.example.activitytracker;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityLayout extends AppCompatActivity {

    private TrackedActivity activity;
    private ActivityRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

        repo = ActivityRepository.getInstance();
        String id = getIntent().getStringExtra("activity_id");
        activity = repo.findById(id);

        TextView tvName = findViewById(R.id.tvName);
        TextView tvFrequency = findViewById(R.id.tvFrequency);
        TextView tvCount = findViewById(R.id.tvCount);
        EditText etProtocol = findViewById(R.id.etProtocol);
        Button btnLog = findViewById(R.id.btnLog);
        Button btnSaveProtocol = findViewById(R.id.btnSaveProtocol);
        Button btnDelete = findViewById(R.id.btnDelete);

        tvName.setText(activity.name);
        etProtocol.setText(activity.protocol);
        updateUI(tvFrequency, tvCount);

        btnLog.setOnClickListener(v -> {
            activity.timestamps.add(System.currentTimeMillis());
            updateUI(tvFrequency, tvCount);
        });

        btnSaveProtocol.setOnClickListener(v -> {
            activity.protocol = etProtocol.getText().toString().trim();
            Toast.makeText(this, "Protocol updated", Toast.LENGTH_SHORT).show();
        });

        btnDelete.setOnClickListener(v ->
                new AlertDialog.Builder(this)
                        .setTitle("Delete Activity")
                        .setMessage("Are you sure you want to delete \"" + activity.name + "\"?")
                        .setPositiveButton("Delete", (dialog, which) -> {
                            repo.delete(activity.id);
                            finish(); // Return to main page
                        })
                        .setNegativeButton("Cancel", null)
                        .show()
        );
    }

    private void updateUI(TextView tvFrequency, TextView tvCount) {
        tvCount.setText("Total: " + activity.timestamps.size());

        if (activity.timestamps.size() < 2) {
            tvFrequency.setText("Keep going to see your frequency...");
            return;
        }

        long first = activity.timestamps.get(0);
        long last = activity.timestamps.get(activity.timestamps.size() - 1);
        double avgMs = (double)(last - first) / (activity.timestamps.size() - 1);
        tvFrequency.setText("Average frequency: " + formatInterval(avgMs));
    }

    private String formatInterval(double ms) {
        if (ms < 60_000)
            return String.format("every %.0f seconds", ms / 1000);
        else if (ms < 3_600_000)
            return String.format("every %.0f minutes", ms / 60_000);
        else
            return String.format("every %.1f hours", ms / 3_600_000);
    }
}