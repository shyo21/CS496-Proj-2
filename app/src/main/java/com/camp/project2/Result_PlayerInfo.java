package com.camp.project2;

public class Result_PlayerInfo {
    private String iconColor;
    private String userName;
    private Integer userScore;

    public void setIconColor(String color) {
        iconColor = color;
    }

    public void setUserName(String name) {
        userName = name;
    }

    public void setUserScore(int score) { userScore = score; }

    public String getIconColor() {
        return this.iconColor;
    }

    public String getUserName() {
        return this.userName;
    }

    public Integer getUserScore() { return this.userScore; }
}