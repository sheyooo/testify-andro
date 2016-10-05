package com.sheyi.testify.util;

import java.util.ArrayList;
import java.util.List;

import com.sheyi.testify.models.Category;

public class Application {
    private static List<Category> categories = new ArrayList<>();

    public static List<Category> getCategories() {
        if (categories.size() < 1) {
            return categories;
        } else {
            return categories;
        }

    }
}
