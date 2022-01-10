package com.camp.project2;

public class Userinfo {
    private static String userid = null;
    private static boolean login_check = false;
    private static String roomurl = null;
    public Userinfo(){

    }

    public void setroomurl(String url){
        if(roomurl == null){
            roomurl = url;
        }
    }

    public void setuserid(String id){
        if(userid == null){
            userid = id;
        }
    }
    public String getuserid(){
        return userid;
    }
    public String getroomurl(){
        return roomurl;
    }
    public void setlogin_check(boolean check){
        if(login_check == false){
            login_check = check;
        }
    }
}
