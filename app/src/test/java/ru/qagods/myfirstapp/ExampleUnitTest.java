package ru.qagods.myfirstapp;

import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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