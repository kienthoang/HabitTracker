package com.example.kienhoang.habitapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

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
        databaseHandler.close();
    }

    public void createHabit() {
        DatabaseHandler databaseHandler = new DatabaseHandler(this);

        Spinner spinner = (Spinner) findViewById(R.id.type_spinner);
        String habitTypeName = spinner.getSelectedItem().toString();
        HabitType habitType = databaseHandler.getHabitTypeByName(habitTypeName);

        TextView nameTextView = (TextView) findViewById(R.id.edit_name);
        String habitName = nameTextView.getText().toString();

        ToggleButton breakBuildToggle = (ToggleButton) findViewById(R.id.edit_break_build);
        int breakOrBuild = breakBuildToggle.isChecked() ? 1 : 0;

        TextView goalTextView = (TextView) findViewById(R.id.edit_goal);
        int goal = Integer.parseInt(goalTextView.getText().toString());

        Habit habit = new Habit(habitType, habitName, breakOrBuild, goal);
        databaseHandler.createHabit(habit);

        databaseHandler.close();
    }

    public void doneButtonClicked(View view) {
        createHabit();
        finish();
    }
}
