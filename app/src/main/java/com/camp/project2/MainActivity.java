package com.camp.project2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private final String TAG = "MainActivityLog";
    public EditText id;
    public EditText pwd;
    public Button login;
    public Button reg;
    public String s_id;
    public String s_pwd;
    public RetrofitService myInterface;
    public Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id = findViewById(R.id.id);
        pwd = findViewById(R.id.pwd);
        login = findViewById(R.id.login);
        login.setOnClickListener(this);
        reg = findViewById(R.id.reg);
        reg.setOnClickListener(this);

        s_id = id.getText().toString();
        s_pwd = pwd.getText().toString();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.249.18.122:443/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myInterface = retrofit.create(RetrofitService.class);


    }


    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.login:
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
                            changescreen();
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
            case R.id.reg:
                break;
        }

    }

    public void changescreen(){
        Intent intent = new Intent(this, InitialActivity.class);
        startActivity(intent);
    }
}
