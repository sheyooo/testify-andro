package sheyi.com.testify.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sheyi.com.testify.R;
import sheyi.com.testify.helper.AuthenticationHelper;
import sheyi.com.testify.models.JWToken;
import sheyi.com.testify.models.LoginPayload;
import sheyi.com.testify.rest.ApiClient;
import sheyi.com.testify.rest.ApiInterface;

public class MainActivity extends AppCompatActivity {

    private EditText userET,
            passwordET;
    private ApiInterface api;
    private SharedPreferences pref;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (AuthenticationHelper.isLoggedIn(this)) {
            startActivity(new Intent(this, DashboardActivity.class));
            finish();
            return ;
        }

        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        userET = (EditText) findViewById(R.id.usernameET);
        passwordET = (EditText) findViewById(R.id.passwordET);

        api = ApiClient.getClient(this).create(ApiInterface.class);
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void loginButtonOnClick(View v) {
        String user = userET.getText().toString();
        String pass = passwordET.getText().toString();

        Call<JWToken> call = api.login(new LoginPayload(user, pass));

        call.enqueue(new Callback<JWToken>() {
            @Override
            public void onResponse(Call<JWToken> call, Response<JWToken> response) {
                if (response.code() == 200) {
                    AuthenticationHelper.storeToken(MainActivity.this, response.body().getToken());

                    startActivity(new Intent(MainActivity.this, DashboardActivity.class));
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Wrong login information, please check and try again", Toast.LENGTH_SHORT).show();
                    passwordET.setText("");
                }
            }

            @Override
            public void onFailure(Call<JWToken> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Check your internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void facebookLogin(View v) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginResult.getAccessToken();
                Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(MainActivity.this, "An error occurred please try again later.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
