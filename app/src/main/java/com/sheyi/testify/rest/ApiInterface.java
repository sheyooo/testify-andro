package com.sheyi.testify.rest;

import com.sheyi.testify.models.Category;
import com.sheyi.testify.models.Comment;
import com.sheyi.testify.models.JWToken;
import com.sheyi.testify.models.Post;
import com.sheyi.testify.models.User;
import com.sheyi.testify.models.receivables.ActionStatus;
import com.sheyi.testify.models.sendables.CommentPayload;
import com.sheyi.testify.models.sendables.FBLoginPayload;
import com.sheyi.testify.models.sendables.LoginPayload;
import com.sheyi.testify.models.sendables.PostPayload;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @POST("authenticate")
    Call<JWToken> login(@Body LoginPayload authInfo);

    @POST("fb-token")
    Call<JWToken> loginFb(@Body FBLoginPayload fbToken);

    @GET("users/{hashID}")
    Call<User> getUser(@Path("hashID") String hashID);

    @GET("users/{userID}")
    Call<User> getUser(@Path("userID") int userID);

    @GET("posts")
    Call<List<Post>> getPosts();

    @GET("posts/{id}/comments")
    Call<List<Comment>> getPostComments(@Path("id") int id);

    @POST("posts/{id}/comments")
    Call<Comment> postComment(@Path("id") int id, @Body CommentPayload comment);

    @POST("posts/{id}/amens")
    Call<ActionStatus> sayAmen(@Path("id") int id);

    @POST("posts/{id}/taps")
    Call<ActionStatus> tapInto(@Path("id") int id);

    @DELETE("posts/{id}/taps")
    Call<ActionStatus> tapIntoUndo(@Path("id") int id);

    @POST("posts")
    Call<Post> sendPost(@Body PostPayload postPayload);

    @GET("categories")
    Call<List<Category>> getCategories();

    @GET("movie/{id}")
    Call<Post> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);
}