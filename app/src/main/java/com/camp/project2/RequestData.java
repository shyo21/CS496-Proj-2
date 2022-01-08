package com.camp.project2;

import com.google.gson.annotations.SerializedName;

public class RequestData {
    @SerializedName("id")
    private String id;

    @SerializedName("pwd")
    private String pwd;

    public RequestData(String id, String pwd){
        this.id = id;
        this.pwd = pwd;
    }
}
