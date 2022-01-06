package com.camp.project2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
    String text;
    ArrayList<Integer> displaySize;
    Button startButton;

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

        //playerList.getLayoutParams().width = (int) (0.9 * displayWidth);
        //playerList.getLayoutParams().height = (int) (0.35 * displayHeight);

        ArrayList<String> list = new ArrayList<>();
        for (int i=0; i<100; i++) {
            list.add(i, String.format("TEXT %d", i));
        }

        playerList.setHasFixedSize(true);
        playerList.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerAdapter adapter = new recyclerAdapter(list);
        playerList.setAdapter(adapter);

        text = "https://google.com";

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GameActivity.class);
                startActivity(intent);
            }
        });

        return myView;
    }

    private ArrayList<Integer> getDisplaySize() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ArrayList<Integer> displaySize = new ArrayList<>();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        displaySize.add(0, pixel_to_dp(displayMetrics.widthPixels));
        displaySize.add(1, pixel_to_dp(displayMetrics.heightPixels));
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
