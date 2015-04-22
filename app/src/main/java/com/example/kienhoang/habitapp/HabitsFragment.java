package com.example.kienhoang.habitapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

/**
 * Created by kienhoang on 4/17/15.
 */
public class HabitsFragment extends ListFragment {
    private List<Habit> habits;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_habits, container, false);

        // Pull the list of all habits
        DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
        this.habits = databaseHandler.getAllHabits();

        HabitsFragmentAdapter adapter = new HabitsFragmentAdapter(getActivity(), this.habits);
        setListAdapter(adapter);

        databaseHandler.close();

        return rootView;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        Habit selectedHabit = this.habits.get(position);
        Intent intent = new Intent(getActivity(), HabitGraphActivity.class);
        intent.putExtra("habit_name", selectedHabit.getName());
        startActivity(intent);
    }
}
