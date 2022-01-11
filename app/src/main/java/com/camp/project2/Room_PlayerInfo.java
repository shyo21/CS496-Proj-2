package com.camp.project2;

public class Room_PlayerInfo {
    private static String iconColor;
    private static String userName;

    public void setIconColor(String color) {
        iconColor = color;
    }

    public void setUserName(String name) {
        userName = name;
    }

    public String getIconColor() {
        return this.iconColor;
    }

    public String getUserName() {
        return this.userName;
    }
}
