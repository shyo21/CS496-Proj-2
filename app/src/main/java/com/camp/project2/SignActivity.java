package com.camp.project2;

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

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignActivity extends AppCompatActivity implements View.OnClickListener{
    private final String TAG = "SignupActivityLog";
    public EditText putId;
    public EditText putPwd;
    public EditText putName;
    public ImageButton register;
    public RetrofitClient retrofitClient;
    public Retrofit retrofit;
    public RetrofitService myInterface;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        putId = findViewById(R.id.sign_id);
        putPwd = findViewById(R.id.sign_password);
        putName = findViewById(R.id.sign_name);
        register = findViewById(R.id.sign_signUpButton);
        register.setOnClickListener(this);
        retrofitClient = new RetrofitClient();
        retrofit = retrofitClient.getClient();
        myInterface = retrofit.create(RetrofitService.class);

    }

    @Override
    public void onClick(View view){
        if (view.getId() == R.id.sign_signUpButton) {
            String reqId = putId.getText().toString();
            String reqPwd = putPwd.getText().toString();
            String reqName = putName.getText().toString();
            Call<ResponseBody> call_post = myInterface.signUp(reqName, reqId, reqPwd);
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
            Intent intent2 = new Intent(this, qrScanActivity.class);
            startActivity(intent2);
        }
    }
}