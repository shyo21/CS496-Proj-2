package com.camp.project2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import io.socket.client.Socket;

public class GameActivity extends AppCompatActivity implements Game_Fragment.onClickScore {

    private MyViewPager viewPager;
    public Game_Fragment g_screen;
    public Result_Fragment r_screen;
    public Handler timerHandler;
    public Thread timerThread;
    public int time = 5;
    public Activity act;
    public SocketInterface socketInterface;
    public Socket mysocket;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        act = this;

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        g_screen = new Game_Fragment();
        r_screen = new Result_Fragment();

        viewPager = findViewById(R.id.gameActivity_viewpager);
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
                if(time == 0){
                    User_Info user_info = new User_Info();
                    user_info.setscore(g_screen.scoreValue);
                    socketInterface = new SocketInterface(act);
                    mysocket = socketInterface.getInstance();
                    socketInterface.score();
                    int score = user_info.getScore();
                    r_screen.addItem(user_info.getUserColor(),user_info.getUserId(),score);
                }
                g_screen.timeEnded = true;

                socketInterface.arrangement();
                mysocket.on("ARRANGEMENT", args->runOnUiThread(() -> {
                    try {
                        JSONObject data = (JSONObject)args[0];
                        JSONArray a = data.getJSONArray("array");
                        System.out.println(a);
                        System.out.println("여기여ㅣㄱ 이건가 ?");



                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }));



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
