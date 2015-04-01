package com.example.kienhoang.habitapp;

import android.content.ContentValues;

/**
 * Created by kienhoang on 3/31/15.
 */
public class DailyData {
    private int id;
    private String date;

    public DailyData(String date) {
        this(-1, date);
    }

    public DailyData(int id, String date) {
        this.id = id;
        this.date = date;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(DatabaseAttributes.DAILY_DATA_DATE, date.toString());

        return values;
    }
}
