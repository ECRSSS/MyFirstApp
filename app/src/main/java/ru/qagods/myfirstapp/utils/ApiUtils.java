package ru.qagods.myfirstapp.utils;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.qagods.myfirstapp.Api.AcademyApi;
import ru.qagods.myfirstapp.BuildConfig;

public class ApiUtils {

    private static OkHttpClient okHttpClient;
    private static Retrofit retrofit;
    private static Gson gson;
    private static AcademyApi api;

    public static OkHttpClient getBasicAuthClient(final String email, final String password, boolean newInstance) {
        if (newInstance || okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            builder.authenticator(new Authenticator() {
                @Override
                public Request authenticate(Route route, Response response) throws IOException {
                    String credential = Credentials.basic(email, password);
                    Request request = response.request().newBuilder().header("Authorization", credential).build();
                    return request;

                }
            }).addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));

            okHttpClient = builder.build();
        }

        return okHttpClient;
    }

    public static Retrofit getRetrofit(final String email,final String password,boolean newInstance) {
        if(gson==null){
            gson=new Gson();
        }
        if (retrofit == null || newInstance == true) {
            retrofit = new Retrofit.Builder().baseUrl(BuildConfig.SERVER_URL)
                    //need for interceptors
                    .client(getBasicAuthClient(email, password, newInstance))
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public static AcademyApi getApi(final String email,final String password,boolean newInstance){
        if(api==null || newInstance==true){
            api=getRetrofit(email,password,newInstance).create(AcademyApi.class);
        }
        return api;
    }
}
