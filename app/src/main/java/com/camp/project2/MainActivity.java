package com.camp.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewpager);
        viewPager.setPagingEnabled(false);
        viewpagerAdapter = new ViewpagerAdapter(getSupportFragmentManager());

        Character_screen c_screen = new Character_screen();
        Room_screen r_screen = new Room_screen();

        viewpagerAdapter.addItem(c_screen);
        viewpagerAdapter.addItem(r_screen);

        viewPager.setAdapter(viewpagerAdapter);
    }
}