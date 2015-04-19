package com.example.kienhoang.habitapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kienhoang on 4/18/15.
 */
public class HabitsFragmentAdapter extends BaseAdapter {
    private Context context;
    private List<Habit> habits;

    public HabitsFragmentAdapter(Context context, List<Habit> habits) {
        super();

        this.context = context;
        this.habits = habits;
    }

    @Override
    public Habit getItem(int position) {
        return habits.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return habits.size();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Habit habit = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.fragment_habit_row, null);
        }

        TextView nameTextView = (TextView) view.findViewById(R.id.habit_name);
        nameTextView.setText(habit.getName());

        TextView goalTextView = (TextView) view.findViewById(R.id.habit_goal);
        goalTextView.setText("Goal: " + Integer.toString(habit.getGoal()) + "/day");

        TextView breakBuildTextView = (TextView) view.findViewById(R.id.habit_break_build);
        if (habit.isBuildHabit()) {
            breakBuildTextView.setText("build");
            breakBuildTextView.setTextColor(Color.parseColor("#458B00"));
        } else {
            breakBuildTextView.setText("break");
            breakBuildTextView.setTextColor(Color.RED);
        }
        return view;
    }
}
