package com.example.kienhoang.habitapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.List;

/**
 * Created by kienhoang on 4/17/15.
 */
public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
        List<Habit> allHabits = databaseHandler.getAllHabits();
        int numBuildHabits = 0;
        for (Habit habit : allHabits) {
            if (habit.isBuildHabit()) {
                numBuildHabits += 1;
            }
        }
        int numBreakHabits = allHabits.size() - numBuildHabits;

        // Adjust the progress bars.
        List<DailyHabitCount> habitCounts = databaseHandler.getHabitCountsByDate(new Date());
        LinearLayout buildProgressBar = (LinearLayout) rootView.findViewById(R.id.build_progress_bar);
        LinearLayout breakProgressBar = (LinearLayout) rootView.findViewById(R.id.break_progress_bar);

        // Build habit progress bar.
        int numFinishedHabits = 0;
        for (DailyHabitCount habitCount : habitCounts) {
            Habit habit = habitCount.getHabit();
            if (habit.isBuildHabit() && habitCount.getCount() >= habit.getGoal()) {
                numFinishedHabits += 1;
            }
        }
        int buildPercentage = (int) (((float) numFinishedHabits) / numBuildHabits * 100);
        ViewGroup.LayoutParams params = buildProgressBar.getLayoutParams();
        params.height = Math.max(buildPercentage * 4, 100);
        params.width = 200;
        TextView buildPercentageTextView = (TextView) rootView.findViewById(R.id.build_progress_percentage);
        buildPercentageTextView.setText(buildPercentage + "%");
        TextView buildCountTextView = (TextView) rootView.findViewById(R.id.build_progress_count);
        buildCountTextView.setText(numFinishedHabits + "/" + numBuildHabits + " build habits");

        // Break habit progress bar.
        int numCleanHabits = numBreakHabits;
        for (DailyHabitCount habitCount : habitCounts) {
            Habit habit = habitCount.getHabit();
            if (habit.isBreakHabit() && habitCount.getCount() > habit.getGoal()) {
                numCleanHabits -= 1;
            }
        }
        int breakPercentage = (int) (((float) numCleanHabits) / numBreakHabits * 100);
        params = breakProgressBar.getLayoutParams();
        params.height = Math.max(breakPercentage * 4, 100);
        params.width = 200;
        TextView breakPercentageTextView = (TextView) rootView.findViewById(R.id.break_progress_percentage);
        breakPercentageTextView.setText(breakPercentage + "%");
        TextView breakCountTextView = (TextView) rootView.findViewById(R.id.break_progress_count);
        breakCountTextView.setText(numCleanHabits + "/" + numBreakHabits + " break habits");

        return rootView;
    }
}
