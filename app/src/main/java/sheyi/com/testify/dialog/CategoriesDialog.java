package sheyi.com.testify.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sheyi.com.testify.R;
import sheyi.com.testify.adapter.CategoryAdapter;
import sheyi.com.testify.callback.CategoryCallback;
import sheyi.com.testify.models.Category;
import sheyi.com.testify.rest.ApiClient;
import sheyi.com.testify.rest.ApiInterface;

public class CategoriesDialog extends DialogFragment {
    private CategoryCallback callback;
    private List<Category> data;
    private RecyclerView recyclerView;
    private AlertDialog.Builder builder;
    private ProgressBar progressBar;
    private Dialog dialog;
    private CategoryAdapter categoryAdapter;

    public void setCallback(CategoryCallback callback) {
        this.callback = callback;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_category, null);

        initializeComponent(view);
        return dialog;
    }

    private void initializeComponent(View view) {
        builder.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        callback.onCategorySet(categoryAdapter.getCheckedList());
                    }
                });

        dialog = builder.create();
        recyclerView = (RecyclerView) view.findViewById(R.id.categoryList);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        getData();
    }

    private void getData() {
        ApiInterface apiService = ApiClient.getApi(this.getActivity());
        Call<List<Category>> call = apiService.getCategories();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                data = response.body();
                progressBar.setVisibility(View.GONE);
                setUpDialog();
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });
    }

    private void setUpDialog() {
        categoryAdapter = new CategoryAdapter(getContext(), data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(categoryAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        dismiss();
    }
}
