package com.sheyi.testify.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
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

import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.pusher.client.Pusher;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.ChannelEventListener;
import com.pusher.client.channel.SubscriptionEventListener;
import com.sheyi.testify.R;
import com.sheyi.testify.adapter.PostsAdapter;
import com.sheyi.testify.helper.AuthenticationHelper;
import com.sheyi.testify.models.Post;
import com.sheyi.testify.models.User;
import com.sheyi.testify.rest.ApiClient;
import com.sheyi.testify.rest.ApiInterface;
import com.sheyi.testify.util.PusherUtil;
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

    private SwipeRefreshLayout swipeRefreshContainer;

    private RecyclerView recycler;
    private PostsAdapter adapter;

    private ProgressBar loaderBar;
    private LinearLayout retryLL;

    private List<Post> posts = new ArrayList<>();
    private Pusher pusher;
    private Channel postsStream;

    private static String POST_CHANNEL = "posts-stream";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);

        pusher = PusherUtil.getInstance(this);

        getByIDs();

        adapter = new PostsAdapter(NavDrawerActivity.this, posts);
        recycler.setLayoutManager(new LinearLayoutManager(NavDrawerActivity.this));
        recycler.setAdapter(adapter);

        retryLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPosts();
                Toast.makeText(NavDrawerActivity.this, "Reconnecting...", Toast.LENGTH_SHORT).show();
            }
        });

        swipeRefreshContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadPosts();
            }
        });
        swipeRefreshContainer.setColorSchemeResources(R.color.colorPrimary);

        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        runSetup();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        pusher.unsubscribe(POST_CHANNEL);
    }

    private void getByIDs() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        loaderBar = (ProgressBar) findViewById(R.id.homeSpinner);
        retryLL = (LinearLayout) findViewById(R.id.retryLL);

        swipeRefreshContainer = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshContainer);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        recycler = (RecyclerView) findViewById(R.id.postRecyclerView);

    }

    private void runSetup() {
        if (AuthenticationHelper.isLoggedIn(this)) {
            initCurrentUser();
        } else {
            resetUser();
        }
        getSupportActionBar().setTitle("Testify");

        loadPosts();
        startPusher();
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
        } else if (id == R.id.action_logout) {
            AuthenticationHelper.logout(this);
            startActivity(new Intent(this, MainActivity.class));
            finish();
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

                TextView currentUserEmailTV = (TextView) findViewById(R.id.currentUserEmailTV);
                currentUserEmailTV.setText(response.body().getEmail());

                ImageView currentUserAvatar = (ImageView) findViewById(R.id.currentUserAvatarIV);
                Picasso.with(NavDrawerActivity.this)
                        .load(response.body().getAvatar())
                        .error(R.drawable.testify_user_icon)
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
        swipeRefreshContainer.setRefreshing(true);

        ApiInterface apiService = ApiClient.getApi(this);

        Call<List<Post>> call = apiService.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                posts.clear();
                posts.addAll(response.body());
                adapter.notifyDataSetChanged();

                loaderBar.setVisibility(View.GONE);
                retryLL.setVisibility(View.GONE);
                swipeRefreshContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                loaderBar.setVisibility(View.GONE);
                retryLL.setVisibility(View.VISIBLE);

                swipeRefreshContainer.setRefreshing(false);

                Toast.makeText(NavDrawerActivity.this, "Couldn't not connect to internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startPusher() {

        postsStream = pusher.subscribe(POST_CHANNEL, new ChannelEventListener() {
            @Override
            public void onSubscriptionSucceeded(String s) {
                NavDrawerActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(NavDrawerActivity.this, "Sub success", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onEvent(String s, String s1, String s2) {

            }
        });

        postsStream.bind("new_post", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channel, String event, final String data) {

                NavDrawerActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson g = new Gson();
                        Post p = g.fromJson(data, Post.class);

                        posts.add(0, p);
                        adapter.notifyItemInserted(0);

                        recycler.scrollToPosition(0);
                    }
                });
            }
        });


        pusher.connect();
    }

}
