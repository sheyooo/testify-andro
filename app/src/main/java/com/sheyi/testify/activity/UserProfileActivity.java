package com.sheyi.testify.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sheyi.testify.R;
import com.sheyi.testify.models.User;
import com.sheyi.testify.rest.ApiClient;
import com.sheyi.testify.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends AppCompatActivity {

    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userID = getIntent().getExtras().getInt("user_id");
        setContentView(R.layout.activity_user_profile);

        loadUserProfile();
    }

    private void loadUserProfile() {
        ApiInterface api = ApiClient.getApi(this);

        Call<User> call = api.getUser(userID);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}
