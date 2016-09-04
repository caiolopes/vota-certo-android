package com.votacerto;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.drawee.backends.pipeline.Fresco;

public class MyApplication extends Application {
    public static final String PREF = "VOTACERTO";
    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        Fresco.initialize(this);
    }
}
