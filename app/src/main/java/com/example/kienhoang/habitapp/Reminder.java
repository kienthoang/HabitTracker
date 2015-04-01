package com.example.kienhoang.habitapp;

import android.content.ContentValues;

/**
 * Created by kienhoang on 3/31/15.
 */
public class Reminder {
    private int id;
    private HabitType habitType;
    private String title;
    private String description;

    public Reminder(HabitType habitType, String title, String desciption) {
        this.habitType = habitType;
        this.title = title;
        this.description = desciption;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(DatabaseAttributes.REMINDER_TYPE_ID, this.habitType.getId());
        values.put(DatabaseAttributes.REMINDER_TITLE, this.title);
        values.put(DatabaseAttributes.REMINDER_DESC, this.description);

        return values;
    }
}
