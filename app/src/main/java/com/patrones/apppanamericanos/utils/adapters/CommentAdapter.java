package com.patrones.apppanamericanos.utils.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.patrones.apppanamericanos.R;
import com.patrones.apppanamericanos.models.entities.Comment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private List<Comment> comments;
    private Context context;

    public CommentAdapter(List<Comment> comments, Context context) {
        this.comments = comments;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.event_activity_content_comment, parent, false);
        return new CommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
        holder.Bind(comments.get(position));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.title_event_comment_tv)
        TextView title;
        @BindView(R.id.author_event_comment_tv)
        TextView author;
        @BindView(R.id.description_event_comment_tv)
        TextView description;

        View mView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            ButterKnife.bind(this, mView);
        }

        public void Bind(final Comment comment){
            if(comment != null){
                title.setText(comment.getTitle());
                author.setText("- "+comment.getUserName());
                description.setText("\""+comment.getDescription()+"\"");
            }
        }
    }
}
