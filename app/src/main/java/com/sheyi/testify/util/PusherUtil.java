package com.sheyi.testify.util;


import android.app.Activity;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.util.HttpAuthorizer;
import com.sheyi.testify.helper.AuthenticationHelper;
import com.sheyi.testify.rest.ApiClient;

import java.util.HashMap;

public class PusherUtil {

    private static Pusher pusher;

    public static Pusher getInstance(Activity activity) {
        if (pusher != null) {
            return pusher;
        } else {
            HttpAuthorizer authorize = new HttpAuthorizer(ApiClient.BASE_URL + "pusher/auth");
            HashMap<String, String> hmap = new HashMap<>();
            hmap.put("Authorization", "Bearer " + AuthenticationHelper.getToken(activity));

            authorize.setHeaders(hmap);

            PusherOptions opt = new PusherOptions();
            opt.setEncrypted(true)
                .setAuthorizer(authorize);

            Pusher p = new Pusher("b5fa9d11972af2e0b8d1", opt);

            PusherUtil.pusher = p;

            return p;
        }
    }
}
