package com.camp.project2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    public MyViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        viewPager = findViewById(R.id.viewpager);
        viewPager.setPagingEnabled(false);
        ViewpagerAdapter viewpagerAdapter = new ViewpagerAdapter(getSupportFragmentManager());

        Game_screen g_screen = new Game_screen();
        Result_screen r_screen = new Result_screen();

        viewpagerAdapter.addItem(g_screen);
        viewpagerAdapter.addItem(r_screen);

        viewPager.setAdapter(viewpagerAdapter);
    }
}
