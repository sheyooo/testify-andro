package sheyi.com.testify.rest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import sheyi.com.testify.models.JWToken;
import sheyi.com.testify.models.LoginPayload;
import sheyi.com.testify.models.Post;


public interface ApiInterface {
    @POST("authenticate")
    Call<JWToken> login(@Body LoginPayload authInfo);

    @GET("posts")
    Call<ArrayList<Post>> getPosts();

    @GET("movie/{id}")
    Call<Post> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);
}