package ru.qagods.myfirstapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Songs implements Serializable {

    @SerializedName("data")
    private List<DataBean> mData;

    public List<DataBean> getData() {
        return mData;
    }

    public void setData(List<DataBean> data) {
        mData = data;
    }

    public static class DataBean implements Serializable{
        /**
         * id : 0
         * name : string
         * duration : string
         */

        @SerializedName("id")
        private int mId;
        @SerializedName("name")
        private String mName;
        @SerializedName("duration")
        private String mDuration;

        public int getId() {
            return mId;
        }

        public void setId(int id) {
            mId = id;
        }

        public String getName() {
            return mName;
        }

        public void setName(String name) {
            mName = name;
        }

        public String getDuration() {
            return mDuration;
        }

        public void setDuration(String duration) {
            mDuration = duration;
        }
    }
}
