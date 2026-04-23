package com.example.activitytracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activitytracker.ActivityAdapter;
import com.example.activitytracker.ActivityRepository;
import com.example.activitytracker.R;
import com.example.activitytracker.TrackedActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityRepository repo;
    private ActivityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repo = ActivityRepository.getInstance();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ActivityAdapter(repo.getAll(), this::openActivity);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.fabAdd).setOnClickListener(v -> showAddDialog());
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged(); // Refresh list when returning from ActivityLayout
    }

    private void showAddDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_activity, null);
        EditText etName = dialogView.findViewById(R.id.etName);
        EditText etProtocol = dialogView.findViewById(R.id.etProtocol);
        Spinner spinnerColor = dialogView.findViewById(R.id.spinnerColor);

        String[] colors = {"Red", "Blue", "Green", "Orange", "Purple"};
        spinnerColor.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, colors));

        new AlertDialog.Builder(this)
                .setTitle("New Activity")
                .setView(dialogView)
                .setPositiveButton("Add", (dialog, which) -> {
                    String name = etName.getText().toString().trim();
                    String protocol = etProtocol.getText().toString().trim();
                    String color = spinnerColor.getSelectedItem().toString();

                    if (!name.isEmpty()) {
                        repo.add(new TrackedActivity(name, protocol, color));
                        adapter.notifyItemInserted(repo.getAll().size() - 1);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void openActivity(TrackedActivity activity) {
        Intent intent = new Intent(this, TrackedActivity.class);
        intent.putExtra("activity_id", activity.id);
        startActivity(intent);
    }
}