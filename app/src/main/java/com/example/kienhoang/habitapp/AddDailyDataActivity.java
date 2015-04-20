package com.example.kienhoang.habitapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by kienhoang on 4/18/15.
 */
public class AddDailyDataActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_daily_data);
        setTitle("Log Habit");

        // Set habits spinner.
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        List<Habit> habitObjs = databaseHandler.getAllHabits();
        List<String> habits = new ArrayList<>();
        for (Habit habit : habitObjs) {
            habits.add(habit.getName());
        }
        Spinner spinner = (Spinner) findViewById(R.id.habit_spinner);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, habits));
        databaseHandler.close();
    }

    public void createDailyData() {
        DatabaseHandler databaseHandler = new DatabaseHandler(this);

        // Create daily data if it doesn't exist.
        DailyData dailyData = databaseHandler.getDailyDataByDate(new Date());
        if (dailyData == null) {
            dailyData = new DailyData(new Date());
            databaseHandler.createDailyData(dailyData);
            dailyData = databaseHandler.getDailyDataByDate(new Date());
        }

        // Create or update daily habit count.
        Spinner spinner = (Spinner) findViewById(R.id.habit_spinner);
        String habitName = spinner.getSelectedItem().toString();
        Habit habit = databaseHandler.getHabitByName(habitName);
        DailyHabitCount dailyHabitCount = databaseHandler.getHabitCountByHabitAndDailyData(habit, dailyData);
        if (dailyHabitCount == null) {
            dailyHabitCount = new DailyHabitCount(habit, dailyData, 1);
            databaseHandler.createDailyHabitCount(dailyHabitCount);
        } else {
            dailyHabitCount.setCount(dailyHabitCount.getCount() + 1);
            databaseHandler.updateDailyHabitCount(dailyHabitCount);
        }

        // Create feed item.
        FeedItem feedItem = new FeedItem(habit, new Date());
        databaseHandler.createFeedItem(feedItem);

        databaseHandler.close();
    }

    public void logButtonClicked(View view) {
        createDailyData();
        finish();
    }
}
