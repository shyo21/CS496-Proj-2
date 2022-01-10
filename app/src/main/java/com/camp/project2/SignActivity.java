package com.camp.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignActivity extends AppCompatActivity implements View.OnClickListener{
    private final String TAG = "SignupActivityLog";
    public TextView name;
    public TextView id;
    public TextView pwd;
    public EditText putid;
    public EditText putpwd;
    public EditText putname;
    public Button register;
    public RetrofitClient retrofitClient;
    public Retrofit retrofit;
    public RetrofitService myInterface;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        id = findViewById(R.id.user_id);
        pwd = findViewById(R.id.user_pwd);
        name = findViewById(R.id.user_name);
        putid = findViewById(R.id.putid);
        putpwd = findViewById(R.id.putpwd);
        putname = findViewById(R.id.putname);
        register = findViewById(R.id.register);
        register.setOnClickListener(this);
        retrofitClient = new RetrofitClient();
        retrofit = retrofitClient.getClient();
        myInterface = retrofit.create(RetrofitService.class);

    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.register:
                String reqid = putid.getText().toString();
                String reqpwd = putpwd.getText().toString();
                String reqname = putname.getText().toString();
                Call<ResponseBody> call_post = myInterface.signUp(reqname, reqid, reqpwd);
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
                Intent intent2 = new Intent(this, MainActivity.class);
                startActivity(intent2);
                break;
        }
    }
}