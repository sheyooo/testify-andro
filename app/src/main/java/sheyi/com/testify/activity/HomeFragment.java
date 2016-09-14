package sheyi.com.testify.activity;

/**
 * Created by andela on 08/09/2016.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sheyi.com.testify.R;
import sheyi.com.testify.adapter.PostsAdapter;
import sheyi.com.testify.models.Post;
import sheyi.com.testify.rest.ApiClient;
import sheyi.com.testify.rest.ApiInterface;


public class HomeFragment extends Fragment {
    private RecyclerView recycler;
    private PostsAdapter adapter;

    private ProgressBar loaderBar;

    public HomeFragment() {
        // Required empty public constructor
    }

    public void loadPosts() {
        loaderBar.setVisibility(View.VISIBLE);

        recycler = (RecyclerView) getView().findViewById(R.id.post_recycler_view);

        ArrayList<Post> posts = new ArrayList<>();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<ArrayList<Post>> call = apiService.getPosts();
        call.enqueue(new Callback<ArrayList<Post>>() {
            @Override
            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                ArrayList<Post> post = response.body();
                adapter = new PostsAdapter(getActivity(), post);

                recycler.setAdapter(adapter);
                loaderBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                loaderBar.setVisibility(View.GONE);

                Toast.makeText(getActivity(), "Couldn't not connect to internet", Toast.LENGTH_SHORT).show();
            }
        });

        adapter = new PostsAdapter(getActivity(), posts);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setAdapter(adapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        loaderBar = (ProgressBar) getView().findViewById(R.id.homeSpinner);

        loadPosts();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}