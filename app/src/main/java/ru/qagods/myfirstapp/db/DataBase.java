package ru.qagods.myfirstapp.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;


import ru.qagods.myfirstapp.model.Album;
import ru.qagods.myfirstapp.model.Song;
import ru.qagods.myfirstapp.model.comment.Comment;

@Database(entities = {Album.class,Song.class,Comment.class}, version = 1)
public abstract class DataBase extends RoomDatabase {
    public abstract MusicDao getMusicDao();
}
