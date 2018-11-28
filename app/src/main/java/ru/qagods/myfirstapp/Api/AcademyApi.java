package ru.qagods.myfirstapp.Api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import ru.qagods.myfirstapp.model.Album;
import ru.qagods.myfirstapp.model.Albums;
import ru.qagods.myfirstapp.model.Song;
import ru.qagods.myfirstapp.model.Songs;
import ru.qagods.myfirstapp.model.User;

public interface AcademyApi {

    @POST("registration")
    Call<Void> registration(@Body User user);

    @GET("albums")
    Call<Albums> getAlbums();

    @GET("albums/{id}")
    Call<Album> getAlbum(@Path("id") int id);

    @GET("songs")
    Call<Songs> getSongs();

    @GET("songs/{id}")
    Call<Song> getSong(@Path("id") int id);


}
