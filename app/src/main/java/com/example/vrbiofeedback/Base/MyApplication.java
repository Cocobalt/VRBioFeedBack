package com.example.vrbiofeedback.Base;

import android.app.Application;

public class MyApplication extends Application {
    private static String UserID;
    private static String UserBR; // User selected Breath Rate
    private static String SessionDuration; // User selected Session Duration in min
    private static String scene;

    public static String getScene() {
        return scene;
    }

    public static void setScene(String scene) {
        MyApplication.scene = scene;
    }

    public static String getUserID() {
        return UserID;
    }

    public static void setUserID(String userID) {
        UserID = userID;
    }

    public static String getUserBR() {
        return UserBR;
    }

    public static void setUserBR(String userBR) {
        UserBR = userBR;
    }

    public static String getSessionDuration() {
        return SessionDuration;
    }

    public static void setSessionDuration(String sessionDuration) {
        SessionDuration = sessionDuration;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }




}