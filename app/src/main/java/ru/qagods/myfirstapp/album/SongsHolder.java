package ru.qagods.myfirstapp.album;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import ru.qagods.myfirstapp.R;
import ru.qagods.myfirstapp.model.Song;

public class SongsHolder extends RecyclerView.ViewHolder {

    private TextView mId;
    private TextView mTitle;
    private TextView mDuration;

    public SongsHolder(View itemView) {
        super(itemView);
        mId = itemView.findViewById(R.id.tv_song_id);
        mTitle = itemView.findViewById(R.id.tv_title);
        mDuration = itemView.findViewById(R.id.tv_duration);
    }

    public void bind(Song item) {
        mId.setText(String.valueOf(item.getId()));
        mTitle.setText(item.getName());
        mDuration.setText(item.getDuration());

    }
}
