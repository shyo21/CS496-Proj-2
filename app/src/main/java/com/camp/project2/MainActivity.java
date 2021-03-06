package com.camp.project2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private final String TAG = "MainActivityLog";
    public EditText id;
    public EditText pwd;
    public ImageButton login;
    public ImageButton reg;
    public String s_id;
    public String s_pwd;
    public RetrofitService myInterface;
    public Retrofit retrofit;
    //public Login_info login_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        id = findViewById(R.id.main_id);
        pwd = findViewById(R.id.main_pwd);
        login = findViewById(R.id.main_loginButton);
        login.setOnClickListener(this);
        reg = findViewById(R.id.main_signUpButton);
        reg.setOnClickListener(this);

        //login_info = new Login_info(s_id, s_pwd);

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        clientBuilder.addInterceptor(loggingInterceptor);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.249.18.122:443/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(clientBuilder.build())
                .build();
        myInterface = retrofit.create(RetrofitService.class);
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.main_loginButton:
                System.out.println("we are here");
                s_id = id.getText().toString();
                s_pwd = pwd.getText().toString();
                Login_Info login_info = new Login_Info(s_id, s_pwd);

                Call<ResponseBody> call_post = myInterface.logIn(s_id, s_pwd);
                //Call<ResponseBody> call_post = myInterface.postFunc(jsonObject);
                //Call<ResponseBody> call_post = myInterface.logIn(login_info);

                call_post.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                assert response.body() != null;
                                String result = response.body().string();
                                Log.v(TAG, "result = " + result);
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            User_Info userinfo = new User_Info();
                            userinfo.set_login_check(true);
                            userinfo.setUserId(s_id);

                            System.out.println("login id = " + s_id);
                            changeScreen();
                        } else {
                            Log.v(TAG, "error = " + response.code());
                            Toast.makeText(getApplicationContext(), "error = " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        Log.v(TAG, "Fail");
                        Toast.makeText(getApplicationContext(), "Response Fail", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.main_signUpButton:
                Intent intent2 = new Intent(this, SignActivity.class);
                startActivity(intent2);
                break;
        }

    }

    public void changeScreen(){
        Intent intent = new Intent(this, RoomActivity.class);
        startActivity(intent);
    }
}
