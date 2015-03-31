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

    private static final String ATTR_ID = "id";
    private static final String ATTR_NAME = "name";
    private static final String ATTR_TITLE = "title";
    private static final String ATTR_DESC = "description";
    private static final String ATTR_GOAL = "goal";
    private static final String ATTR_DATE = "date";
    private static final String ATTR_COUNT = "count";

    private static final String ATTR_HABIT_ID = "habit_id";
    private static final String ATTR_HABIT_TYPE_ID = "habit_type_id";
    private static final String ATTR_DAILY_DATA_ID = "daily_data_id";
    private static final String ATTR_REMINDERS_ID = "daily_reminder_id";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create tables.
    @Override
    public void onCreate(SQLiteDatabase database) {
        String createHabitsTableSQL = "CREATE TABLE " + TABLE_HABITS + "("
                + ATTR_ID + " INTEGER PRIMARY KEY," + ATTR_HABIT_TYPE_ID + " INTEGER NOT NULL,"
                + ATTR_NAME + " TEXT NOT NULL," + ATTR_GOAL + " INTEGER NOT NULL"
                + ")";
        String createHabitTypesTableSQL = "CREATE TABLE " + TABLE_HABIT_TYPES + "("
                + ATTR_ID + " INTEGER PRIMARY KEY," + ATTR_NAME + " TEXT NOT NULL"
                + ")";
        String createRemindersTableSQL = "CREATE TABLE " + TABLE_REMINDERS + "("
                + ATTR_ID + " INTEGER PRIMARY KEY," + ATTR_TITLE + " TEXT NOT NULL,"
                + ATTR_DESC + " TEXT NOT NULL"
                + ")";
        String createDailyDataTableSQL = "CREATE TABLE " + TABLE_DAILY_DATA + "("
                + ATTR_ID + " INTEGER PRIMARY KEY," + ATTR_DATE + " DATE NOT NULL"
                + ")";
        String createDailyRemindersTableSQL = "CREATE TABLE " + TABLE_DAILY_REMINDERS + "("
                + ATTR_ID + " INTEGER PRIMARY KEY," + ATTR_DAILY_DATA_ID + " INTEGER NOT NULL,"
                + ATTR_REMINDERS_ID + " INTEGER NOT NULL"
                + ")";
        String createDailyHabitCountsTableSQL = "CREATE TABLE " + TABLE_DAILY_HABIT_COUNTS + "("
                + ATTR_ID + " INTEGER PRIMARY KEY," + ATTR_DAILY_DATA_ID + " INTEGER NOT NULL,"
                + ATTR_HABIT_ID + " INTEGER NOT NULL," + ATTR_COUNT + " INTEGER"
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
}
