package com.sheyi.testify.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sheyi.testify.R;
import com.sheyi.testify.activity.NewPostActivity;
import com.sheyi.testify.orm.CategoryORM;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private List<CategoryORM> data = new ArrayList<>();
    private NewPostActivity activity;
    private List<CategoryORM> checkedList = new ArrayList<CategoryORM>();

    public CategoryAdapter(NewPostActivity activity, List<CategoryORM> data) {
        this.activity = activity;
        this.data = data;
    }

    public List<CategoryORM> getCheckedList() {
        return checkedList;
    }

    public void setCheckedList(List<CategoryORM> checked) {
        this.checkedList = checked;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CategoryORM current = data.get(position);
        holder.category.setText(current.getName());
        if (myIndexOf(checkedList, current) >= 0) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public int myIndexOf(List<CategoryORM> list, CategoryORM categoryORM) {
        int check = -1;
        int counter = 0;
        for (CategoryORM c : list) {
            if (c.getApiID() == categoryORM.getApiID()) {
                check = counter;
                break;
            }

            counter++;
        }

        return check;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView category;
        CheckBox checkBox;

        public MyViewHolder(View itemView) {
            super(itemView);
            category = (TextView) itemView.findViewById(R.id.categoryName);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);

            View.OnClickListener listener = new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int find = myIndexOf(checkedList, data.get(getAdapterPosition()));

                    if (find >= 0) {
                        checkedList.remove(find);
                    } else {
                        checkedList.add(data.get(getAdapterPosition()));
                    }

                    checkBox.setChecked(!(find >= 0));
                }
            };

            checkBox.setOnClickListener(listener);
            itemView.setOnClickListener(listener);
        }
    }
}