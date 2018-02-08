package com.harlie.leehounshell.musicsearch;


import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

public class MusicSearchApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public void onCreate() {
        super.onCreate();
        MusicSearchApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MusicSearchApplication.context;
    }
}
