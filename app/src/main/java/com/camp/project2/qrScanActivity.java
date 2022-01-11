package com.camp.project2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.camera.CameraSettings;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class qrScanActivity extends AppCompatActivity {

    CaptureManager capture;
    DecoratedBarcodeView barcodeScannerView;
    CameraSettings cameraSettings = new CameraSettings();
    String cameraSettingData = "";
    boolean captureFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscan);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        try {
            Intent intent = getIntent();
            cameraSettingData = String.valueOf(intent.getStringExtra("camera"));

            if(cameraSettingData.equals("front")){ cameraSettings.setRequestedCameraId(1); }
            else if(cameraSettingData.equals("back")){ cameraSettings.setRequestedCameraId(0); }
            else { cameraSettings.setRequestedCameraId(0); }
        }
        catch (Exception e){ e.printStackTrace(); }

        barcodeScannerView = findViewById(R.id.barcodeScannerView);
        barcodeScannerView.getBarcodeView().setCameraSettings(cameraSettings);
        capture = new CaptureManager(this, barcodeScannerView);
        capture.initializeFromIntent(this.getIntent(),savedInstanceState);
        capture.decode();

        barcodeScannerView.decodeContinuous(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                try {
                    String result_data;
                    if(!captureFlag){
                        result_data = result.toString();
                        byte[] result_byte;
                        if(result_data.contains("[") && result_data.contains("]")
                                && result_data.contains(",")){
                            result_byte = getByteArray(result_data);
                            result_data = new String(result_byte, StandardCharsets.UTF_8);
                        }

                        String alertTittle = "[QR 스캔 정보 확인]";
                        String alertMessage = "[정보]"+"\n"+"\n"+ result_data;
                        String buttonYes = "다시 스캔";
                        String buttonNo = "종료";
                        new AlertDialog.Builder(qrScanActivity.this)
                                .setTitle(alertTittle)
                                .setMessage(alertMessage)
                                .setCancelable(false)
                                .setPositiveButton(buttonYes, (dialog, which) -> captureFlag = false)
                                .setNegativeButton(buttonNo, (dialog, which) -> {
                                    try {
                                        finish();
                                        overridePendingTransition(0,0);
                                    }
                                    catch (Exception e){ e.printStackTrace(); }
                                }).show();
                        captureFlag = true;
                    }
                }
                catch (Exception e){ e.printStackTrace(); }
            }
            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            try {
                finish();
                overridePendingTransition(0,0);
            }
            catch (Exception e){ e.printStackTrace(); }
        }
        return true;
    }

    @Override
    protected void onResume(){
        super.onResume();
        try { capture.onResume(); }
        catch (Exception e){ e.printStackTrace(); }
    }

    @Override
    protected void onPause(){
        super.onPause();
        try { capture.onPause(); }
        catch (Exception e){ e.printStackTrace(); }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        try { capture.onDestroy(); }
        catch (Exception e){ e.printStackTrace(); }
    }

    public static byte [] getByteArray(String data) {
        byte[] ok_result = null;
        try {
            data = data.replaceAll("\\[", "");
            data = data.replaceAll("\\]", "");
            data = data.replaceAll(" ", "");

            int check = 0;

            for(int i=0; i<data.length(); i++) {
                if(data.charAt(i) == ',') {
                    check ++;
                }
            }

            ok_result = new byte [check+1];

            if(data.length() > 0) {
                if(check > 0) {
                    for(int j=0; j<=check; j++) {
                        ok_result [j] = Byte.parseByte(data.split("[,]")[j]);
                    }
                }
                else { //데이터가 한개만 저장된 경우
                    ok_result [0] = Byte.parseByte(data);
                }
            }
            else { ok_result [0] = 0x00; }
        }
        catch(Exception e) { e.printStackTrace(); }
        return ok_result;
    }
}
