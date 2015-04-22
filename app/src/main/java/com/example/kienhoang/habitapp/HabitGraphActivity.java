package com.example.kienhoang.habitapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by kienhoang on 4/21/15.
 */
public class HabitGraphActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_graph);

        // Get selected habit.
        Bundle extras = getIntent().getExtras();
        String habitName = "";
        if (extras != null) {
            habitName = extras.getString("habit_name");
        }

        // Set habit graph.
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        Habit habit = databaseHandler.getHabitByName(habitName);
        if (habit != null) {
            TextView titleView = (TextView) findViewById(R.id.graph_title);
            titleView.setText(habit.getName() + " log");

            // Fetch day-by-day data for the given habit.
            List<DailyHabitCount> habitCounts = databaseHandler.getHabitCountsByHabit(habit);
            HashMap<Date, List<DailyHabitCount>> habitCountsByDate = new HashMap<>();
            for (DailyHabitCount habitCount : habitCounts) {
                Date key = habitCount.getDailyData().getDate();
                if (!habitCountsByDate.containsKey(key)) {
                    habitCountsByDate.put(key, new ArrayList<DailyHabitCount>());
                }

                List<DailyHabitCount> counts = habitCountsByDate.get(key);
                counts.add(habitCount);
            }

            // Setup the graph.
            GraphView graph = (GraphView) findViewById(R.id.graph);
            DataPoint[] dataPoints = new DataPoint[habitCountsByDate.size() + 1];
            int index = 1;

            Iterator iterator = habitCountsByDate.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry pair = (Map.Entry) iterator.next();
                Date date = (Date) pair.getKey();

                int count = 0;
                List<DailyHabitCount> counts = (List<DailyHabitCount>) pair.getValue();
                for (DailyHabitCount habitCount : counts) {
                    count += habitCount.getCount();
                }

                dataPoints[index++] = new DataPoint(date, count);
            }
            Date yesterday = new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 24L);
            dataPoints[0] = new DataPoint(yesterday, 2);

            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
            graph.addSeries(series);

            // Set date label formatter.
            graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
            graph.getGridLabelRenderer().setNumHorizontalLabels(dataPoints.length + 1);

            graph.getViewport().setYAxisBoundsManual(true);
            graph.getViewport().setMinY(0);
            graph.getViewport().setMaxY(5);
        }

        databaseHandler.close();
    }
}
