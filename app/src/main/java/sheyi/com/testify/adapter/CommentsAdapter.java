package sheyi.com.testify.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.curioustechizen.ago.RelativeTimeTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sheyi.com.testify.R;
import sheyi.com.testify.models.Comment;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {
    private List<Comment> dataSet = new ArrayList<>();
    private Activity activity;

    public CommentsAdapter(Activity activity, List<Comment> data) {
        this.activity = activity;
        this.dataSet = data;
    }

    public void setDataSet(List<Comment> data) {
        this.dataSet = data;
        this.notifyDataSetChanged();
    }

    @Override
    public CommentsAdapter.CommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.comment_view, parent, false);
        return new CommentsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CommentsViewHolder holder, int position) {
        Comment c = dataSet.get(position);

        try {
            Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(c.getCreatedAt());
            holder.timeTextView.setReferenceTime(d.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.nameTV.setText(c.getUser().getName());
        holder.commentTextView.setText(c.getText());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class CommentsViewHolder extends RecyclerView.ViewHolder {
        TextView nameTV, commentTextView;

        RelativeTimeTextView timeTextView;

        public CommentsViewHolder(View itemView) {
            super(itemView);

            nameTV = (TextView) itemView.findViewById(R.id.nameTextView);
            commentTextView = (TextView) itemView.findViewById(R.id.commentTextView);
            timeTextView = (RelativeTimeTextView) itemView.findViewById(R.id.timeTextView);
        }
    }
}
