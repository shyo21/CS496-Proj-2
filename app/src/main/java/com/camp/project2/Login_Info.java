package com.camp.project2;

import com.google.gson.annotations.SerializedName;

public class Login_Info {
    @SerializedName("id")
    public String id;

    @SerializedName("pwd")
    public String pwd;

    public Login_Info(String u_id, String u_pwd){
        this.id = u_id;
        this.pwd = u_pwd;
    }
}
