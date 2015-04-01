package com.example.kienhoang.habitapp;

import android.content.ContentValues;

/**
 * Created by kienhoang on 3/31/15.
 */
public class Habit implements DatabaseObject {
    private static final int HABIT_BREAK = 0;
    private static final int HABIT_BUILD = 1;

    private int id;
    private HabitType habitType;
    private String name;
    private int breakOrBuild;
    private int goal;

    public Habit(HabitType habitType, String name, int breakOrBuild, int goal) {
        this.habitType = habitType;
        this.name = name;
        this.breakOrBuild = breakOrBuild;
        this.goal = goal;
    }

    public int getId() {
        return this.id;
    }

    public void setId() {
        this.id = id;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(DatabaseAttributes.HABIT_NAME, this.name);
        values.put(DatabaseAttributes.HABIT_BREAK_OR_BUILD, this.breakOrBuild);
        values.put(DatabaseAttributes.HABIT_TYPE_ID, this.habitType.getId());
        values.put(DatabaseAttributes.HABIT_GOAL, this.goal);

        return values;
    }
}
