package com.camp.project2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class Game_screen extends Fragment {
    public TextView stopwatch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_game_screen, container, false);
        stopwatch = viewGroup.findViewById(R.id.stopwatch_textview);
        ((GameActivity)getActivity()).watch_thread.start();
        System.out.println("Thread start");

        //ddd

        return viewGroup;
    }
}
