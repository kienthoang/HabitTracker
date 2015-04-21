package com.example.kienhoang.habitapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kienhoang on 4/21/15.
 */
public class HabitGraphActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_graph);

        // Get selected habit.
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String habitName = extras.getString("habit_name");
        }

        // Set habit graph.
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        setTitle("");

        databaseHandler.close();
    }
}
