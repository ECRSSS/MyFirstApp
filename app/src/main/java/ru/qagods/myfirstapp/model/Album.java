package ru.qagods.myfirstapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Album implements Serializable {
    /**
     * data : {"id":0,"name":"string","songs":[{"id":0,"name":"string","duration":"string"}],"release_date":"2018-11-28T19:59:15.279Z"}
     */

    @SerializedName("data")
    private DataBean mData;

    public DataBean getData() {
        return mData;
    }

    public void setData(DataBean data) {
        mData = data;
    }

    public static class DataBean implements Serializable {
        /**
         * id : 0
         * name : string
         * songs : [{"id":0,"name":"string","duration":"string"}]
         * release_date : 2018-11-28T19:59:15.279Z
         */

        @SerializedName("id")
        private int mId;
        @SerializedName("name")
        private String mName;
        @SerializedName("release_date")
        private String mReleaseDate;
        @SerializedName("songs")
        private List<Song.DataBean> mSongs;

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

        public String getReleaseDate() {
            return mReleaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            mReleaseDate = releaseDate;
        }

        public List<Song.DataBean> getSongs() {
            return mSongs;
        }

        public void setSongs(List<Song.DataBean> songs) {
            mSongs = songs;
        }

    }
}
