package ru.qagods.myfirstapp.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.qagods.myfirstapp.model.Album;
import ru.qagods.myfirstapp.model.Song;
import ru.qagods.myfirstapp.model.comment.Comment;

@Dao
public interface MusicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAlbums(List<Album> albums);

    @Query("SELECT * from album")
    List<Album> getAlbums();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSongs(List<Song> songs);

    @Query("SELECT * FROM song")
    List<Song> getSongs();

    @Query("SELECT * FROM song where albumkey=:albumkey")
    List<Song> getSongsById(int albumkey);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertComments(List<Comment> comments);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertComment(Comment comment);

    @Query("SELECT * FROM comment where albumId=:albumkey")
    List<Comment> getCommentsByAlbumId(int albumkey);

    @Delete
    void deleteAlbum(Album album);

    //удалить альбом по id
    @Query("DELETE FROM album where albumid = :albumId")
    void deleteAlbumById(int albumId);

}
