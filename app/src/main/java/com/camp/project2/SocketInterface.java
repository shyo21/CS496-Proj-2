package com.camp.project2;

import android.app.Activity;
import android.net.SocketKeepalive;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.net.URL;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
//mSocket.emit("EVENT_NAME",data) 데이터 담아서 이벤트 발생시키기
//mSocket.emit("EVENT_NAME"): 데이터없이 이벤트 발생시키기
//mSocket.on(“EVENT_NAME”,mListener) : 리스너(콜백함수 포함) 서버에서 이벤트 발생시켰을 때 리스터 함수가 실행된다.
public class SocketInterface extends AsyncTask {
    private static Socket mysocket = null;
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

    public Socket getinstance() {
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

    public void send(){
        JSONObject data = new JSONObject();
        try {
            data.put("message", "master");
            mysocket.emit("SEND", data);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /*

    public void sendtest(){
        JSONObject data = new JSONObject();
        try {
            data.put("test", "test complete");
            mysocket.emit("TESTSEND", data);
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/

    public void createroom(){
        JSONObject data = new JSONObject();
        try{
            Userinfo userinfo = new Userinfo();
            String userid = userinfo.getuserid();
            System.out.println(userid);
            data.put("userid", userid);
            mysocket.emit("CREATEROOM", data);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
