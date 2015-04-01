package com.example.kienhoang.habitapp;

import android.content.ContentValues;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kienhoang on 3/31/15.
 */
public class DailyData {
    private int id;
    private Date date;

    public DailyData(String dateString) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = formatter.parse(dateString);
            date.setMonth(date.getMonth() + 1);
            this.date = date;
        } catch (Exception e){
            Log.d("ERROR", e.getMessage());
        }
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        int year = date.getYear() + 1900;
        int month = date.getMonth();
        int day = date.getDate();
        String dateString = year + "-" + month + "-" + day;
        values.put(DatabaseAttributes.DAILY_DATA_DATE, dateString);

        return values;
    }
}
