package com.example.kienhoang.habitapp;

import android.content.ContentValues;

import java.util.Date;

public class FeedItem {
    private int id;
    private Date date;
    private Habit habit;

    public FeedItem(Habit habit, Date date) {
        this(-1, habit, date);
    }

    public FeedItem(int id, Habit habit, Date date) {
        this.id = id;
        this.date = date;
        this.habit = habit;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(DatabaseAttributes.FEED_ITEM_DATE, date.toString());
        values.put(DatabaseAttributes.FEED_ITEM_HABIT_ID, habit.getId());

        return values;
    }
}
