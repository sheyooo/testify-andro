package com.sheyi.testify.orm;

import io.realm.RealmObject;

public class CategoryORM extends RealmObject {

    private String name;
    private String type;
    private int sort;
    private int apiID;

    public CategoryORM() {}

    public CategoryORM(int apiID, String name, String type, int sort) {
        this.apiID = apiID;
        this.name = name;
        this.type = type;
        this.sort = sort;
    }

    public int getApiID() {
        return apiID;
    }

    public void setApiID(int apiID) {
        this.apiID = apiID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
