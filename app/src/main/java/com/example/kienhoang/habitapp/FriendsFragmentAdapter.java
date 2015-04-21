package com.example.kienhoang.habitapp;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kienhoang on 4/18/15.
 */
public class FriendsFragmentAdapter extends BaseAdapter {
    private Context context;
    private List<Friend> friends;

    public FriendsFragmentAdapter(Context context, List<Friend> friends) {
        super();

        this.context = context;
        this.friends = friends;
    }

    @Override
    public Friend getItem(int position) {
        return friends.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return friends.size();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Friend friend = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.fragment_friend_row, null);
        }

        TextView nameTextView = (TextView) view.findViewById(R.id.friend_row_name);
        nameTextView.setText(friend.getName());

        TextView habitsTextView = (TextView) view.findViewById(R.id.friend_row_habits);
        habitsTextView.setText(friend.getNumBuildHabits() + " build habits, " + friend.getNumBreakHabits() + " break habits");

        ImageView profileView = (ImageView) view.findViewById(R.id.friend_row_profile);
        profileView.setBackgroundResource(friend.getProfilePic());

        return view;
    }
}
