package sheyi.com.testify.rest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import sheyi.com.testify.models.Category;
import sheyi.com.testify.models.JWToken;
import sheyi.com.testify.models.Post;
import sheyi.com.testify.models.User;
import sheyi.com.testify.models.sendables.FBLoginPayload;
import sheyi.com.testify.models.sendables.LoginPayload;

public interface ApiInterface {
    @POST("authenticate")
    Call<JWToken> login(@Body LoginPayload authInfo);

    @POST("fb-token")
    Call<JWToken> loginFb(@Body FBLoginPayload fbToken);

    @GET("users/{hashID}")
    Call<User> getUser(@Path("hashID") String hashID);

    @GET("posts")
    Call<ArrayList<Post>> getPosts();

    @GET("categories")
    Call<List<Category>> getCategories();

    @GET("movie/{id}")
    Call<Post> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);
}