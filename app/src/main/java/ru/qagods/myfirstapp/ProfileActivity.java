package ru.qagods.myfirstapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    public static String EMAIL_KEY = "EMAIL_KEY";
    public static String PASSWORD_KEY = "PASSWORD_KEY";

    private AppCompatImageView mProfileImage;
    private TextView mLogin;
    private TextView mPassword;


    private View.OnClickListener mOnPhotoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //todo
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_profile);
        mProfileImage = findViewById(R.id.profile_image);
        mLogin = findViewById(R.id.tvEmail);
        mPassword = findViewById(R.id.tvPassword);

        Bundle extras = getIntent().getExtras();
        String email = extras.getString(EMAIL_KEY);
        String password = extras.getString(PASSWORD_KEY);

        mLogin.setText(email);
        mPassword.setText(password);

        mProfileImage.setOnClickListener(mOnPhotoClickListener);
    }
}
