package ru.qagods.myfirstapp;

import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.qagods.myfirstapp.model.Comment;
import ru.qagods.myfirstapp.utils.ApiUtils;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws IOException, ParseException {
        String timestamp="2018-12-04T15:12:56+00:00";
        timestamp=timestamp.replace("+00:00","");
        timestamp=timestamp.replace("T"," ");

        SimpleDateFormat format=new SimpleDateFormat("yyyy-M-d H:m:s");
        Date date = format.parse(timestamp);
        System.out.println(date.toString());
    }
}