package ru.qagods.myfirstapp;

import android.os.Bundle;
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


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import ru.qagods.myfirstapp.model.User;
import ru.qagods.myfirstapp.utils.ApiUtils;

public class RegistrationFragment extends Fragment {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf8");

    private EditText mNewEmail;
    private EditText mNewName;
    private EditText mNewPassword;
    private EditText mRepeatPassword;
    private Button mRegistrationButton;

    private View.OnClickListener onClickRegisterListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isInputValid()) {
                User user = new User(mNewEmail.getText().toString(), mNewName.getText().toString()
                        , mNewPassword.getText().toString());
                ApiUtils.getApiRx("", "", false).registration(user.getData())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                                    showMessage("Успешная регистрация");
                                    getFragmentManager().popBackStack();
                                }, throwable -> showMessage("Ошибка запроса")
                        );
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
        View v = inflater.inflate(R.layout.fr_registration, container, false);
        mNewEmail = v.findViewById(R.id.etNewLogin);
        mNewName = v.findViewById(R.id.etNewName);
        mNewPassword = v.findViewById(R.id.etNewPassword);
        mRepeatPassword = v.findViewById(R.id.etConfirmPassword);
        mRegistrationButton = v.findViewById(R.id.btnRegisterUser);
        mRegistrationButton.setOnClickListener(onClickRegisterListener);

        return v;
    }


    private boolean isInputValid() {
        return isValidLogin() & isValidPassword() & isValidName();
    }

    private boolean isValidLogin() {
        String login = mNewEmail.getText().toString();
        if (Patterns.EMAIL_ADDRESS.matcher(login).matches() && !TextUtils.isEmpty(login))
            return true;
        else {
            mNewEmail.setError("Неверный email");
            return false;
        }
    }

    private boolean isValidPassword() {
        String password = mNewPassword.getText().toString();
        String confirmationPassword = mRepeatPassword.getText().toString();
        if (!TextUtils.isEmpty(password) && password.equals(confirmationPassword))
            return true;
        else {
            mNewPassword.setError("Пароль- минимум 8 символов, пароли должны совпадать");
            mRepeatPassword.setError("Пароль- минимум 8 символов, пароли должны совпадать");
            return false;
        }
    }

    private boolean isValidName() {
        boolean isNotEmpty = !TextUtils.isEmpty(mNewName.getText().toString());
        if (isNotEmpty) {
            return true;
        } else {
            mNewName.setError("Имя не должно быть пустым");
            return false;
        }
    }

    private void showMessage(@StringRes int string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();
    }

    private void showMessage(String string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();
    }

}
