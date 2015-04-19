package com.example.kienhoang.habitapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;

/**
 * Created by kienhoang on 4/17/15.
 */
public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // Adjust the progress bars.
        DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
        DailyData todayData = databaseHandler.getDailyDataByDate(new Date());


        return rootView;
    }
}
