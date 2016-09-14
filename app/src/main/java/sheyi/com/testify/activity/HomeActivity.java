package sheyi.com.testify.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sheyi.com.testify.adapter.PostsAdapter;
import sheyi.com.testify.R;
import sheyi.com.testify.models.Post;
import sheyi.com.testify.rest.ApiClient;
import sheyi.com.testify.rest.ApiInterface;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recycler;
    private PostsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recycler = (RecyclerView) findViewById(R.id.post_recycler_view);

        ArrayList<Post> posts = new ArrayList<>();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<ArrayList<Post>> call = apiService.getPosts();
        call.enqueue(new Callback<ArrayList<Post>>() {
            @Override
            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                ArrayList<Post> post = response.body();
                adapter = new PostsAdapter(HomeActivity.this, post);

                recycler.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Couldnt not connect to internet", Toast.LENGTH_SHORT).show();
            }
        });

        adapter = new PostsAdapter(this, posts);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);
    }

    public void fabNewPostOnClick(View v) {
        Intent i = new Intent(this, NewPostActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
