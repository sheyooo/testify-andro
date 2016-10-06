package com.sheyi.testify.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.sheyi.testify.R;
import com.sheyi.testify.adapter.PostsAdapter;
import com.sheyi.testify.helper.AuthenticationHelper;
import com.sheyi.testify.models.Post;
import com.sheyi.testify.models.User;
import com.sheyi.testify.rest.ApiClient;
import com.sheyi.testify.rest.ApiInterface;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NavDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private DrawerLayout drawer;

    private RecyclerView recycler;
    private PostsAdapter adapter;

    private ProgressBar loaderBar;
    private LinearLayout retryLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        loaderBar = (ProgressBar) findViewById(R.id.homeSpinner);
        retryLL = (LinearLayout) findViewById(R.id.retryLL);

        retryLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPosts();
                Toast.makeText(NavDrawerActivity.this, "Reconnecting...", Toast.LENGTH_SHORT).show();
            }
        });
        setSupportActionBar(mToolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        runSetup();
    }

    private void runSetup() {
        if (AuthenticationHelper.isLoggedIn(this)) {
            initCurrentUser();
        } else {
            resetUser();
        }
        getSupportActionBar().setTitle("Testify");

        loadPosts();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void fabNewPostOnClick(View v) {
        Intent i = new Intent(this, NewPostActivity.class);
        startActivity(i);
    }

    private void initCurrentUser() {
        AuthenticationHelper.getUser(this, new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Transformation roundedTransformation = new RoundedTransformationBuilder()
                        .borderColor(Color.WHITE)
                        .borderWidthDp(2)
                        .cornerRadiusDp(100)
                        .oval(false)
                        .build();

                TextView currentUserNameTV = (TextView) findViewById(R.id.currentUserNameTV);
                currentUserNameTV.setText(response.body().getName());

                ImageView currentUserAvatar = (ImageView) findViewById(R.id.currentUserAvatarIV);
                Picasso.with(NavDrawerActivity.this)
//                        .load(response.body().getAvatar())
                        .load("http://lorempixel.com/400/200")
                        .transform(roundedTransformation)
                        .resize(100, 100)
                        .centerCrop()
                        .into(currentUserAvatar);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                initCurrentUser();
            }
        });
    }

    private void resetUser() {
        TextView currentUserNameTV = (TextView) findViewById(R.id.currentUserNameTV);
        currentUserNameTV.setText("Guest");

        ImageView currentUserAvatar = (ImageView) findViewById(R.id.currentUserAvatarIV);
        currentUserAvatar.setImageResource(R.drawable.testify_user_icon);
    }

    public void loadPosts() {
        loaderBar.setVisibility(View.VISIBLE);
        retryLL.setVisibility(View.GONE);

        recycler = (RecyclerView) findViewById(R.id.postRecyclerView);

        ArrayList<Post> posts = new ArrayList<>();

        ApiInterface apiService = ApiClient.getApi(this);

        Call<List<Post>> call = apiService.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                List<Post> posts = response.body();
                adapter = new PostsAdapter(NavDrawerActivity.this, posts);

                recycler.setAdapter(adapter);
                loaderBar.setVisibility(View.GONE);
                retryLL.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                loaderBar.setVisibility(View.GONE);
                retryLL.setVisibility(View.VISIBLE);

                Toast.makeText(NavDrawerActivity.this, "Couldn't not connect to internet", Toast.LENGTH_SHORT).show();
            }
        });

        adapter = new PostsAdapter(NavDrawerActivity.this, posts);
        recycler.setLayoutManager(new LinearLayoutManager(NavDrawerActivity.this));
        recycler.setAdapter(adapter);
    }

}
