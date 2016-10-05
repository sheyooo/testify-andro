package com.sheyi.testify.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("sort")
    @Expose
    private Integer sort;
    @SerializedName("pivot")
    @Expose
    private Pivot pivot;

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The sort
     */
    public Integer getSort() {
        return sort;
    }

    /**
     *
     * @param sort
     * The sort
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     *
     * @return
     * The pivot
     */
    public Pivot getPivot() {
        return pivot;
    }

    /**
     *
     * @param pivot
     * The pivot
     */
    public void setPivot(Pivot pivot) {
        this.pivot = pivot;
    }

}