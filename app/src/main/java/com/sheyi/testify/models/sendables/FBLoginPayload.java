package com.sheyi.testify.models.sendables;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FBLoginPayload {

    @SerializedName("fb_access_token")
    @Expose
    private String fbAccessToken;

    /**
     *
     * @return
     * The fbAccessToken
     */
    public String getFbAccessToken() {
        return fbAccessToken;
    }

    /**
     *
     * @param fbAccessToken
     * The fb_access_token
     */
    public void setFbAccessToken(String fbAccessToken) {
        this.fbAccessToken = fbAccessToken;
    }

}