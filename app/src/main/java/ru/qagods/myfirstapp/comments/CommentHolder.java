package ru.qagods.myfirstapp.comments;

import android.icu.util.Calendar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import ru.qagods.myfirstapp.R;
import ru.qagods.myfirstapp.model.comment.Comment;

public class CommentHolder extends RecyclerView.ViewHolder {

    private TextView mTimeComment;
    private TextView mAuthor;
    private TextView mText;

    public CommentHolder(View itemView) {
        super(itemView);
        mTimeComment = itemView.findViewById(R.id.tv_commenttime_id);
        mAuthor = itemView.findViewById(R.id.tv_commentauthor_id);
        mText = itemView.findViewById(R.id.tv_commenttext_id);
    }

    public void bind(Comment item) {
        mTimeComment.setText(timeStampHandle(item.getTimestamp()));
        mAuthor.setText("Автор: "+item.getAuthor());
        mText.setText(item.getText());
    }

    private String timeStampHandle(String time){
        String timestamp=time;
        timestamp=timestamp.replace("+00:00","");
        timestamp=timestamp.replace("T"," ");
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-M-d H:m:s");
        try {
            cal.setTime(format.parse(timestamp));
        } catch (ParseException e) {
            throw new RuntimeException("Ошибка в парсинге даты");
        }
        if(System.currentTimeMillis()-cal.getTimeInMillis()<86400000){
            return time.substring(11,19);
        }else{
            return time.substring(0,10);
        }
    }
}
