package com.sheyi.testify.orm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sheyi.testify.models.Category;
import com.sheyi.testify.models.User;

import java.util.ArrayList;
import java.util.List;
// TODO: TO IMPLEMENT OFFLINE AVAILABILITY

public class PostORM {
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("hash_id")
    @Expose
    private String hashId;
    @SerializedName("anonymous")
    @Expose
    private Boolean anonymous;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("favorited")
    @Expose
    private Boolean favorited;
    @SerializedName("tapped_into")
    @Expose
    private Boolean tappedInto;
    @SerializedName("amen")
    @Expose
    private Boolean amen;
    @SerializedName("prayer")
    @Expose
    private Boolean prayer;
    @SerializedName("amens_count")
    @Expose
    private Integer amensCount;
    @SerializedName("favorites_count")
    @Expose
    private Integer favoritesCount;
    @SerializedName("taps_count")
    @Expose
    private Integer tapsCount;
    @SerializedName("comments_count")
    @Expose
    private Integer commentsCount;
    @SerializedName("images")
    @Expose
    private List<Object> images = new ArrayList<Object>();
    @SerializedName("categories")
    @Expose
    private List<Category> categories = new ArrayList<Category>();
    @SerializedName("user")
    @Expose
    private User user;

    /**
     *
     * @return
     * The id
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     * The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     * @return
     * The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @param updatedAt
     * The updated_at
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     *
     * @return
     * The hashId
     */
    public String getHashId() {
        return hashId;
    }

    /**
     *
     * @param hashId
     * The hash_id
     */
    public void setHashId(String hashId) {
        this.hashId = hashId;
    }

    /**
     *
     * @return
     * The anonymous
     */
    public Boolean getAnonymous() {
        return anonymous;
    }

    /**
     *
     * @param anonymous
     * The anonymous
     */
    public void setAnonymous(Boolean anonymous) {
        this.anonymous = anonymous;
    }

    /**
     *
     * @return
     * The text
     */
    public String getText() {
        return text;
    }

    /**
     *
     * @param text
     * The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     *
     * @return
     * The favorited
     */
    public Boolean getFavorited() {
        return favorited;
    }

    /**
     *
     * @param favorited
     * The favorited
     */
    public void setFavorited(Boolean favorited) {
        this.favorited = favorited;
    }

    /**
     *
     * @return
     * The tappedInto
     */
    public Boolean getTappedInto() {
        return tappedInto;
    }

    /**
     *
     * @param tappedInto
     * The tapped_into
     */
    public void setTappedInto(Boolean tappedInto) {
        this.tappedInto = tappedInto;
    }

    /**
     *
     * @return
     * The amen
     */
    public Boolean getAmen() {
        return amen;
    }

    /**
     *
     * @param amen
     * The amen
     */
    public void setAmen(Boolean amen) {
        this.amen = amen;
    }

    /**
     *
     * @return
     * The prayer
     */
    public Boolean getPrayer() {
        return prayer;
    }

    /**
     *
     * @param prayer
     * The prayer
     */
    public void setPrayer(Boolean prayer) {
        this.prayer = prayer;
    }

    /**
     *
     * @return
     * The amensCount
     */
    public Integer getAmensCount() {
        return amensCount;
    }

    /**
     *
     * @param amensCount
     * The amens_count
     */
    public void setAmensCount(Integer amensCount) {
        this.amensCount = amensCount;
    }

    /**
     *
     * @return
     * The favoritesCount
     */
    public Integer getFavoritesCount() {
        return favoritesCount;
    }

    /**
     *
     * @param favoritesCount
     * The favorites_count
     */
    public void setFavoritesCount(Integer favoritesCount) {
        this.favoritesCount = favoritesCount;
    }

    /**
     *
     * @return
     * The tapsCount
     */
    public Integer getTapsCount() {
        return tapsCount;
    }

    /**
     *
     * @param tapsCount
     * The taps_count
     */
    public void setTapsCount(Integer tapsCount) {
        this.tapsCount = tapsCount;
    }

    /**
     *
     * @return
     * The commentsCount
     */
    public Integer getCommentsCount() {
        return commentsCount;
    }

    /**
     *
     * @param commentsCount
     * The comments_count
     */
    public void setCommentsCount(Integer commentsCount) {
        this.commentsCount = commentsCount;
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
     * The categories
     */
    public List<Category> getCategories() {
        return categories;
    }

    /**
     *
     * @param categories
     * The categories
     */
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    /**
     *
     * @return
     * The user
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user
     * The user
     */
    public void setUser(User user) {
        this.user = user;
    }

}
