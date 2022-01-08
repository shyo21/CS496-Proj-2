package com.camp.project2;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class Character_screen extends Fragment implements View.OnClickListener {
    private Button screen_change;
    private Button red_button;
    private Button yellow_button;
    private Button green_button;
    private Button blue_button;
    private Button purple_button;
    private Button black_button;
    private TextView testview;
    private ImageView characterview;

    InitialActivity activity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
        activity = (InitialActivity)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("here");
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_character_screen, container, false);

        screen_change = rootView.findViewById(R.id.room_create_button);
        characterview = rootView.findViewById(R.id.character_img);
        red_button = rootView.findViewById(R.id.red_button);
        red_button.setOnClickListener(this);
        yellow_button = rootView.findViewById(R.id.yellow_button);
        yellow_button.setOnClickListener(this);
        green_button = rootView.findViewById(R.id.green_button);
        green_button.setOnClickListener(this);
        blue_button = rootView.findViewById(R.id.blue_button);
        blue_button.setOnClickListener(this);
        purple_button = rootView.findViewById(R.id.purple_button);
        purple_button.setOnClickListener(this);
        black_button = rootView.findViewById(R.id.black_button);
        black_button.setOnClickListener(this);

        screen_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = activity.viewPager.getCurrentItem();
                //System.out.println("position = "+ String.valueOf(position));

                if(position == 0){
                    activity.viewPager.setCurrentItem(1, true);
                }
                else{
                    activity.viewPager.setCurrentItem(0, true);
                }
            }
        });
        return rootView;
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.red_button:
                characterview.setBackgroundColor(Color.parseColor("#F44336"));
                break;
            case R.id.yellow_button:
                characterview.setBackgroundColor(Color.parseColor("#FFEB3B"));
                break;
            case R.id.green_button:
                characterview.setBackgroundColor(Color.parseColor("#42FF3B"));
                break;
            case R.id.blue_button:
                characterview.setBackgroundColor(Color.parseColor("#3B4FFF"));
                break;
            case R.id.purple_button:
                characterview.setBackgroundColor(Color.parseColor("#D505CA"));
                break;
            case R.id.black_button:
                characterview.setBackgroundColor(Color.parseColor("#020202"));
                break;
        }
    }

}