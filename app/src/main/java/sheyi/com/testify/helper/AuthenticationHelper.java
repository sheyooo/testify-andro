package sheyi.com.testify.helper;

import android.content.Context;
import android.preference.PreferenceManager;

import sheyi.com.testify.rest.ApiClient;


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
}
