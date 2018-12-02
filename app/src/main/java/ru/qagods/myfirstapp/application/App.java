package ru.qagods.myfirstapp.application;

import android.app.Application;
import android.arch.persistence.room.Room;

import ru.qagods.myfirstapp.db.DataBase;
import ru.qagods.myfirstapp.db.MusicDao;


public class App extends Application {

    private DataBase mDatabase;

    @Override
    public void onCreate() {
        super.onCreate();

        mDatabase = Room.databaseBuilder(getApplicationContext(), DataBase.class, "music_database")
                .fallbackToDestructiveMigration()
                .build();
    }

    public DataBase getDatabase() {
        return mDatabase;
    }

    public MusicDao getMusicDao(){
        return getDatabase().getMusicDao();
    }
}
