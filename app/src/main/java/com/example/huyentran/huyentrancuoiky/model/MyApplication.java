package com.example.huyentran.huyentrancuoiky.model;

import android.app.Application;

public class MyApplication extends Application {
    private int UserId;

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }
}
