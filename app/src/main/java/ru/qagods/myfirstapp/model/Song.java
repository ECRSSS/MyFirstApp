package ru.qagods.myfirstapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Song implements Serializable {

    /**
     * data : {"id":0,"name":"string","duration":"string"}
     */

    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "duration")
    private String duration;
    @ColumnInfo(name = "albumkey")
    private int albumkey;

    public int getAlbumkey() {
        return albumkey;
    }

    public void setAlbumkey(int albumkey) {
        this.albumkey = albumkey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
