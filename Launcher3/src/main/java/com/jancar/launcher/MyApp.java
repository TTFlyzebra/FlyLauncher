package com.jancar.launcher;

import android.app.Application;

import com.android.launcher3.LauncherAppState;

public class MyApp extends Application {

    public LauncherAppState launcherApp;

    @Override
    public void onCreate() {
        super.onCreate();
        LauncherAppState.setApplicationContext(getApplicationContext());
        launcherApp = LauncherAppState.getInstance();
    }
}
