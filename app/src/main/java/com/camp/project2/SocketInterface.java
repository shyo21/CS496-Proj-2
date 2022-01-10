package com.camp.project2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.net.URL;

import io.socket.client.IO;
import io.socket.client.Socket;

//mSocket.emit("EVENT_NAME",data) 데이터 담아서 이벤트 발생시키기
//mSocket.emit("EVENT_NAME"): 데이터없이 이벤트 발생시키기
//mSocket.on(“EVENT_NAME”,mListener) : 리스너(콜백함수 포함) 서버에서 이벤트 발생시켰을 때 리스터 함수가 실행된다.
public class SocketInterface extends AsyncTask {
    private static Socket mysocket = null;
    @SuppressLint("StaticFieldLeak")
    private static Activity act = null;

    @Override
    protected Object doInBackground(Object[] objects) {
        onProgressUpdate(1);
        return null;
    }

    protected void onProgressUpdate(int value) {
        System.out.println("function operating");

    }

    public SocketInterface(Activity activity) {
        if(act == null){
            act = activity;
        }
    }

    public Socket getInstance() {
        if (mysocket == null) {
            try {
                URL url = new URL("http://192.249.18.122:443");
                mysocket = IO.socket(url.toURI());
                mysocket.connect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mysocket;
    }

    public void joinroom() {
        JSONObject data = new JSONObject();
        try {
            User_Info userinfo = new User_Info();
            String userid = userinfo.getUserId();
            String usercolor = userinfo.getUserColor();
            data.put("userid", userid);
            data.put("usercolor", usercolor);
            mysocket.emit("JOINROOM", data);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void createroom(){
        JSONObject data = new JSONObject();
        try{
            User_Info userinfo = new User_Info();
            String userid = userinfo.getUserId();
            String usercolor = userinfo.getUserColor();
            System.out.println(userid);
            data.put("userid", userid);
            data.put("usercolor", usercolor);
            mysocket.emit("CREATEROOM", data);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
