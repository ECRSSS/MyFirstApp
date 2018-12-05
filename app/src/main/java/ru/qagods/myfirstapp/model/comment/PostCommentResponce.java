package ru.qagods.myfirstapp.model.comment;

import com.google.gson.annotations.SerializedName;

public class PostCommentResponce {

    @SerializedName("id")
    private int mId;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }
}
