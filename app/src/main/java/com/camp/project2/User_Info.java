package com.camp.project2;

public class User_Info {
    private static String userId = null;
    private static boolean login_check = false;
    private static String roomUrl = null;
    private static String userColor = "white";

    public User_Info(){ }

    public void setRoomUrl(String url){
        if(roomUrl == null){
            roomUrl = url;
        }
    }

    public void setUserId(String id){
        if(userId == null){
            userId = id;
        }
    }

    public void setUserColor(String col){
        userColor = col;
    }

    public String getUserId(){
        return userId;
    }
    public String getRoomUrl(){
        return roomUrl;
    }
    public String getUserColor() {return userColor;}

    public void set_login_check(boolean check) {
        if(!login_check){
            login_check = check;
        }
    }
}
