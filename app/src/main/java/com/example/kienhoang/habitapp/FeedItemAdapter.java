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
public class FeedItemAdapter extends BaseAdapter {
    private Context context;
    private List<FeedItem> feedItems;

    public FeedItemAdapter(Context context, List<FeedItem> feedItems) {
        super();

        this.context = context;
        this.feedItems = feedItems;
    }

    @Override
    public FeedItem getItem(int position) {
        return feedItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return feedItems.size();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        FeedItem feedItem = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.feed_item, null);
        }

        Habit habit = feedItem.getHabit();
        TextView nameTextView = (TextView) view.findViewById(R.id.habit_name);
        nameTextView.setText(habit.getName());
        if (habit.isBuildHabit()) {
            nameTextView.setTextColor(Color.parseColor("#488214"));
        } else {
            nameTextView.setTextColor(Color.parseColor("#AF4035"));
        }

        TextView timeTextView = (TextView) view.findViewById(R.id.feed_item_time);
        timeTextView.setText(feedItem.getDate() + " " + feedItem.getTime());

        return view;
    }
}
