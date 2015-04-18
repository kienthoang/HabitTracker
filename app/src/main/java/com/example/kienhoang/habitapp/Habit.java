package com.example.kienhoang.habitapp;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by kienhoang on 3/31/15.
 */
public class Habit implements DatabaseObject {
    public static final int HABIT_BREAK = 0;
    public static final int HABIT_BUILD = 1;

    private int id;
    private HabitType habitType;
    private String name;
    private int breakOrBuild;
    private int goal;

    public Habit(HabitType habitType, String name, int breakOrBuild, int goal) {
        this(-1, habitType, name, breakOrBuild, goal);
    }

    public Habit(int id, HabitType habitType, String name, int breakOrBuild, int goal) {
        this.id = id;
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

    public String getName() {
        return this.name;
    }

    public boolean isBreakHabit() {
        return this.breakOrBuild == HABIT_BREAK;
    }

    public boolean isBuildHabit() {
        return this.breakOrBuild == HABIT_BUILD;
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
