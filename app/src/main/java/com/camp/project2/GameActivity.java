package com.camp.project2;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
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

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

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
        @SuppressLint("SetTextI18n")
        final Runnable setTimerValue = () -> {
            if (time >= 10) { g_screen.stopwatch.setText("00:"+ time); }
            else { g_screen.stopwatch.setText("00:0"+ time); }
        };

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
