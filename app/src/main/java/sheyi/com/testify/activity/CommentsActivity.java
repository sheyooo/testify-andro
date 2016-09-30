package sheyi.com.testify.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sheyi.com.testify.R;
import sheyi.com.testify.adapter.CommentsAdapter;
import sheyi.com.testify.models.Comment;
import sheyi.com.testify.models.sendables.CommentPayload;
import sheyi.com.testify.rest.ApiClient;
import sheyi.com.testify.rest.ApiInterface;

public class CommentsActivity extends AppCompatActivity {
    private RecyclerView commentRecyclerView;
    private CommentsAdapter adapter;
    private int postID;
    private ProgressBar loader;
    private List<Comment> comments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Comments");

        loader = (ProgressBar) findViewById(R.id.progressBarComments);

        commentRecyclerView = (RecyclerView) findViewById(R.id.commentRecyclerView);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        postID = getIntent().getExtras().getInt("post_id");

        adapter = new CommentsAdapter(this, comments);
        commentRecyclerView.setAdapter(adapter);

        loadComments();
    }

    public void loadComments() {
        ApiInterface api = ApiClient.getApi(this);
        Call<List<Comment>> call = api.getPostComments(postID);
        loader.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                comments.addAll(response.body());
                adapter.notifyDataSetChanged();

                loader.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                loadComments();
                Toast.makeText(CommentsActivity.this, "Retrying", Toast.LENGTH_SHORT).show();

                loader.setVisibility(View.GONE);
            }
        });
    }

    public void sendComment(View v) {
        final EditText commentET = (EditText) findViewById(R.id.commentEditText);
        final String comment = commentET.getText().toString();

        CommentPayload cp = new CommentPayload();
        cp.setText(comment);

        ApiInterface api = ApiClient.getApi(this);
        Call<Comment> call = api.postComment(postID, cp);

        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                comments.add(response.body());
                adapter.notifyDataSetChanged();

                commentET.setText("");
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                Toast.makeText(CommentsActivity.this, "An error occurred, try again", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
