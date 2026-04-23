package com.example.activitytracker;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TrackedActivity {
    public String id;
    public String name;
    public String protocol;
    public String color;
    public List<Long> timestamps;

    public TrackedActivity(String name, String protocol, String color) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.protocol = protocol;
        this.color = color;
        this.timestamps = new ArrayList<>();
    }
}