package com.camp.project2;

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

        class NewRunnable implements Runnable {
            @Override
            public void run(){
                while(true){
                    try {
                        Thread.sleep(1000);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    mHandler.sendEmptyMessage(0);
                }
            }
        }
        mHandler =new Handler() { //이벤트가 발생했을 때 호출되는 함
            @Override
            public void handleMessage (Message msg){
                System.out.println("handler enter");
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String strtime = sdf.format(cal.getTime());
                System.out.println(strtime);
                g_screen.stopwatch.setText(strtime);
            }
        };
        NewRunnable nr = new NewRunnable();
        watch_thread = new Thread(nr);
    }
}
