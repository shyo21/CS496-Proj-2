package com.camp.project2;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import io.socket.client.IO;
import io.socket.emitter.Emitter;

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

/*
    public void socketcommunication(){
        socket = new Socket();
        try{
            socket.connect(new InetSocketAddress("http://192.249.18.122:443/", 433));
            is = socket.getInputStream();
            os = socket.getOutputStream();

            byte[] byteArr = null;
            String msg = "Hello server";

            byteArr = msg.getBytes("UTF-8");
            os.write(byteArr);
            os.flush();
            System.out.println("Data transmitted Ok!");

            byteArr = new byte[512];
            int readBytecount = is.read();
            if(readBytecount == -1){
                throw new IOException();
            }

            msg = new String(byteArr, 0, readBytecount, "UTF-8");
            System.out.println("Data received OK!");
            System.out.println("Message : " + msg);

            is.close();
            os.close();

            socket.close();
        }catch (Exception e){
            e.printStackTrace();

        }
    }

 */
}
