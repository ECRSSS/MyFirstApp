package ru.qagods.myfirstapp.comments;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.qagods.myfirstapp.R;
import ru.qagods.myfirstapp.model.comment.Comment;

public class CommentAdapter extends RecyclerView.Adapter<CommentHolder> {

    @NonNull
    private final List<Comment> mComments = new ArrayList<>();

    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_comment, parent, false);
        return new CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentHolder holder, int position) {
        Comment comment = mComments.get(position);
        holder.bind(comment);
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    public void addData(List<Comment> data, boolean isRefreshed) {
        if (isRefreshed) {
            mComments.clear();
        }
        mComments.addAll(data);
        notifyDataSetChanged();
    }
}
