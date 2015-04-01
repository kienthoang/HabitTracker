package com.example.kienhoang.habitapp;

import android.content.ContentValues;

/**
 * Created by kienhoang on 3/31/15.
 */
public class DailyReminder {
    private int id;
    private DailyData dailyData;
    private Reminder reminder;

    public DailyReminder(DailyData dailyData, Reminder reminder) {
        this.dailyData = dailyData;
        this.reminder = reminder;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(DatabaseAttributes.DAILY_REMINDER_DATA_ID, this.dailyData.getId());
        values.put(DatabaseAttributes.DAILY_REMINDER_ID, this.reminder.getId());

        return values;
    }
}
