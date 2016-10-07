package com.sheyi.testify.activity;

import android.app.ProgressDialog;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.sheyi.testify.R;
import com.sheyi.testify.callback.CategoryCallback;
import com.sheyi.testify.dialog.CategoriesDialog;
import com.sheyi.testify.models.Category;
import com.sheyi.testify.models.Post;
import com.sheyi.testify.models.sendables.PostPayload;
import com.sheyi.testify.orm.CategoryORM;
import com.sheyi.testify.rest.ApiClient;
import com.sheyi.testify.rest.ApiInterface;
import com.sheyi.testify.util.Application;
import com.wefika.flowlayout.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sheyi.testify.util.Application.getCategories;

public class NewPostActivity extends AppCompatActivity implements CategoryCallback {
    private LinearLayout etHolderLL;
    private EditText postEditText;
    private Switch anonSwitch;
    private ImageView anonymityIcon;
    private ProgressDialog catProgressDialog;
    private FlowLayout categoriesView;

    private FlowLayout.LayoutParams layout;

    private ColorStateList textColor, hintColor;

    private List<Category> setCategories;
    private List<CategoryORM> checkedCategoriesORM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        setSupportActionBar((Toolbar) findViewById(R.id.new_post_toolbar));
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("");

        anonSwitch = (Switch) findViewById(R.id.anonymousSwitch);
        etHolderLL = (LinearLayout) findViewById(R.id.editTextHolderLayout);
        postEditText = (EditText) findViewById(R.id.postEditText);
        anonymityIcon = (ImageView) findViewById(R.id.anonymityIcon);
        categoriesView = (FlowLayout) findViewById(R.id.categoriesView);

        textColor = postEditText.getTextColors();
        hintColor = postEditText.getHintTextColors();

        setCategories = new ArrayList<>();
        checkedCategoriesORM = new ArrayList<>();

        anonSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switchOnClick(buttonView);
            }
        });

        layout = new FlowLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layout.setMargins(0, 0, 5, 5);

        populateCategories();
        setGeneralDefault();
    }

    private void populateCategories() {
        if (getCategories().size() < 1) {
            catProgressDialog = ProgressDialog.show(this, "First time loading categories", "Loading categories...", true);
            downloadCategories();
        }
    }

    public void downloadCategories() {
        ApiInterface apiService = ApiClient.getApi(this);
        Call<List<Category>> call = apiService.getCategories();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                Application.saveCategoriesToDB(response.body());
                catProgressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                catProgressDialog.dismiss();
                NewPostActivity.this.finish();
            }
        });
    }

    public void switchOnClick(View v) {
        if (((Switch) v).isChecked()) {
            etHolderLL.setBackgroundColor(getResources().getColor(R.color.darkAsh));
            postEditText.setTextColor(getResources().getColor(R.color.white));
            postEditText.setHintTextColor(getResources().getColor(R.color.lightAsh));
            anonymityIcon.setVisibility(View.VISIBLE);
        } else {
            etHolderLL.setBackgroundColor(getResources().getColor(R.color.white));
            postEditText.setTextColor(textColor);
            postEditText.setHintTextColor(hintColor);
            anonymityIcon.setVisibility(View.INVISIBLE);
        }
    }

    public void showCat(View v) {
        CategoriesDialog cd = new CategoriesDialog();
        cd.setActivityField(this);
        cd.setCallback(this);
        cd.setCurrentlyChecked(checkedCategoriesORM);

        cd.show(getSupportFragmentManager(), "categories fragment");
    }

    public void sendPost(View v) {
        PostPayload pay = new PostPayload();

        if (!isPostValid()) {
            return;
        }

        int anon = anonSwitch.isChecked() ? 1 : 0;

        pay.setAnonymous(anon);
        pay.setCategories(getIDs(checkedCategoriesORM));
        pay.setImages(new ArrayList<>());
        pay.setPost(postEditText.getText().toString());

        ApiInterface api = ApiClient.getApi(this);

        Call<Post> call = api.sendPost(pay);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Toast.makeText(NewPostActivity.this, "It went", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(NewPostActivity.this, "Didnt make it", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean isPostValid() {
        boolean isTextValid = postEditText.getText().toString().length() > 5;
        boolean isImageValid = false;

        return isTextValid || isImageValid;
    }

    public List<Integer> getIDs(List<CategoryORM> cats) {
        List<Integer> ids = new ArrayList<>();

        for (CategoryORM cat : cats) {
            ids.add(cat.getApiID());
        }

        return ids;
    }

    public void setGeneralDefault() {
        List<CategoryORM> cats = Application.getCategories();

        for (CategoryORM cat : cats) {
            if (cat.getName().equals("General")) {
                Category tag = new Category(cat.getApiID(), cat.getName(), cat.getType(), cat.getSort());

                TextView tv = Application.generateUICategoryTag(this, tag);
                tv.setLayoutParams(layout);

                // Add to flow layout view
                categoriesView.addView(tv);

                // Add to array
                setCategories.add(tag);
                checkedCategoriesORM.add(cat);
            }
        }
    }

    @Override
    public void onCategorySet(List<CategoryORM> selectedCategories) {

        this.checkedCategoriesORM = selectedCategories;

        categoriesView.removeAllViews();
        setCategories.removeAll(setCategories);

        // Generate the blue categories label for the post
        for (CategoryORM cat : selectedCategories) {
            Category tag = new Category(cat.getApiID(), cat.getName(), cat.getType(), cat.getSort());

            TextView tv = Application.generateUICategoryTag(this, tag);
            tv.setLayoutParams(layout);

            // Add to flow layout view
            categoriesView.addView(tv);

            // Add to array
            setCategories.add(tag);
        }


        // If no tag is selected!!!
        if (selectedCategories.size() < 1) {
            Category tag = new Category(0, "No category", "", 0);

            TextView tv = Application.generateUICategoryTag(this, tag);
            tv.setLayoutParams(layout);

            // Add to flow layout view
            categoriesView.addView(tv);
        }
    }

}
