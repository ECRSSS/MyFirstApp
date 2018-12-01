package ru.qagods.myfirstapp.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;


import ru.qagods.myfirstapp.model.Album;

@Database(entities = {Album.class}, version = 1)
public abstract class DataBase extends RoomDatabase {
    public abstract MusicDao getMusicDao();
}
