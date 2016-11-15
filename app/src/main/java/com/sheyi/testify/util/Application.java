package com.sheyi.testify.util;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.sheyi.testify.R;
import com.sheyi.testify.models.Category;
import com.sheyi.testify.orm.CategoryORM;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

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

        Realm realm = Realm.getDefaultInstance();


        for(Category cat : categories) {
            cats.add(new CategoryORM(cat.getId(), cat.getName(), cat.getType(), cat.getSort()));
            //CategoryORM catORM = new CategoryORM(cat.getId(), cat.getName(), cat.getType(), cat.getSort());
//            CategoryORM catORM = realm.createObject(CategoryORM.class);
//            catORM.setApiID(cat.getId());
//            catORM.setName(cat.getName());
//            catORM.setType(cat.getType());
//            catORM.setSort(cat.getSort());
        }

        realm.beginTransaction();
        realm.copyToRealm(cats);
        realm.commitTransaction();
    }

    public static List<CategoryORM> getCategories() {
        Realm realm = Realm.getDefaultInstance();

        List<CategoryORM> cats;
        try {
            RealmQuery query = realm.where(CategoryORM.class);

            RealmResults l = query.findAll();
            //cats = CategoryORM.(CategoryORM.class);
            cats = l;
        } catch (Exception e) {
            cats = new ArrayList<>();
        }

        return cats;
    }
}
