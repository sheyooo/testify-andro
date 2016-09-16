package sheyi.com.testify;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class TestifyApp extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
