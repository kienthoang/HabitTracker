package com.example.kienhoang.habitapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    private static final String TABLE_DAILY_HABIT_COUNTS = "dailyhabitcounts";
    private static final String TABLE_FEED_ITEM = "feeditems";

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
        database.execSQL(createDailyHabitCountsTableSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_HABITS);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_HABIT_TYPES);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDERS);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_DAILY_DATA);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_DAILY_HABIT_COUNTS);

        onCreate(database);
    }

    public Cursor fetchBySelection(String table, String[] selections, String[] selectionArgs) {
        SQLiteDatabase database = this.getReadableDatabase();
        String selectionQuery = "";
        for (int i = 0; i < selections.length - 1; i++) {
            selectionQuery += selections[i] + "=? AND ";
        }
        selectionQuery += selections[selections.length - 1] + "=?";

        Cursor cursor = database.query(table, null, selectionQuery, selectionArgs, null, null, null,
                null);
        return cursor;
    }

    // Main Activity.
    public HabitType getHabitTypeById(int id) {
        HabitType habitType = null;
        Cursor cursor = this.fetchBySelection(TABLE_HABIT_TYPES,
                new String[]{DatabaseAttributes.ID},
                new String[]{Integer.toString(id)});
        if (isNonEmptyCursor(cursor)) {
            cursor.moveToFirst();
            habitType = new HabitType(cursor.getInt(0), cursor.getString(1));
        }

        return habitType;
    }

    public HabitType getHabitTypeByName(String name) {
        HabitType habitType = null;
        Cursor cursor = this.fetchBySelection(TABLE_HABIT_TYPES,
                new String[]{DatabaseAttributes.HABIT_TYPE_NAME},
                new String[]{name});
        if (isNonEmptyCursor(cursor)) {
            cursor.moveToFirst();
            habitType = new HabitType(cursor.getInt(0), cursor.getString(1));
        }

        return habitType;
    }

    public List<Habit> getAllHabits() {
        List<Habit> habits = new ArrayList<>();
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

                    Habit habit = new Habit(id, habitType, name, breakOrBuild, goal);
                    habits.add(habit);
                }
            } while (cursor.moveToNext());
        }

        return habits;
    }

    // index 0 is break, 1 is build.
    public List<DailyHabitCount> getHabitCountsByDate(Date date) {
        List<DailyHabitCount> habitCounts = new ArrayList<>();
        List<Habit> allHabits = this.getAllHabits();
        for (Habit habit : allHabits) {
            DailyHabitCount dailyCount = this.getHabitCountByHabitAndDate(habit, date);
            if (dailyCount != null) {
                habitCounts.add(dailyCount);
            }
        }

        return habitCounts;
    }

    public List<Reminder> getAllReminders() {
        List<Reminder> reminders = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_REMINDERS, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                HabitType habitType = this.getHabitTypeById(cursor.getInt(3));
                if (habitType != null) {
                    Reminder reminder = new Reminder(cursor.getInt(0), habitType, cursor.getString(1), cursor.getString(2));
                    reminders.add(reminder);
                }
            } while (cursor.moveToNext());
        }

        return reminders;
    }

    public Reminder getReminderForHabitTypeId(int habitTypeId) {
        Reminder reminder = null;

        Cursor cursor = this.fetchBySelection(TABLE_REMINDERS,
                new String[]{DatabaseAttributes.ID},
                new String[]{Integer.toString(habitTypeId)});
        if (isNonEmptyCursor(cursor)) {
            cursor.moveToFirst();
            HabitType habitType = this.getHabitTypeById(habitTypeId);
            reminder = new Reminder(cursor.getInt(0), habitType, cursor.getString(1), cursor.getString(2));
        }

        return reminder;
    }

    public List<Reminder> getRelevantReminders() {
        List<Habit> habits = this.getAllHabits();
        List<Integer> typeIds = new ArrayList<>();
        for (Habit habit : habits) {
            Integer typeId = habit.getHabitType().getId();
            if (typeIds.indexOf(typeId) == -1) {
                typeIds.add(typeId);
            }
        }

        List<Reminder> reminders = new ArrayList<>();
        for (Integer typeId : typeIds) {
            reminders.add(this.getReminderForHabitTypeId(typeId));
        }
        return reminders;
    }

    // Update habit daily counts Activity.
    public DailyData getDailyDataByDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        DailyData dailyData = null;
        Cursor cursor = this.fetchBySelection(TABLE_DAILY_DATA,
                new String[]{DatabaseAttributes.DAILY_DATA_DATE},
                new String[]{formatter.format(date)});
        if (isNonEmptyCursor(cursor)) {
            cursor.moveToFirst();
            String dateStr = cursor.getString(1);
            try {
                dailyData = new DailyData(cursor.getInt(0), formatter.parse(dateStr));
            } catch (Exception e) {
            }
        }

        return dailyData;
    }

    public Habit getHabitById(int id) {
        Habit habit = null;
        Cursor cursor = this.fetchBySelection(TABLE_HABITS,
                new String[]{DatabaseAttributes.ID},
                new String[]{Integer.toString(id)});
        if (isNonEmptyCursor(cursor)) {
            cursor.moveToFirst();
            habit = new Habit(cursor.getInt(0), null, cursor.getString(2), cursor.getInt(3),
                    cursor.getInt(4));
        }

        return habit;
    }

    public Habit getHabitByName(String name) {
        Habit habit = null;
        Cursor cursor = this.fetchBySelection(TABLE_HABITS,
                new String[]{DatabaseAttributes.HABIT_NAME},
                new String[]{name});
        if (isNonEmptyCursor(cursor)) {
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
            habitCount = getHabitCountByHabitAndDailyData(habit, dailyData);
        }

        return habitCount;
    }

    public DailyHabitCount getHabitCountByHabitAndDailyData(Habit habit, DailyData dailyData) {
        DailyHabitCount habitCount = null;

        Cursor cursor = this.fetchBySelection(TABLE_DAILY_HABIT_COUNTS,
                new String[]{
                        DatabaseAttributes.DAILY_HABIT_COUNT_HABIT_ID,
                        DatabaseAttributes.DAILY_HABIT_COUNT_DATA_ID},
                new String[]{
                        Integer.toString(habit.getId()),
                        Integer.toString(dailyData.getId())});
        if (isNonEmptyCursor(cursor)) {
            cursor.moveToFirst();
            habitCount = new DailyHabitCount(cursor.getInt(0), habit, dailyData, cursor.getInt(3));
        }

        return habitCount;
    }

    // Add-habit Activity.
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

    public void createHabitType(HabitType habitType) {
        SQLiteDatabase database = this.getReadableDatabase();
        database.insert(TABLE_HABIT_TYPES, null, habitType.toContentValues());
        database.close();
    }

    public void createDailyData(DailyData dailyData) {
        SQLiteDatabase database = this.getReadableDatabase();
        database.insert(TABLE_DAILY_DATA, null, dailyData.toContentValues());
        database.close();
    }

    public void createDailyHabitCount(DailyHabitCount dailyHabitCount) {
        SQLiteDatabase database = this.getReadableDatabase();
        database.insert(TABLE_DAILY_HABIT_COUNTS, null, dailyHabitCount.toContentValues());
        database.close();
    }

    public void createReminder(Reminder reminder) {
        SQLiteDatabase database = this.getReadableDatabase();
        database.insert(TABLE_REMINDERS, null, reminder.toContentValues());
        database.close();
    }

    public void updateDailyHabitCount(DailyHabitCount dailyHabitCount) {
        SQLiteDatabase database = this.getReadableDatabase();
        database.update(TABLE_DAILY_HABIT_COUNTS, dailyHabitCount.toContentValues(),
                DatabaseAttributes.ID + "=" + dailyHabitCount.getId(), null);
        database.close();
    }

    public boolean isNonEmptyCursor(Cursor cursor) {
        return cursor != null && cursor.getCount() > 0;
    }
}
