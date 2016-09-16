package sheyi.com.testify.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sheyi.com.testify.R;
import sheyi.com.testify.helper.AuthenticationHelper;
import sheyi.com.testify.models.User;

public class DashboardActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        if (AuthenticationHelper.isLoggedIn(this)) {
            initCurrentUser();
        } else {
            resetUser();
        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);

        ActionBar ab = getSupportActionBar();

        ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Testify");

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(
                R.id.fragment_navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout),
                mToolbar);
        drawerFragment.setDrawerListener(this);

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.container_body, new HomeFragment());
        tx.commit();
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

        if (id == R.id.action_logout) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(intent);

            AuthenticationHelper.logout(this);

            finish();
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void initCurrentUser() {
        AuthenticationHelper.getUser(this, new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                TextView currentUserNameTV = (TextView) findViewById(R.id.currentUserNameTV);
                currentUserNameTV.setText(response.body().getName());

                ImageView currentUserAvatar = (ImageView) findViewById(R.id.currentUserAvatarIV);
                Picasso.with(DashboardActivity.this)
//                        .load(response.body().getAvatar())
                        .load("http://lorempixel.com/400/200")
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

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                //title = getString(R.string.title_home);
                break;
            case 1:
                //fragment = new FriendsFragment();
                //title = getString(R.string.title_friends);
                break;
            case 2:
                //fragment = new MessagesFragment();
                //title = getString(R.string.title_messages);
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }

    public void fabNewPostOnClick(View v) {
        Intent i = new Intent(this, NewPostActivity.class);
        startActivity(i);
    }
}