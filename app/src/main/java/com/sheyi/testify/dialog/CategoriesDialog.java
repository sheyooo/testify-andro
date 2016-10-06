package com.sheyi.testify.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.sheyi.testify.R;
import com.sheyi.testify.activity.NewPostActivity;
import com.sheyi.testify.adapter.CategoryAdapter;
import com.sheyi.testify.callback.CategoryCallback;
import com.sheyi.testify.orm.CategoryORM;
import com.sheyi.testify.util.Application;

import java.util.ArrayList;
import java.util.List;

public class CategoriesDialog extends DialogFragment {
    private CategoryCallback callback;
    private List<CategoryORM> data;
    private RecyclerView recyclerView;
    private AlertDialog.Builder builder;
    private Dialog dialog;
    private CategoryAdapter categoryAdapter;
    private NewPostActivity activity;

    private List<CategoryORM> currentlyCheckedCats = new ArrayList<>();

    public void setActivityField(NewPostActivity activity) {
        this.activity = activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_category, null);

        initializeComponent(view);
        return dialog;
    }

    public void setCallback(CategoryCallback callback) {
        this.callback = callback;
    }

    public void setCurrentlyChecked(List<CategoryORM> list) {
        this.currentlyCheckedCats = list;
    }

    private void initializeComponent(View view) {
        builder.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (categoryAdapter.getCheckedList() != null) {
                            callback.onCategorySet(categoryAdapter.getCheckedList());
                        }
                    }
                });

        dialog = builder.create();
        recyclerView = (RecyclerView) view.findViewById(R.id.categoryList);

        List<CategoryORM> cats = Application.getCategories();
        data = cats;
        setUpDialog();
    }

    private void setUpDialog() {
        categoryAdapter = new CategoryAdapter(activity, data);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        categoryAdapter.setCheckedList(currentlyCheckedCats);
        recyclerView.setAdapter(categoryAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        dismiss();
    }
}
