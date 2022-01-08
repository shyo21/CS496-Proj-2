package com.camp.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
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
    private final String TAG = "MainActivityLog";
    private ViewpagerAdapter viewpagerAdapter;
    public MyViewPager viewPager;
    public EditText id;
    public EditText pwd;
    public Button reg;
    public String s_id;
    public String s_pwd;
    public RetrofitService myInterface;
    public Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewpager);
        viewPager.setPagingEnabled(false);
        viewpagerAdapter = new ViewpagerAdapter(getSupportFragmentManager());

        id = findViewById(R.id.id);
        pwd = findViewById(R.id.pwd);
        reg = findViewById(R.id.reg);
        reg.setOnClickListener(this);

        s_id = id.getText().toString();
        s_pwd = pwd.getText().toString();

        Character_screen c_screen = new Character_screen();
        Room_screen r_screen = new Room_screen();

        viewpagerAdapter.addItem(c_screen);
        viewpagerAdapter.addItem(r_screen);

        viewPager.setAdapter(viewpagerAdapter);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.249.18.122:443/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myInterface = retrofit.create(RetrofitService.class);
        /*
        RetrofitClient a = new RetrofitClient();

        retrofit = a.getClient();
        myInterface = retrofit.create(RetrofitService.class);*?
        /*
        retrofit = new Retrofit.Builder().baseUrl("https://172.10.18.122:80/").addConverterFactory(GsonConverterFactory.create()).build();
        myInterface = retrofit.create(RetrofitService.class);
        */
    }


    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.reg:
                System.out.println("we are here");
                Call<ResponseBody> call_post = myInterface.postFunc("post data");
                call_post.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                String result = response.body().string();
                                Log.v(TAG, "result = " + result);
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.v(TAG, "error = " + String.valueOf(response.code()));
                            Toast.makeText(getApplicationContext(), "error = " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.v(TAG, "Fail");
                        Toast.makeText(getApplicationContext(), "Response Fail", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }

    }


    @Override
    public void onBackPressed(){//액티비티에서 동작.
        viewPager.setCurrentItem(0);
    }

}