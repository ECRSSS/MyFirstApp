package ru.qagods.myfirstapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@Entity
public class Album implements Serializable {
    /**
     * id : 0
     * name : string
     * songs : [{"id":0,"name":"string","duration":"string"}]
     * release_date : 2018-11-28T19:59:15.279Z
     */
    @PrimaryKey
    @ColumnInfo(name = "albumid")
    @SerializedName("id")
    private int mId;
    @ColumnInfo(name = "name")
    @SerializedName("name")
    private String mName;
    @ColumnInfo(name = "release")
    @SerializedName("release_date")
    private String mReleaseDate;
    @SerializedName("songs")
    @Ignore
    private List<Song> mSongs;

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

    public List<Song> getSongs() {
        return mSongs;
    }

    public void setSongs(List<Song> songs) {
        mSongs = songs;
    }

}
