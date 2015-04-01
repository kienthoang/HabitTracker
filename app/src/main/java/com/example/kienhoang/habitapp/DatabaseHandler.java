package com.example.kienhoang.habitapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public Cursor fetchBySelection(String table, String[] selections, String[] selectionArgs) {
        SQLiteDatabase database = this.getReadableDatabase();
        String selectionQuery = "";
        for (int i = 0; i < selections.length - 1; i++) {
            selectionQuery += selections[i] + "=? AND ";
        }
        selectionQuery += selections[selections.length] + "=?";

        Cursor cursor = database.query(table, null, selectionQuery, selectionArgs, null, null, null,
                null);
        return cursor;
    }

    // Main fragment.
    public HabitType getHabitTypeById(int id) {
        HabitType habitType = null;
        Cursor cursor = this.fetchBySelection(TABLE_HABIT_TYPES,
                new String[]{DatabaseAttributes.ID},
                new String[]{Integer.toString(id)});
        if (cursor != null) {
            cursor.moveToFirst();
            habitType = new HabitType(cursor.getInt(0), cursor.getString(1));
        }

        return habitType;
    }

    public List<Habit> getAllHabits() {
        List<Habit> habits = new ArrayList<Habit>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_HABITS, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                HabitType habitType = this.getHabitTypeById(cursor.getInt(1));
                if (habitType != null) {
                    int id = cursor.getInt(0);
                    String name = cursor.getString(2);
                    int goal = cursor.getInt(3);
                    int breakOrBuild = cursor.getInt(4);

                    Habit habit = new Habit(id, habitType, name, goal, breakOrBuild);
                    habits.add(habit);
                }
            } while (cursor.moveToNext());
        }

        return habits;
    }

    // index 0 is break, 1 is build.
    public int[] getHabitCountsByDate(Date date) {
        int[] counts = new int[2];
        List<Habit> allHabits = this.getAllHabits();
        for (Habit habit : allHabits) {
            DailyHabitCount dailyCount = this.getHabitCountByHabitAndDate(habit, date);
            int count = dailyCount.getCount();
            if (habit.isBreakHabit()) {
                counts[0] -= count;
            } else {
                counts[1] += count;
            }
        }

        return counts;
    }

    public List<Reminder> getRemindersForDate(Date date) {
        List<Reminder> reminders = new ArrayList<Reminder>();
        DailyData dailyData = this.getDailyDataByDate(date);
        if (dailyData != null) {
            Cursor cursor = this.fetchBySelection(TABLE_DAILY_REMINDERS,
                    new String[] {DatabaseAttributes.DAILY_REMINDER_DATA_ID},
                    new String[] {Integer.toString(dailyData.getId())});
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    int reminderId = cursor.getInt(2);
                    Cursor reminderCursor = this.fetchBySelection(TABLE_REMINDERS,
                            new String[] {DatabaseAttributes.ID},
                            new String[] {Integer.toString(reminderId)});
                    if (reminderCursor != null) {
                        reminderCursor.moveToFirst();
                        String title = reminderCursor.getString(1);
                        String desc = reminderCursor.getString(2);
                        int habitTypeId = reminderCursor.getInt(3);
                        Cursor habitTypeCursor = this.fetchBySelection(TABLE_HABIT_TYPES,
                                new String[] {DatabaseAttributes.ID},
                                new String[] {Integer.toString(habitTypeId)});
                        if (habitTypeCursor != null) {
                            habitTypeCursor.moveToFirst();
                            String habitTypeName = habitTypeCursor.getString(1);
                            HabitType type = new HabitType(habitTypeId, habitTypeName);
                            Reminder reminder = new Reminder(type, title, desc);

                            reminders.add(reminder);
                        }
                    }
                } while (cursor.moveToNext());
            }
        }

        return reminders;
    }

    // Update habit daily counts fragment.
    public DailyData getDailyDataByDate(Date date) {
        DailyData dailyData = null;
        Cursor cursor = this.fetchBySelection(TABLE_DAILY_DATA,
                new String[]{DatabaseAttributes.DAILY_DATA_DATE},
                new String[]{date.toString()});
        if (cursor != null) {
            cursor.moveToFirst();
            dailyData = new DailyData(cursor.getInt(0), cursor.getString(0));
        }

        return dailyData;
    }

    public Habit getHabitById(int id) {
        Habit habit = null;
        Cursor cursor = this.fetchBySelection(TABLE_HABITS,
                new String[]{DatabaseAttributes.ID},
                new String[]{Integer.toString(id)});
        if (cursor != null) {
            cursor.moveToFirst();
            habit = new Habit(cursor.getInt(0), null, cursor.getString(2), cursor.getInt(3),
                    cursor.getInt(4));
        }

        return habit;
    }

    public DailyHabitCount getHabitCountByHabitAndDate(Habit habit, Date date) {
        DailyHabitCount habitCount = null;

        DailyData dailyData = this.getDailyDataByDate(date);
        if (dailyData != null) {
            Cursor cursor = this.fetchBySelection(TABLE_DAILY_HABIT_COUNTS,
                    new String[] {
                            DatabaseAttributes.DAILY_HABIT_COUNT_HABIT_ID,
                            DatabaseAttributes.DAILY_HABIT_COUNT_DATA_ID},
                    new String[] {
                            Integer.toString(habit.getId()),
                            Integer.toString(dailyData.getId())});
            if (cursor != null) {
                cursor.moveToFirst();
                habitCount = new DailyHabitCount(cursor.getInt(0), habit, dailyData, cursor.getInt(4));
            }
        }

        return habitCount;
    }

    // Add habit Fragment.
    public List<HabitType> getAllHabitTypes() {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_HABIT_TYPES, null);

        List<HabitType> habitTypes = new ArrayList<HabitType>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);

                HabitType habitType = new HabitType(id, name);
                habitTypes.add(habitType);
            } while (cursor.moveToNext());
        }

        return habitTypes;
    }

    public void createHabit(Habit habit) {
        SQLiteDatabase database = this.getReadableDatabase();
        database.insert(TABLE_HABITS, null, habit.toContentValues());
        database.close();
    }
}
