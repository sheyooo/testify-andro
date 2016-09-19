package sheyi.com.testify.adapter;


import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.squareup.picasso.Picasso;
import com.wefika.flowlayout.FlowLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import sheyi.com.testify.R;
import sheyi.com.testify.models.Category;
import sheyi.com.testify.models.Post;


public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostViewHolder>{

    private ArrayList<Post> posts;
    private Activity activity;

    public PostsAdapter(Activity activity, ArrayList<Post> data) {
        this.posts = data;
        this.activity = activity;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_view, parent, false);
        PostViewHolder pvh = new PostViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        Post p = posts.get(position);

        holder.contentTextView.setText(p.getText());

        try {
            Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(p.getCreatedAt());
            holder.timeTextView.setReferenceTime(d.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.amenCountTView.setText(p.getAmensCount().toString());
        holder.tapCountTView.setText(p.getTapsCount().toString());
        holder.commentCountTView.setText(p.getCommentsCount().toString());

        if(p.getUser() != null) {
            holder.nameTextView.setText(p.getUser().getName());

            Picasso.with(activity)
                    .load(p.getUser().getAvatar())
                    .placeholder(R.drawable.testify_logo)
                    .error(R.drawable.ic_image)
                    .resize(100, 100)
                    .centerCrop()
                    .into(holder.profileImageView);
        } else {
            holder.nameTextView.setText("Anonymous");

            Picasso.with(activity)
                    .load(R.drawable.testify_logo)
                    .resize(100, 100)
                    .centerCrop()
                    .into(holder.profileImageView);
        }

        FlowLayout.LayoutParams layout = new FlowLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        layout.setMargins(0, 0, 5, 5);

        holder.tagsFlowLayout.removeAllViews();

        for (Category tag : posts.get(position).getCategories()) {
            TextView tv = this.generateUITag(tag);
            tv.setLayoutParams(layout);
            holder.tagsFlowLayout.addView(tv);
        }

        if (posts.get(position).getAnonymous()) {
            Category cat = new Category();
            cat.setName("Anonymous");

            TextView tv = this.generateUITag(cat);
            tv.setLayoutParams(layout);
            holder.tagsFlowLayout.addView(tv);
        }

    }

    private TextView generateUITag(Category tag) {
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

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        FlowLayout tagsFlowLayout;
        ImageView profileImageView;
        TextView amenCountTView,
                tapCountTView,
                commentCountTView,
                nameTextView,
                contentTextView;

        RelativeTimeTextView timeTextView;

        PostViewHolder(View itemView) {
            super(itemView);

            profileImageView = (ImageView) itemView.findViewById(R.id.userProfileImageView);
            tagsFlowLayout = (FlowLayout) itemView.findViewById(R.id.tagsFlowLayout);

            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            contentTextView = (TextView) itemView.findViewById(R.id.contentTextView);
            timeTextView = (RelativeTimeTextView) itemView.findViewById(R.id.timeTextView);
            amenCountTView = (TextView) itemView.findViewById(R.id.amenCountTView);
            tapCountTView = (TextView) itemView.findViewById(R.id.tapCountTView);
            commentCountTView = (TextView) itemView.findViewById(R.id.commentCountTView);
        }
    }

}