package ru.qagods.myfirstapp.albums;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

import ru.qagods.myfirstapp.R;
import ru.qagods.myfirstapp.model.Albums;


public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsHolder> {

    @NonNull
    private final List<Albums.DataBean> mAlbums = new ArrayList<>();
    private final OnItemClickListener mOnClickListener;

    public AlbumsAdapter(OnItemClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @Override
    public AlbumsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_album, parent, false);
        return new AlbumsHolder(view);
    }

    @Override
    public void onBindViewHolder(AlbumsHolder holder, int position) {
        Albums.DataBean album = mAlbums.get(position);
        holder.bind(album, mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return mAlbums.size();
    }

    public void addData(List<Albums.DataBean> data, boolean isRefreshed) {
        if (isRefreshed) {
            mAlbums.clear();
        }

        mAlbums.addAll(data);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Albums.DataBean album);
    }
}
