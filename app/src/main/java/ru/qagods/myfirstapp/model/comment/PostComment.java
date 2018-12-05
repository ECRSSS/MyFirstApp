package ru.qagods.myfirstapp.model.comment;

import com.google.gson.annotations.SerializedName;

public class PostComment {
    @SerializedName("text")
    private String mText;
    @SerializedName("album_id")
    private int mAlbumId;

    public PostComment(String mText, int mAlbumId) {
        this.mText = mText;
        this.mAlbumId = mAlbumId;
    }

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public int getmAlbumId() {
        return mAlbumId;
    }

    public void setmAlbumId(int mAlbumId) {
        this.mAlbumId = mAlbumId;
    }
}
