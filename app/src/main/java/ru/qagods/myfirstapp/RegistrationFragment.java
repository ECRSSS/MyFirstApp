package ru.qagods.myfirstapp;

import android.os.Bundle;
import android.os.PatternMatcher;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationFragment extends Fragment {

    private EditText mNewLogin;
    private EditText mNewPassword;
    private EditText mRepeatPassword;
    private Button mRegistrationButton;

    private View.OnClickListener onClickRegisterListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(isInputValid()){

            }
        }
    };

    public static RegistrationFragment newInstance() {

        Bundle args = new Bundle();

        RegistrationFragment fragment = new RegistrationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fr_registration,container);
        mNewLogin=v.findViewById(R.id.etNewLogin);
        mNewPassword=v.findViewById(R.id.etNewPassword);
        mRepeatPassword=v.findViewById(R.id.etConfirmPassword);
        mRegistrationButton=v.findViewById(R.id.buttonRegister);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private boolean isInputValid(){
        return isValidLogin() && isValidPassword();
    }

    private boolean isValidLogin(){
        String login=mNewLogin.getText().toString();
        if(Patterns.EMAIL_ADDRESS.matcher(login).matches() && !TextUtils.isEmpty(login))
            return true;
        else
            return false;
    }

    private boolean isValidPassword(){
        String password=mNewPassword.getText().toString();
        String confirmationPassword=mRepeatPassword.getText().toString();
        if(!TextUtils.isEmpty(password) && password.equals(confirmationPassword))
            return true;
        else
            return false;
    }

    private void showMessage(@StringRes int string){
        Toast.makeText(getActivity(),string,Toast.LENGTH_SHORT).show();
    }
}