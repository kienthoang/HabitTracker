package com.example.kienhoang.habitapp;

import android.content.ContentValues;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FeedItem {
    private int id;
    private String date;
    private String time;
    private Habit habit;

    public FeedItem(Habit habit, Date date) {
        this(-1, habit, date);
    }

    public FeedItem(int id, Habit habit, Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
        String dateStr = formatter.format(date);
        formatter = new SimpleDateFormat("h:mm a");
        String time = formatter.format(date);

        this.id = id;
        this.habit = habit;
        this.date = dateStr;
        this.time = time;
    }

    public FeedItem(int id, Habit habit, String date, String time) {
        this.id = id;
        this.habit = habit;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Habit getHabit() {
        return this.habit;
    }

    public String getDate() {
        return this.date;
    }

    public String getTime() {
        return this.time;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(DatabaseAttributes.FEED_ITEM_DATE, this.date);
        values.put(DatabaseAttributes.FEED_ITEM_TIME, this.time);
        values.put(DatabaseAttributes.FEED_ITEM_HABIT_ID, habit.getId());

        return values;
    }
}
