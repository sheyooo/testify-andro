package com.sheyi.testify.repo;


import android.app.Activity;

import com.sheyi.testify.models.Post;
import com.sheyi.testify.rest.ApiClient;
import com.sheyi.testify.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
// TODO: TO IMPLEMENT OFFLINE AVAILABILITY

public class PostsRepository {

    //private List<Post> posts;
    private Activity activity;

    public PostsRepository(Activity activity) {
        this.activity = activity;
    }


    public void getPosts() {

        ApiInterface api = ApiClient.getApi(activity);

        Call<List<Post>> call = api.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });



    }

}
