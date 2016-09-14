package sheyi.com.testify.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pivot {

    @SerializedName("post_id")
    @Expose
    private Integer postId;
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;

    /**
     *
     * @return
     * The postId
     */
    public Integer getPostId() {
        return postId;
    }

    /**
     *
     * @param postId
     * The post_id
     */
    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    /**
     *
     * @return
     * The categoryId
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     *
     * @param categoryId
     * The category_id
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

}