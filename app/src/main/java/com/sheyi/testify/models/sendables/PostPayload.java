package com.sheyi.testify.models.sendables;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PostPayload {

    @SerializedName("post")
    @Expose
    private String post;
    @SerializedName("anonymous")
    @Expose
    private Integer anonymous;
    @SerializedName("categories")
    @Expose
    private List<Integer> categories = new ArrayList<Integer>();
    @SerializedName("images")
    @Expose
    private List<Object> images = new ArrayList<Object>();
    @SerializedName("socket_id")
    @Expose
    private String socketId;

    /**
     *
     * @return
     * The post
     */
    public String getPost() {
        return post;
    }

    /**
     *
     * @param post
     * The post
     */
    public void setPost(String post) {
        this.post = post;
    }

    /**
     *
     * @return
     * The anonymous
     */
    public Integer getAnonymous() {
        return anonymous;
    }

    /**
     *
     * @param anonymous
     * The anonymous
     */
    public void setAnonymous(Integer anonymous) {
        this.anonymous = anonymous;
    }

    /**
     *
     * @return
     * The categories
     */
    public List<Integer> getCategories() {
        return categories;
    }

    /**
     *
     * @param categories
     * The categories
     */
    public void setCategories(List<Integer> categories) {
        this.categories = categories;
    }

    /**
     *
     * @return
     * The images
     */
    public List<Object> getImages() {
        return images;
    }

    /**
     *
     * @param images
     * The images
     */
    public void setImages(List<Object> images) {
        this.images = images;
    }

    /**
     *
     * @return
     * The socketId
     */
    public String getSocketId() {
        return socketId;
    }

    /**
     *
     * @param socketId
     * The socket_id
     */
    public void setSocketId(String socketId) {
        this.socketId = socketId;
    }

}