package com.chennupatibalu.filedrive;

import android.app.Application;

import com.firebase.client.Firebase;

public class FileDrive extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
