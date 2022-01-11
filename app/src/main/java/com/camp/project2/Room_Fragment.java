package com.camp.project2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;


public class Room_Fragment extends Fragment {
    public View myView;
    public ImageView qrCode;
    public RecyclerView playerList;
    public Room_RecyclerAdapter adapter;
    public String text;
    public ImageButton startButton;
    public User_Info userinfo;
    public ArrayList<Room_PlayerInfo> mList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userinfo = new User_Info();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_room, container, false);
        qrCode = myView.findViewById(R.id.room_qrCode);
        playerList = myView.findViewById(R.id.room_playerList);
        startButton = myView.findViewById(R.id.room_startButton);

        text = "http://google.com";

        playerList.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new Room_RecyclerAdapter(mList);
        playerList.setAdapter(adapter);

        addItem("blue", "player1");

        qrCode.setImageBitmap(qrCodeMaker(text));

        startButton.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), GameActivity.class);
            startActivity(intent);
        });


        /*
        try {
            System.out.println("소켓 연결 직");
            URL url = new URL("http://192.249.18.122:443");
            System.out.println("여기");
            mysocket = IO.socket(url.toURI());
            mysocket.connect();
            mysocket.on("SEND", new Emitter.Listener() {
                @Override
                public void call(final Object... args) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject data = (JSONObject) args[0];
                                System.out.println(data.getString("message"));
                                System.out.println("\n");
                            } catch(Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
            JSONObject data = new JSONObject();
            try {
                data.put("message", "master");
                mysocket.emit("SEND", data);
            }catch(Exception e){
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return myView;
    }

    public void addItem(String color, String name) {
        Room_PlayerInfo item = new Room_PlayerInfo();
        item.setIconColor(color);
        item.setUserName(name);

        mList.add(item);
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
