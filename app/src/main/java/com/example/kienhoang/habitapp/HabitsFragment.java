package com.example.kienhoang.habitapp;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by kienhoang on 4/17/15.
 */
public class HabitsFragment extends ListFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
