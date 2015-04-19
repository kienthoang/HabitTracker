package com.example.kienhoang.habitapp;

import android.content.ContentValues;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by kienhoang on 3/31/15.
 */
public class DailyData {
    private int id;
    private Date date;

    public DailyData(Date date) {
        this(-1, date);
    }

    public DailyData(int id, Date date) {
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
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        values.put(DatabaseAttributes.DAILY_DATA_DATE, formatter.format(date));

        return values;
    }
}
