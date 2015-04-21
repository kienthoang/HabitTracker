package com.example.kienhoang.habitapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

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

        Habit habit;
        if (position == this.feedItems.size() - 2) {
            habit = new Habit(null, "Sprinting", Habit.HABIT_BUILD, 1);

            ImageView profileView = (ImageView) view.findViewById(R.id.feed_item_profile);
            profileView.setBackgroundResource(R.drawable.circle_profile_2);

            TextView userTextView = (TextView) view.findViewById(R.id.feed_item_user);
            userTextView.setText("John Malkovich logged");
        } else if (position == this.feedItems.size() - 1) {
            habit = new Habit(null, "Yoga", Habit.HABIT_BUILD, 1);

            ImageView profileView = (ImageView) view.findViewById(R.id.feed_item_profile);
            profileView.setBackgroundResource(R.drawable.circle_profile_4);

            TextView userTextView = (TextView) view.findViewById(R.id.feed_item_user);
            userTextView.setText("Katheryn Winnick logged");
        } else {
            habit = feedItem.getHabit();

            TextView timeTextView = (TextView) view.findViewById(R.id.feed_item_time);timeTextView.setText(feedItem.getDate() + " " + feedItem.getTime());
        }
        TextView nameTextView = (TextView) view.findViewById(R.id.habit_name);
        nameTextView.setText(habit.getName());
        if (habit.isBuildHabit()) {
            nameTextView.setTextColor(Color.parseColor("#488214"));
        } else {
            nameTextView.setTextColor(Color.parseColor("#AF4035"));
        }

        return view;
    }
}
