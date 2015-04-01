package com.example.kienhoang.habitapp;

import android.content.ContentValues;

/**
 * Created by kienhoang on 3/31/15.
 */
public class DailyHabitCount {
    private int id;
    private DailyData dailyData;
    private Habit habit;
    private int count;

    public DailyHabitCount(Habit habit, DailyData dailyData, int count) {
        this.habit = habit;
        this.dailyData = dailyData;
        this.count = count;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(DatabaseAttributes.DAILY_HABIT_COUNT_DATA_ID, this.dailyData.getId());
        values.put(DatabaseAttributes.DAILY_HABIT_COUNT_HABIT_ID, this.habit.getId());
        values.put(DatabaseAttributes.DAILY_HABIT_COUNT, this.count);

        return values;
    }
}
