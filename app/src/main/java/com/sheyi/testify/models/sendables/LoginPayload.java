package com.sheyi.testify.models.sendables;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginPayload {

    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("password")
    @Expose
    private String password;


    public LoginPayload(String u, String p) {
        this.user = u;
        this.password = p;
    }

    /**
     *
     * @return
     * The user
     */
    public String getUser() {
        return user;
    }

    /**
     *
     * @param user
     * The user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     *
     * @return
     * The password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     * The password
     */
    public void setPassword(String password) {
        this.password = password;
    }

}