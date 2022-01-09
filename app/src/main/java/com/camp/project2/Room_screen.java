package com.camp.project2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class Room_screen extends Fragment {
    View myView;
    ImageView qrCode;
    RecyclerView playerList;
    recyclerAdapter adapter;
    String text;
    ArrayList<Integer> displaySize;
    ImageButton startButton;

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

        playerList.getLayoutParams().width = (int) (0.85 * displayWidth);
        playerList.getLayoutParams().height = (int) (0.35 * displayHeight);
        playerList.setHasFixedSize(true);
        playerList.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new recyclerAdapter(list);
        playerList.setAdapter(adapter);

        qrCode.getLayoutParams().width = (int) (0.3 * displayWidth);
        qrCode.getLayoutParams().height = (int) (0.3 * displayWidth);
        qrCode.setImageBitmap(qrCodeMaker(text));

        startButton.getLayoutParams().width = (int) (0.13 * displayWidth);
        startButton.getLayoutParams().height = (int) (0.3 * displayWidth);

        startButton.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), GameActivity.class);
            startActivity(intent);
        });

        return myView;
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
