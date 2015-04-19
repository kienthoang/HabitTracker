package com.example.kienhoang.habitapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kienhoang on 4/18/15.
 */
public class AddHabitActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);
        setTitle("New Habit");

        // Set habit types spinner.
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        List<HabitType> habitTypeObjs = databaseHandler.getAllHabitTypes();
        List<String> habitTypes = new ArrayList<>();
        for (HabitType type : habitTypeObjs) {
            habitTypes.add(type.getName());
        }
        Spinner spinner = (Spinner) findViewById(R.id.type_spinner);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, habitTypes));
    }
}
