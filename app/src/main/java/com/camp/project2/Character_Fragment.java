package com.camp.project2;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.Socket;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Character_Fragment extends Fragment implements View.OnClickListener {
    private final String TAG = "CharacterScreenLog";
    private ImageButton make_room;
    private ImageButton find_room;
    private Button red_button;
    private Button yellow_button;
    private Button green_button;
    private Button blue_button;
    private Button purple_button;
    private Button black_button;
    private ImageView characterview;
    public SocketInterface socketInterface;
    public String room_number = null;
    public String address = null;
    public int a = 0;
    RoomActivity activity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (RoomActivity)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("here");
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_character, container, false);
        make_room = rootView.findViewById(R.id.character_makeRoom);
        find_room = rootView.findViewById(R.id.character_findRoom);
        find_room.setOnClickListener(this);
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

        make_room.setOnClickListener(view -> {
            int position = activity.viewPager.getCurrentItem();
            if (position == 0) {
                socketInterface = new SocketInterface(getActivity());
                Socket mysocket = socketInterface.getInstance();
                socketInterface.createroom();
                mysocket.on("CREATEROOM", args -> requireActivity().runOnUiThread(() -> {
                    try {
                        JSONObject data = (JSONObject) args[0];
                        System.out.println(data.getString("userid"));
                        room_number = data.getString("room_num");
                        address = "http://192.249.18.122:443/" + "room" + room_number;
                        activity.r_screen.text = address;
                        activity.r_screen.qrCode.setImageBitmap(qrCodeMaker(address));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }));
                activity.viewPager.setCurrentItem(1, true);
            } else {
                activity.viewPager.setCurrentItem(0, true);
            }
        });

        find_room.setOnClickListener(view -> {
            socketInterface = new SocketInterface(getActivity());
            Socket mysocket = socketInterface.getInstance();
            socketInterface.joinroom();
            int position2 = activity.viewPager.getCurrentItem();
            socketInterface.showmember();

            mysocket.on("SHOWMEMBER",args -> requireActivity().runOnUiThread(() -> {
                try {
                    JSONObject data = (JSONObject) args[0];
                    String ONE = data.getString("one");
                    System.out.println(ONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }));
            /*
            socketInterface.asktable();
            mysocket.on("ASKTABLE", args -> requireActivity().runOnUiThread(() -> {
                try {
                    JSONObject data = (JSONObject) args[0];
                    String number = data.getString("num");
                    for(int j = 0; j < 5; j ++){
                        if(String.valueOf(j).equals(number)){
                            a = j;
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }));

            for(int b = 0; b < a; b++){
                socketInterface.line(b);
                mysocket.on("LINE", args -> requireActivity().runOnUiThread(() -> {
                    try {
                        JSONObject data = (JSONObject) args[0];
                        String number = data.getString("num");
                        //String id = data.get("id");


                        for(int j = 0; j < 5; j ++){
                            if(String.valueOf(j).equals(number)){
                                a = j;
                                break;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }));
            }
            */

            activity.viewPager.setCurrentItem(1, true);
        });
        return rootView;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view){
        User_Info userinfo = new User_Info();
        switch (view.getId()){
            case R.id.red_button:
                userinfo.setUserColor("red");
                System.out.println("red");
                characterview.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.red));
                break;
            case R.id.yellow_button:
                userinfo.setUserColor("yellow");
                System.out.println("yellow");
                characterview.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.yellow));
                break;
            case R.id.green_button:
                userinfo.setUserColor("green");
                characterview.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.green));
                break;
            case R.id.blue_button:
                userinfo.setUserColor("blue");
                characterview.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.blue));
                break;
            case R.id.purple_button:
                userinfo.setUserColor("purple");
                characterview.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.sky_blue));
                break;
            case R.id.black_button:
                userinfo.setUserColor("black");
                characterview.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.purple));
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