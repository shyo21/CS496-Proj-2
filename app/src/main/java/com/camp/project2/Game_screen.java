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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.Objects;
import java.util.Random;

public class Game_screen extends Fragment {
    public TextView stopwatch;
    public TextView scoreView;
    public ImageButton[] moleArray = new ImageButton[9];
    public Integer[] moleId = new Integer[9];
    public ImageView[] pipeArray = new ImageView[9];
    public Integer[] pipeId = new Integer[9];
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
        scoreView = myView.findViewById(R.id.score);
        moleId = new Integer[]{ R.id.mole1, R.id.mole2, R.id.mole3, R.id.mole4, R.id.mole5, R.id.mole6, R.id.mole7, R.id.mole8, R.id.mole9 };
        pipeId = new Integer[]{ R.id.pipe1, R.id.pipe2, R.id.pipe3, R.id.pipe4, R.id.pipe5, R.id.pipe6, R.id.pipe7, R.id.pipe8, R.id.pipe9 };

        for (int i = 0; i < moleArray.length; i++) {
            moleArray[i] = myView.findViewById(moleId[i]);
            moleArray[i].setTag("off");
            //moleArray[i].setVisibility(View.INVISIBLE);
            pipeArray[i] = myView.findViewById(pipeId[i]);
            //pipeArray[i].setVisibility(View.INVISIBLE);
        }

        setOnClickListeners(moleArray);
        setActionThread();

        for (Thread thread : actionThread) { thread.start(); }
        ((GameActivity) requireActivity()).timerThread.start();

        return myView;
    }

    public interface onClickScore {
        void onClickSetScore(TextView scoreView, Integer scoreValue);
    }

    public void setOnClickListeners(ImageButton[] imageArray) {
        for (ImageButton currentButton : imageArray) {
            currentButton.setOnClickListener(view -> {
                if (view.getTag().toString().equals("on")) {
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
        ObjectAnimator up = ObjectAnimator.ofFloat(mole, "translationY", -140);
        ObjectAnimator down = ObjectAnimator.ofFloat(mole, "translationY", 0);
        animatorSet.play(up).before(down);
        animatorSet.setDuration(speed);
        return animatorSet;
    }

    public void setActionThread() {
        actionHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                moleArray[msg.arg1].setTag("on");
                moleArray[msg.arg1].setImageResource(R.drawable.mole);
                moleAction(moleArray[msg.arg1], msg.arg2).start();
            }
        };

        tagHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                moleArray[msg.arg1].setTag("off");
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
        for (int i = 0; i < moleArray.length; i++) {
            setAction action = new setAction(i);
            actionThread[i] = new Thread(action);
        }
    }
}