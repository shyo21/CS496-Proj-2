package com.camp.project2;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


public class Character_screen extends Fragment implements View.OnClickListener {
    private final String TAG = "CharacterScreenLog";
    private ImageButton make_room;
    private ImageButton find_room;
    private Button red_button;
    private Button yellow_button;
    private Button green_button;
    private Button blue_button;
    private Button purple_button;
    private Button black_button;
    private TextView testview;
    private ImageView characterview;
    public SocketInterface socketInterface;
    public String room_number = null;
    public String address = null;

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

        make_room = rootView.findViewById(R.id.room_create_button);
        find_room = rootView.findViewById(R.id.find_room);
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

        make_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = activity.viewPager.getCurrentItem();
                if(position == 0){
                    socketInterface = new SocketInterface(getActivity());
                    Socket mysocket = socketInterface.getinstance();
                    socketInterface.createroom();
                    mysocket.on("CREATEROOM", new Emitter.Listener() {
                        @Override
                        public void call(final Object... args) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        JSONObject data = (JSONObject) args[0];
                                        System.out.println(data.getString("userid"));
                                        room_number = data.getString("room_num");
                                        address = "http://192.249.18.122:443/"+"room"+room_number;//roomnumber 추가
                                        activity.r_screen.text = address;
                                        activity.r_screen.qrCode.setImageBitmap(qrCodeMaker(address));
                                        /*
                                        Retrofit retrofit = new RetrofitClient().getClient();
                                        RetrofitService myInterface = retrofit.create(RetrofitService.class);
                                        Call<ResponseBody> call_post = myInterface.logIn(s_id, s_pwd);


                                        call_post.enqueue(new Callback<ResponseBody>() {
                                            @Override
                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                if (response.isSuccessful()) {
                                                    try {
                                                        String result = response.body().string();
                                                        Log.v(TAG, "result = " + result);
                                                        Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }

                                                } else {
                                                    Log.v(TAG, "error = " + String.valueOf(response.code()));
                                                    Toast.makeText(getActivity().getApplicationContext(), "error = " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                Log.v(TAG, "Fail");
                                                Toast.makeText(getActivity().getApplicationContext(), "Response Fail", Toast.LENGTH_SHORT).show();
                                            }
                                        });*/

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    });
                    activity.viewPager.setCurrentItem(1, true);
                }
                else{
                    activity.viewPager.setCurrentItem(0, true);
                }
            }
        });

        find_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return rootView;
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.red_button:
                characterview.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.red));
                break;
            case R.id.yellow_button:
                characterview.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.yellow));
                break;
            case R.id.green_button:
                characterview.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.green));
                break;
            case R.id.blue_button:
                characterview.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.blue));
                break;
            case R.id.purple_button:
                characterview.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.sky_blue));
                break;
            case R.id.black_button:
                characterview.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.purple));
                break;
        }
    }
    private Bitmap qrCodeMaker(String url) {
        Bitmap qrcode = null;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(url, BarcodeFormat.QR_CODE, 1000, 1000);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

            for(int x = 0; x < 1000; x++) {
                for (int y = 0; y < 1000; y++) {
                    if (bitmap.getPixel(x,y) == Color.WHITE) { bitmap.setPixel(x,y,Color.TRANSPARENT); }}}

            qrcode = Bitmap.createBitmap(bitmap,120,120,760,760);
        } catch (WriterException e) { e.printStackTrace(); }

        return qrcode;
    }

}