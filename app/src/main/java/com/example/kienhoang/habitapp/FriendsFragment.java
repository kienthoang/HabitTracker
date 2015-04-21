package com.example.kienhoang.habitapp;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kienhoang on 4/17/15.
 */
public class FriendsFragment extends ListFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_friends, container, false);

        // List of friends.
        List<Friend> friends = new ArrayList<>();
        Friend friend = new Friend(R.drawable.circle_profile_2, "John Malkovich", 10, 5);
        friends.add(friend);
        friend = new Friend(R.drawable.circle_profile_3, "Travis Fimmel", 3, 2);
        friends.add(friend);
        friend = new Friend(R.drawable.circle_profile_4, "Katheryn Winnick", 2, 9);
        friends.add(friend);

        FriendsFragmentAdapter adapter = new FriendsFragmentAdapter(getActivity(), friends);
        setListAdapter(adapter);

        return rootView;
    }
}
