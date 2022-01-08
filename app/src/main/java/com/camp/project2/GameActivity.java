package com.camp.project2;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity implements Game_screen.onClickScore {

    public MyViewPager viewPager;
    public Game_screen g_screen;
    public Result_screen r_screen;
    public Handler timerHandler;
    public Thread timerThread;
    public int time = 30;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        g_screen = new Game_screen();
        r_screen = new Result_screen();

        viewPager = findViewById(R.id.viewpager);
        viewPager.setPagingEnabled(false);
        ViewpagerAdapter viewpagerAdapter = new ViewpagerAdapter(getSupportFragmentManager());
        viewpagerAdapter.addItem(g_screen);
        viewpagerAdapter.addItem(r_screen);
        viewPager.setAdapter(viewpagerAdapter);

        setTimerThread();
    }

    public void setTimerThread() {
        timerHandler = new Handler();
        final Runnable setTimerValue = () -> { g_screen.stopwatch.setText(String.valueOf(time)); };

        class setTimer implements Runnable {
            @Override
            public void run(){
                while(time > 0){
                    try { Thread.sleep(1000);
                    } catch (Exception e){ e.printStackTrace(); }
                    timerHandler.post(setTimerValue);
                    time--;
                }
                g_screen.timeEnded = true;
                viewPager.setCurrentItem(1, true);
            }
        }

        setTimer timer = new setTimer();
        timerThread = new Thread(timer);
    }

    @Override
    public void onClickSetScore(TextView scoreView, Integer scoreValue) {
        scoreView.setText(String.valueOf(scoreValue));
    }

}
