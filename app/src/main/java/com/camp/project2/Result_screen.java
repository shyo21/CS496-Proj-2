package com.camp.project2;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Result_screen extends Fragment {

    View myView;
    RecyclerView roulette;
    Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_result_screen, container, false);
        roulette = myView.findViewById(R.id.roulette);
        button = myView.findViewById(R.id.button);

        ArrayList<String> list = new ArrayList<>();
        for (int i=0; i<100; i++) {
            list.add(i, String.format("TEXT %d", i));
        }

        roulette.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(myView.getContext());
        roulette.setLayoutManager(linearLayoutManager);
        recyclerAdapter adapter = new recyclerAdapter(list);
        roulette.setAdapter(adapter);
        LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
        linearSnapHelper.attachToRecyclerView(roulette);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int time = 40;
                final Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (linearLayoutManager.findLastCompletelyVisibleItemPosition() < (adapter.getItemCount() - 1)) {

                            linearLayoutManager.smoothScrollToPosition(roulette, new RecyclerView.State(), linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1);
                        }

                        else if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == (adapter.getItemCount() - 1)) {

                            linearLayoutManager.smoothScrollToPosition(roulette, new RecyclerView.State(), 0);
                        }
                    }
                }, 0, time);
            }
        });

        return myView;
    }
}
