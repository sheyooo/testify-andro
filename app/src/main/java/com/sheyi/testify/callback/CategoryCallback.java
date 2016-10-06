package com.sheyi.testify.callback;

import com.sheyi.testify.orm.CategoryORM;

import java.util.List;

public interface CategoryCallback {

    void onCategorySet(List<CategoryORM> l);

}
