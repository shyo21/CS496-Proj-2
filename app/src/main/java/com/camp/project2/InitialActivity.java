package com.camp.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class InitialActivity extends AppCompatActivity{
    //onAttach() -> fragment를 activtiy에 attach
    //oncreate -> activtiy/ fragment 객체 생성 + 변수 초기화 시
    //oncreateview -> inflater를 사용해서 view를 불러와 view에 레이아웃을 매칭. view를 return.
    //onStart() : Fragment 화면에 표시
    //onResume() : Fragment 화면에 로딩이 끝났을 떄 호


    // 다른 fragment를 add하게 되면 onPause(). onStop(). onDestroyView()함수 실행.
    //onPause() : 화면중지되면 호출
    //onStop() : fragment 화면삭제
    //onDestryview: view 리소스 해제
    //
    // 다른화면으로 replace 할 떄

    //onDetach() : Fragment 와 Activity 의 연결고리가 끊길 때 호출

    ////안드로이드 어댑터(Adapter): "사용자가 정의한 데이터 리스트를 입력으로 받아들여 화면에 표시할 뷰(View)들을 생성"
    //page adapter는 abstract class
    private ViewpagerAdapter viewpagerAdapter;
    public MyViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        viewPager = findViewById(R.id.viewpager);
        viewPager.setPagingEnabled(false);
        viewpagerAdapter = new ViewpagerAdapter(getSupportFragmentManager());

        Character_screen c_screen = new Character_screen();
        Room_screen r_screen = new Room_screen();

        viewpagerAdapter.addItem(c_screen);
        viewpagerAdapter.addItem(r_screen);

        viewPager.setAdapter(viewpagerAdapter);

    }




    @Override
    public void onBackPressed(){//액티비티에서 동작.
        viewPager.setCurrentItem(0);
    }

}