package com.camp.project2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GameActivity extends AppCompatActivity {

    public MyViewPager viewPager;
    public Game_screen g_screen;
    public Result_screen r_screen;
    public static Handler mHandler;
    public Thread watch_thread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        viewPager = findViewById(R.id.viewpager);
        viewPager.setPagingEnabled(true);
        ViewpagerAdapter viewpagerAdapter = new ViewpagerAdapter(getSupportFragmentManager());

        g_screen = new Game_screen();
        r_screen = new Result_screen();

        viewpagerAdapter.addItem(g_screen);
        viewpagerAdapter.addItem(r_screen);

        viewPager.setAdapter(viewpagerAdapter);

        mHandler =new Handler();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String strTime = sdf.format(cal.getTime());
                g_screen.stopwatch.setText(strTime);
            }
        };

        class NewRunnable implements Runnable {
            @Override
            public void run(){
                while(true){
                    try {
                        Thread.sleep(1000);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    mHandler.post(runnable);
                }
            }
        }

        NewRunnable nr = new NewRunnable();
        watch_thread = new Thread(nr);
    }
}
