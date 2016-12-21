package com.sheyi.testify.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.sheyi.testify.R;
import com.sheyi.testify.models.User;
import com.sheyi.testify.rest.ApiClient;
import com.sheyi.testify.rest.ApiInterface;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends AppCompatActivity {

    private int userID;
    private ImageView userProfileImage;
    private TextView userProfileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userID = getIntent().getExtras().getInt("user_id");
        setContentView(R.layout.activity_user_profile);

        userProfileImage = (ImageView) findViewById(R.id.userProfileAvatar);
        userProfileName = (TextView) findViewById(R.id.userProfileName);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadUserProfile();
    }

    private void loadUserProfile() {
        ApiInterface api = ApiClient.getApi(this);

        final Transformation roundedTransformation = new RoundedTransformationBuilder()
                .borderColor(Color.WHITE)
                .borderWidthDp(9)
                .cornerRadiusDp(300)
                .oval(false)
                .build();

        Call<User> call = api.getUser(userID);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User userProfile = response.body();

                Picasso.with(UserProfileActivity.this)
                        .load(userProfile.getAvatar())
                        .transform(roundedTransformation)
                        .into(userProfileImage);

                userProfileName.setText(userProfile.getName());

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}
