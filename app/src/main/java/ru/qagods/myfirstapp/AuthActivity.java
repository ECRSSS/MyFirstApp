package ru.qagods.myfirstapp;

import android.content.Intent;
import android.os.PatternMatcher;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ru.qagods.myfirstapp.model.User;

public class AuthActivity extends AppCompatActivity {

    private EditText mLoginField;
    private EditText mPasswordField;
    private Button mEnterButton;
    private Button mRegisterButton;

    private View.OnClickListener mOnClickEnterButtonListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(isEmailValid() && isPasswordValid()){
                Intent startProfileActivityIntent=new Intent(AuthActivity.this,ProfileActivity.class);
                User user=new User(mLoginField.getText().toString(),mPasswordField.getText().toString());
                startProfileActivityIntent.putExtra(ProfileActivity.USER_KEY,user);
                startActivity(startProfileActivityIntent);
            }else{
                showMessage(R.string.accessDeniedToastText);
            }
        }
    };
    private View.OnClickListener mOnClickRegisterButtonListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //todo Обработать нажатие
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_auth);
        mLoginField=findViewById(R.id.login);
        mPasswordField=findViewById(R.id.password);
        mEnterButton=findViewById(R.id.buttonEnter);
        mRegisterButton=findViewById(R.id.buttonRegister);

        mEnterButton.setOnClickListener(mOnClickEnterButtonListener);
        mRegisterButton.setOnClickListener(mOnClickRegisterButtonListener);
    }

    private boolean isEmailValid(){
        String text=mLoginField.getText().toString();
        if(!TextUtils.isEmpty(text)&& Patterns.EMAIL_ADDRESS.matcher(text).matches())
            return true;
        else
            return false;
    }

    private boolean isPasswordValid(){
        String text=mLoginField.getText().toString();
        if(!TextUtils.isEmpty(text))
            return true;
        else
            return false;
    }

    private void showMessage(@StringRes int string){
        Toast.makeText(this,getString(string),Toast.LENGTH_SHORT).show();
    }
}
