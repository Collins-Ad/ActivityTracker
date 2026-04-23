package com.example.activitytracker;

import java.util.ArrayList;
import java.util.List;

// Singleton so both Activities share the same in-memory list
    public class ActivityRepository {
        private static ActivityRepository instance;
        private final List<TrackedActivity> activities = new ArrayList<>();

        private ActivityRepository() {}

        public static ActivityRepository getInstance() {
            if (instance == null) instance = new ActivityRepository();
            return instance;
        }

        public List<TrackedActivity> getAll() { return activities; }

        public void add(TrackedActivity a) { activities.add(a); }

        public void delete(String id) {
            activities.removeIf(a -> a.id.equals(id));
        }

        public TrackedActivity findById(String id) {
            for (TrackedActivity a : activities)
                if (a.id.equals(id)) return a;
            return null;
        }
    }

