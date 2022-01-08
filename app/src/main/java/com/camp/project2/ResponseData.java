package com.camp.project2;


import com.google.gson.annotations.SerializedName;

public class ResponseData {
    @SerializedName("status") //serializedname으로 json 객체와 변수를 매칭.
    private int status;

    @SerializedName("id")
    private String id;

    @SerializedName("pwd")
    private String pwd;

    @SerializedName("message")
    private String message;

    public String getid(){
        return this.id;
    }

    public String getpwd(){
        return this.pwd;
    }

    public int getStatus(){
        return this.status;
    }

    public String getMessage() {
        return message;
    }
};