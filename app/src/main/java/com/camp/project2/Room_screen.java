package com.camp.project2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.emitter.Emitter;

public class Room_screen extends Fragment {
    View myView;
    ImageView qrCode;
    RecyclerView playerList;
    recyclerAdapter adapter;
    String text;
    ArrayList<Integer> displaySize;
    Button startButton;
    private static io.socket.client.Socket mysocket;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_room_screen, container, false);
        qrCode = myView.findViewById(R.id.qrCode);
        playerList = myView.findViewById(R.id.playerList);
        startButton = myView.findViewById(R.id.startButton);

        displaySize = getDisplaySize();
        int displayWidth = displaySize.get(0);
        int displayHeight = displaySize.get(1);

        ArrayList<String> list = new ArrayList<>();
        for (int i=0; i<100; i++) {
            list.add(i, String.format("TEXT %d", i));
        }

        text = "https://google.com";

        playerList.getLayoutParams().width = (int) (0.9 * displayWidth);
        playerList.getLayoutParams().height = (int) (0.35 * displayHeight);
        playerList.setHasFixedSize(true);
        playerList.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new recyclerAdapter(list);
        playerList.setAdapter(adapter);

        qrCode.getLayoutParams().width = (int) (0.3 * displayWidth);
        qrCode.getLayoutParams().height = (int) (0.3 * displayWidth);
        qrCode.setImageBitmap(qrCodeMaker(text));

        startButton.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), GameActivity.class);
            startActivity(intent);
        });


        System.out.println("gameactivity.java 42");
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

            System.out.println("여기");
            JSONObject data = new JSONObject();
            try {
                data.put("message", "master");
                mysocket.emit("SEND", data);
            }catch(Exception e){
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myView;
    }

    private Bitmap qrCodeMaker(String url) {
        Bitmap qrcode = null;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(url, BarcodeFormat.QR_CODE, 1000, 1000);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);


            qrcode = Bitmap.createBitmap(bitmap,120,120,760,760);
        } catch (WriterException e) { e.printStackTrace(); }

        return qrcode;
    }

    private ArrayList<Integer> getDisplaySize() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ArrayList<Integer> displaySize = new ArrayList<>();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        displaySize.add(0, displayMetrics.widthPixels);
        displaySize.add(1, displayMetrics.heightPixels);
        return displaySize;
    }

    private Integer pixel_to_dp (int pixel) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return pixel / displayMetrics.densityDpi;
    }

    private Integer dp_to_pixel (int dp) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return dp * displayMetrics.densityDpi;
    }
}
