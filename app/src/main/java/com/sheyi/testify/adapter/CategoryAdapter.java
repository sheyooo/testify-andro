package com.sheyi.testify.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sheyi.testify.R;
import com.sheyi.testify.models.Category;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    List<Category> data = Collections.emptyList();
    private Context context;
    List<Category> checkedList = new ArrayList<>();

    public CategoryAdapter(Context context, List<Category> data) {
        this.context = context;
        this.data = data;
    }

    public List<Category> getCheckedList() {
        return checkedList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Category current = data.get(position);
        holder.category.setText(current.getName());
        if (checkedList.contains(current)) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView category;
        CheckBox checkBox;

        public MyViewHolder(View itemView) {
            super(itemView);
            category = (TextView) itemView.findViewById(R.id.categoryName);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
            itemView.setOnClickListener(new View.OnClickListener() {
                boolean toggle = true;

                @Override
                public void onClick(View v) {
                    if (toggle) {
                        checkBox.setChecked(toggle);
                        checkedList.add(data.get(getAdapterPosition()));
                        toggle = false;
                    } else {
                        checkBox.setChecked(toggle);
                        checkedList.remove(data.get(getAdapterPosition()));
                        toggle = true;
                    }
                }
            });
        }
    }
}