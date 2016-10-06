package com.sheyi.testify;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.orm.SugarContext;

public class TestifyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(getApplicationContext());
        Stetho.initializeWithDefaults(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }
}
