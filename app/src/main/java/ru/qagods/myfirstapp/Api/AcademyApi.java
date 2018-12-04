package ru.qagods.myfirstapp.Api;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import ru.qagods.myfirstapp.model.Album;
import ru.qagods.myfirstapp.model.Comment;
import ru.qagods.myfirstapp.model.Song;
import ru.qagods.myfirstapp.model.User;

public interface AcademyApi {

    @POST("registration")
    Completable registration(@Body User.DataBean userData);

    @GET("albums")
    Single<List<Album>> getAlbums();

    @GET("albums/{id}")
    Single<Album> getAlbum(@Path("id") int id);

    @GET("songs")
    Call<List<Song>> getSongs();

    @GET("songs/{id}")
    Call<Song> getSong(@Path("id") int id);

    @GET("user")
    Single<User> getUser();

    @GET("comments")
    Single<List<Comment>> getComments();


}
