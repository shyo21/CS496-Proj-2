package com.camp.project2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.Objects;

public class Game_screen extends Fragment {
    public TextView stopwatch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_game_screen, container, false);
        stopwatch = myView.findViewById(R.id.stopwatch_textview);
        ((GameActivity) requireActivity()).watch_thread.start();

        return myView;
    }
}