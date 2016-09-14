package sheyi.com.testify.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;

import java.util.Arrays;

import sheyi.com.testify.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void loginButtonOnClick(View v) {
        startActivity(new Intent(this, DashboardActivity.class));

        finish();
    }

    public void facebookLogin(View v) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
    }
}
