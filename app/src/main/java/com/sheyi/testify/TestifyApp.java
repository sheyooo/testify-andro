package com.sheyi.testify;

import android.app.Application;

import com.facebook.stetho.Stetho;

import io.realm.Realm;

public class TestifyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        Stetho.initializeWithDefaults(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
