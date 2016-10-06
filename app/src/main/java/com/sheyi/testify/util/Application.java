package com.sheyi.testify.util;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.orm.SugarRecord;
import com.sheyi.testify.R;
import com.sheyi.testify.models.Category;
import com.sheyi.testify.orm.CategoryORM;

import java.util.ArrayList;
import java.util.List;

public class Application {

    public static TextView generateUICategoryTag(Activity activity, Category tag) {
        TextView tv = new TextView(activity);
        tv.setPadding(10, 1, 10, 1);
        tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
        tv.setBackgroundResource(R.drawable.tags_bg);
        tv.setTextColor(activity.getResources().getColor(R.color.white));
        tv.setText(tag.getName());
        tv.setTextSize(14);
        tv.setTypeface(null, Typeface.BOLD);

        return tv;
    }

    public static void saveCategoriesToDB(List<Category> categories) {
        List<CategoryORM> cats = new ArrayList<>();

        for(Category cat : categories) {
            cats.add(new CategoryORM(cat.getId(), cat.getName(), cat.getType(), cat.getSort()));
        }

        SugarRecord.saveInTx(cats);
    }

    public static List<CategoryORM> getCategories() {
        List<CategoryORM> cats;
        try {
            cats = CategoryORM.listAll(CategoryORM.class);
        } catch (Exception e) {
            cats = new ArrayList<>();
        }

        return cats;
    }
}
