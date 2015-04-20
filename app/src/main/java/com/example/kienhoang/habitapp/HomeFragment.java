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
import java.util.Random;

/**
 * Created by kienhoang on 4/17/15.
 */
public class HomeFragment extends Fragment {
    public void initialPopulate() {
        DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
        List<Reminder> allReminders = databaseHandler.getAllReminders();
        if (allReminders.isEmpty()) {
            // Add reminders.
            HabitType type = databaseHandler.getHabitTypeByName("Working out");
            String title = "Working out helps you sleep better";
            String description = "Studies have shown that moderate to vigorous 20- to 30-minute workouts three to four times a week help you sleep better. However, you should work out in the morning or afternoon rather than close to bedtime, or you may find yourself too energized to sleep.";
            Reminder reminder = new Reminder(type, title, description);
            databaseHandler.createReminder(reminder);

            type = databaseHandler.getHabitTypeByName("Working out");
            title = "Builds and maintains healthy muscles, bones & joints";
            description = "As you get older, your bones lose density (mass), your joints become stiffer and less flexible, and your lean body mass decreases. Regular exercise is one of the best ways to slow or prevent muscle, joint and bone problems.";
            reminder = new Reminder(type, title, description);
            databaseHandler.createReminder(reminder);

            type = databaseHandler.getHabitTypeByName("Addiction");
            title = "How addiction works";
            description = "People use drugs or alcohol to escape, relax, or to reward themselves. But over time, drugs and alcohol make you believe that you can’t cope without them.";
            reminder = new Reminder(type, title, description);
            databaseHandler.createReminder(reminder);

            type = databaseHandler.getHabitTypeByName("Smoking");
            title = "Smoking-the leading cause of death in the U.S.";
            description = "Cigarette smoking causes more than 480,000 deaths each year in the United States. This is about one in five deaths.";
            reminder = new Reminder(type, title, description);
            databaseHandler.createReminder(reminder);

            type = databaseHandler.getHabitTypeByName("Walking");
            title = "Walking keeps you happy";
            description = "Studies have shown regular, moderate-intensity exercise (such as brisk walking) to be as effective as antidepressants in cases of mild to moderate depression.";
            reminder = new Reminder(type, title, description);
            databaseHandler.createReminder(reminder);
        }

        databaseHandler.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        initialPopulate();

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

        // Set reminder.
        List<Reminder> reminders = databaseHandler.getRelevantReminders();
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(reminders.size());
        Reminder reminder = reminders.get(index);
        TextView reminderTitleView = (TextView) rootView.findViewById(R.id.reminders_section_title);
        reminderTitleView.setText(reminder.getTitle());
        TextView descriptionTitleView = (TextView) rootView.findViewById(R.id.reminders_section_description);
        descriptionTitleView.setText(reminder.getDescription());

        return rootView;
    }
}
