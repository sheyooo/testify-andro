package com.sheyi.testify.rest;

import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.sheyi.testify.helper.AuthenticationHelper;

public class ApiClient {

    public static final String BASE_URL = "https://testify-staging.herokuapp.com/api/v1/";
    private static Retrofit retrofit = null;
    private static OkHttpClient.Builder builderClient = null;
    private static String token;


    public static ApiInterface getApi(Context context) {
        if (retrofit == null) {
            builderClient = new OkHttpClient.Builder();
            builderClient.addNetworkInterceptor(new StethoInterceptor());

            token = AuthenticationHelper.getToken(context);

            if (token != null) {
                builderClient.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request r = chain.request();
                        r = r.newBuilder()
                                .addHeader("Authorization", "Bearer " + token)
                                .build();

                        return chain.proceed(r);
                    }
                });
            }

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(builderClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(ApiInterface.class);
    }

    public static void notifyChangeToken() {
        retrofit = null;
    }
}