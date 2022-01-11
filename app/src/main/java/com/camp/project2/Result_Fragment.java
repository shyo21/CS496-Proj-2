package com.camp.project2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Result_Fragment extends Fragment {

    View myView;
    RecyclerView roulette;
    RecyclerView playerList;
    Button button;
    public ArrayList<Result_PlayerInfo> mList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_result, container, false);
        playerList = myView.findViewById(R.id.result_playerList);
        roulette = myView.findViewById(R.id.result_roulette);
        button = myView.findViewById(R.id.button);

        setRouletteView(roulette);
        setPlayerListView(playerList);

        addItem("white","player1",100);

        button.setOnClickListener(view -> setRouletteAction(roulette));

        return myView;
    }

    private void setPlayerListView(RecyclerView playerList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(myView.getContext());
        playerList.setLayoutManager(linearLayoutManager);
        Result_RecyclerAdapter adapter = new Result_RecyclerAdapter(mList);
        playerList.setAdapter(adapter);
        LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
        linearSnapHelper.attachToRecyclerView(playerList);
    }

    private void addItem(String color, String name, Integer score) {
        Result_PlayerInfo item = new Result_PlayerInfo();
        item.setIconColor(color);
        item.setUserName(name);
        item.setUserScore(score);

        mList.add(item);
    }

    private void setRouletteView(RecyclerView roulette) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i, String.format("TEXT %d", i));
        }
        Collections.shuffle(list);

        roulette.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(myView.getContext());
        roulette.setLayoutManager(linearLayoutManager);
        Roulette_RecyclerAdapter adapter = new Roulette_RecyclerAdapter(list);
        roulette.setAdapter(adapter);
        LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
        linearSnapHelper.attachToRecyclerView(roulette);

        roulette.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) { return true; }
        });
    }

    private void setRouletteAction(RecyclerView roulette) {
        recursiveTimer(roulette,2000,30).start();
    }

    private CountDownTimer recursiveTimer(RecyclerView roulette, Integer duration, Integer interval) {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) roulette.getLayoutManager();
        Room_RecyclerAdapter adapter = (Room_RecyclerAdapter) roulette.getAdapter();
        if(duration == 2000) { duration -= 1000; }

        if (interval <= 400) {
            Integer finalDuration = duration;
            return new CountDownTimer(finalDuration, interval) {
                @Override
                public void onTick(long l) {
                    if (Objects.requireNonNull(linearLayoutManager).findLastCompletelyVisibleItemPosition() < (Objects.requireNonNull(adapter).getItemCount() - 1)) {
                        linearLayoutManager.smoothScrollToPosition(roulette, new RecyclerView.State(), linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1);
                    } else if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == (adapter.getItemCount() - 1)) {
                        linearLayoutManager.smoothScrollToPosition(roulette, new RecyclerView.State(), 0);
                    }
                }

                @Override
                public void onFinish() {
                    recursiveTimer(roulette, finalDuration, interval + 100).start();
                }
            };
        } else if (interval <= 600){
            Integer finalDuration1 = duration;
            return new CountDownTimer(finalDuration1, interval) {
                @Override
                public void onTick(long l) {
                    if (Objects.requireNonNull(linearLayoutManager).findLastCompletelyVisibleItemPosition() < (Objects.requireNonNull(adapter).getItemCount() - 1)) {
                        linearLayoutManager.smoothScrollToPosition(roulette, new RecyclerView.State(), linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1);
                    } else if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == (adapter.getItemCount() - 1)) {
                        linearLayoutManager.smoothScrollToPosition(roulette, new RecyclerView.State(), 0);
                    }
                }

                @Override
                public void onFinish() {
                    recursiveTimer(roulette, finalDuration1, interval + 400).start();
                }
            };
        } else {
            return new CountDownTimer(0, 0) {
                @Override
                public void onTick(long l) { }

                @Override
                public void onFinish() { }
            };
        }
    }
}
