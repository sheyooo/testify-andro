package sheyi.com.testify;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sheyi.com.testify.models.Comment;
import sheyi.com.testify.rest.ApiClient;
import sheyi.com.testify.rest.ApiInterface;

public class CommentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        int postID = getIntent().getExtras().getInt("post_id");

        ApiInterface api = ApiClient.getClient(this).create(ApiInterface.class);
        Call<List<Comment>> call = api.getPostComments(postID);

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {

            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

            }
        });
    }
}
