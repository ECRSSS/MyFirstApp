package ru.qagods.myfirstapp.utils;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.qagods.myfirstapp.Api.AcademyApi;
import ru.qagods.myfirstapp.BuildConfig;
import ru.qagods.myfirstapp.model.converter.DataConverterFactory;

public class ApiUtils {

    public static final List<Class<?>> NETWORK_EXCEPTIONS = Arrays.asList(
            UnknownHostException.class,
            SocketTimeoutException.class,
            ConnectException.class
    );

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
                    .addConverterFactory(new DataConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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
