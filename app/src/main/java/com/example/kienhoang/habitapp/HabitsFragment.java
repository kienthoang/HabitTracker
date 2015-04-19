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
        }

        databaseHandler.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initialPopulate();

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_habits, container, false);

        // Pull the list of all habits
        DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
        List<Habit> habits = databaseHandler.getAllHabits();

        HabitsFragmentAdapter adapter = new HabitsFragmentAdapter(getActivity(), habits);
        setListAdapter(adapter);

        return rootView;
    }
}
