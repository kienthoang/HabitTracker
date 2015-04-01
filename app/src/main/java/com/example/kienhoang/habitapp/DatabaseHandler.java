package com.example.kienhoang.habitapp;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

/**
 * The database handler.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "habitsappdb";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_HABITS = "habits";
    private static final String TABLE_HABIT_TYPES = "habittypes";
    private static final String TABLE_REMINDERS = "reminders";
    private static final String TABLE_DAILY_DATA = "dailydata";
    private static final String TABLE_DAILY_REMINDERS = "dailyreminders";
    private static final String TABLE_DAILY_HABIT_COUNTS = "dailyhabitcounts";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create tables.
    @Override
    public void onCreate(SQLiteDatabase database) {
        String createHabitsTableSQL = "CREATE TABLE " + TABLE_HABITS + "("
                + DatabaseAttributes.ID + " INTEGER PRIMARY KEY,"
                + DatabaseAttributes.HABIT_TYPE_ID + " INTEGER NOT NULL,"
                + DatabaseAttributes.HABIT_NAME + " TEXT NOT NULL,"
                + DatabaseAttributes.HABIT_GOAL + " INTEGER NOT NULL,"
                + DatabaseAttributes.HABIT_BREAK_OR_BUILD + " INTEGER"
                + ")";
        String createHabitTypesTableSQL = "CREATE TABLE " + TABLE_HABIT_TYPES + "("
                + DatabaseAttributes.ID + " INTEGER PRIMARY KEY,"
                + DatabaseAttributes.HABIT_TYPE_NAME + " TEXT NOT NULL"
                + ")";
        String createRemindersTableSQL = "CREATE TABLE " + TABLE_REMINDERS + "("
                + DatabaseAttributes.ID + " INTEGER PRIMARY KEY,"
                + DatabaseAttributes.REMINDER_TITLE + " TEXT NOT NULL,"
                + DatabaseAttributes.REMINDER_DESC + " TEXT NOT NULL,"
                + DatabaseAttributes.REMINDER_TYPE_ID + " INTEGER NOT NULL"
                + ")";
        String createDailyDataTableSQL = "CREATE TABLE " + TABLE_DAILY_DATA + "("
                + DatabaseAttributes.ID + " INTEGER PRIMARY KEY,"
                + DatabaseAttributes.DAILY_DATA_DATE + " STRING NOT NULL"
                + ")";
        String createDailyRemindersTableSQL = "CREATE TABLE " + TABLE_DAILY_REMINDERS + "("
                + DatabaseAttributes.ID + " INTEGER PRIMARY KEY,"
                + DatabaseAttributes.DAILY_REMINDER_DATA_ID + " INTEGER NOT NULL,"
                + DatabaseAttributes.DAILY_REMINDER_ID + " INTEGER NOT NULL"
                + ")";
        String createDailyHabitCountsTableSQL = "CREATE TABLE " + TABLE_DAILY_HABIT_COUNTS + "("
                + DatabaseAttributes.ID + " INTEGER PRIMARY KEY,"
                + DatabaseAttributes.DAILY_HABIT_COUNT_DATA_ID + " INTEGER NOT NULL,"
                + DatabaseAttributes.DAILY_HABIT_COUNT_HABIT_ID + " INTEGER NOT NULL,"
                + DatabaseAttributes.DAILY_HABIT_COUNT + " INTEGER"
                + ")";

        database.execSQL(createHabitsTableSQL);
        database.execSQL(createHabitTypesTableSQL);
        database.execSQL(createRemindersTableSQL);
        database.execSQL(createDailyDataTableSQL);
        database.execSQL(createDailyRemindersTableSQL);
        database.execSQL(createDailyHabitCountsTableSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_HABITS);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_HABIT_TYPES);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDERS);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_DAILY_DATA);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_DAILY_REMINDERS);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_DAILY_HABIT_COUNTS);

        onCreate(database);
    }

    public void addHabit(Habit habit) {

    }
}
