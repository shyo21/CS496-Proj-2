package com.camp.project2;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Game_screen extends Fragment {
    public TextView stopwatch;
    public TextView scoreView;
    public Button gameStart;
    public ImageButton[] imageArray = new ImageButton[9];
    public Integer[] imageId = new Integer[9];
    public Thread[] actionThread = new Thread[9];
    public int scoreValue = 0;
    public boolean timeEnded = false;
    public Handler actionHandler;
    public Handler tagHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup myView = (ViewGroup)inflater.inflate(R.layout.fragment_game_screen, container, false);
        stopwatch = myView.findViewById(R.id.stopWatch);
        gameStart = myView.findViewById(R.id.gameStart);
        scoreView = myView.findViewById(R.id.score);
        imageId = new Integer[]{ R.id.mole1, R.id.mole2, R.id.mole3, R.id.mole4, R.id.mole5, R.id.mole6, R.id.mole7, R.id.mole8, R.id.mole9 };

        for (int i = 0; i < imageArray.length; i++) {
            imageArray[i] = myView.findViewById(imageId[i]);
            imageArray[i].setTag("off");
        }

        scoreView.setVisibility(View.GONE);
        setOnClickListeners(imageArray);

        gameStart.setOnClickListener(view -> {
            gameStart.setVisibility(View.GONE);
            scoreView.setVisibility(View.VISIBLE);
            try { TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) { e.printStackTrace(); }

            ((GameActivity) requireActivity()).timerThread.start();

            setActionThread();
            for (Thread thread : actionThread) { thread.start(); }
        });

        return myView;
    }

    public interface onClickScore {
        void onClickSetScore(TextView scoreView, Integer scoreValue);
    }

    public void setOnClickListeners(ImageButton[] imageArray) {
        for (ImageButton currentButton : imageArray) {
            currentButton.setOnClickListener(view -> {
                if (view.getTag().toString().equals("on")) {
                    Toast.makeText(getContext(), "good", Toast.LENGTH_SHORT).show();
                    currentButton.setImageResource(R.drawable.mole_punched);
                    currentButton.setTag("pause");
                    scoreValue++;
                }
                onClickScore clickScore = (onClickScore) getContext();
                Objects.requireNonNull(clickScore).onClickSetScore(scoreView, scoreValue);
            });
        }
    }

    public AnimatorSet moleAction(ImageButton mole, Integer speed) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator up = ObjectAnimator.ofFloat(mole, "translationY", -150);
        ObjectAnimator down = ObjectAnimator.ofFloat(mole, "translationY", 0);
        animatorSet.play(up).before(down);
        animatorSet.setDuration(speed);
        return animatorSet;
    }

    public void setActionThread() {
        actionHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                imageArray[msg.arg1].setTag("on");
                imageArray[msg.arg1].setImageResource(R.drawable.mole);
                moleAction(imageArray[msg.arg1], msg.arg2).start();
            }
        };

        tagHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                imageArray[msg.arg1].setTag("off");
            }
        };

        class setAction implements Runnable {
            final int i;
            setAction(int i) { this.i = i; }

            @Override
            public void run() {
                while(!timeEnded) {
                    try {
                        Message msg1 = new Message();
                        Message msg2 = new Message();
                        int randomActionTime = new Random().nextInt(500) + 200;
                        int randomSleepTime = new Random().nextInt(3000) + 1000;

                        Thread.sleep(randomSleepTime);
                        msg1.arg1 = i;
                        msg1.arg2 = randomActionTime;
                        actionHandler.sendMessage(msg1);

                        Thread.sleep(2L * randomActionTime);
                        msg2.arg1 = i;
                        tagHandler.sendMessage(msg2);
                    } catch (Exception e){ e.printStackTrace(); }
                }
            }
        }
        for (int i = 0; i < imageArray.length; i++) {
            setAction action = new setAction(i);
            actionThread[i] = new Thread(action);
        }
    }
}