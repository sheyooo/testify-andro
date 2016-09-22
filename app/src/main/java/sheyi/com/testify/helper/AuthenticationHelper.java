package sheyi.com.testify.helper;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Base64;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import sheyi.com.testify.models.User;
import sheyi.com.testify.models.app.JWTDecoded;
import sheyi.com.testify.rest.ApiClient;
import sheyi.com.testify.rest.ApiInterface;


public class AuthenticationHelper {

    public static String getToken(Context context) {
       return PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).getString("token", null);
    }

    public static boolean isLoggedIn(Context context) {
        String token = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext())
                .getString("token", null);

        if (token != null) {
            return true;
        } else {
            return false;
        }
    }

    public static void storeToken(Context context, String token) {
        PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).edit().putString("token", token).commit();

        // Make retrofit rebuild when token changes
        ApiClient.notifyChangeToken();
    }

    public static void logout(Context context) {
        PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).edit().remove("token").commit();

        // Make retrofit rebuild when token changes
        ApiClient.notifyChangeToken();
    }

    public static void getUser(Context context, Callback<User> callback) {
        String token = getToken(context);

        String[] jwtPieces = token.split("\\.");

        byte[] jsonBytes = Base64.decode(jwtPieces[1], Base64.DEFAULT);

        String jsonString = new String(jsonBytes);

        Gson gson = new Gson();
        JWTDecoded j = gson.fromJson(jsonString, JWTDecoded.class);

        ApiInterface api = ApiClient.getApi(context);
        Call<User> call = api.getUser(j.hash_id);

        call.enqueue(callback);
    }
}
