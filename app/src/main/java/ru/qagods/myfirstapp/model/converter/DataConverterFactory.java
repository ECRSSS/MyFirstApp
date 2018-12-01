package ru.qagods.myfirstapp.model.converter;

import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import io.reactivex.annotations.Nullable;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import ru.qagods.myfirstapp.model.Data;

public class DataConverterFactory extends Converter.Factory {
    /**
     * Returns a {@link Converter} for converting an HTTP response body to {@code type}, or null if
     * {@code type} cannot be handled by this factory. This is used to create converters for
     * response types such as {@code SimpleResponse} from a {@code Call<SimpleResponse>}
     * declaration.
     *
     * @param type
     * @param annotations
     * @param retrofit
     */
    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        Type typeToEncode=TypeToken.getParameterized(Data.class,type).getType();
        final Converter<ResponseBody,Data> delegate = retrofit.nextResponseBodyConverter(this,typeToEncode,annotations);
        return body -> {
          Data<?> data = delegate.convert(body);
          return data.response;
        };
    }
}
