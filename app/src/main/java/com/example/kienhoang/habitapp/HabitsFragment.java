package com.example.kienhoang.habitapp;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kienhoang on 4/17/15.
 */
public class HabitsFragment extends ListFragment {
    private static final String[] HABIT_TYPES = {"Working out", "Addiction", "Smoking", "Walking", "Sleeping"};

    public void initialPopulate() {
        DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
        List<HabitType> allHabitTypes = databaseHandler.getAllHabitTypes();
        if (allHabitTypes.isEmpty()) {
            // Add habit types.
            for (String typeName : HABIT_TYPES) {
                HabitType type = new HabitType(typeName);
                databaseHandler.createHabitType(type);
            }

            // Add habits (for testing only).
            HabitType type = databaseHandler.getHabitTypeByName("Working out");
            Habit habit = new Habit(type, "Lifting", Habit.HABIT_BUILD, 1);
            databaseHandler.createHabit(habit);

            type = databaseHandler.getHabitTypeByName("Addiction");
            habit = new Habit(type, "Watching Television", Habit.HABIT_BREAK, 1);
            databaseHandler.createHabit(habit);

            type = databaseHandler.getHabitTypeByName("Smoking");
            habit = new Habit(type, "Smoking cigars", Habit.HABIT_BREAK, 0);
            databaseHandler.createHabit(habit);

            type = databaseHandler.getHabitTypeByName("Walking");
            habit = new Habit(type, "Walking around", Habit.HABIT_BUILD, 3);
            databaseHandler.createHabit(habit);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initialPopulate();

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_habits, container, false);

        // Pull the list of all habits
        DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
        List<Habit> habits = databaseHandler.getAllHabits();
        List<String> habitNames = new ArrayList<>();
        for (Habit habit : habits) {
            habitNames.add(habit.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, habitNames);
        setListAdapter(adapter);

        return rootView;
    }
}
