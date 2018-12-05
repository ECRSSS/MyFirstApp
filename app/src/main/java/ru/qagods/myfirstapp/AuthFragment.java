package ru.qagods.myfirstapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ru.qagods.myfirstapp.albums.AlbumsActivity;
import ru.qagods.myfirstapp.model.User;
import ru.qagods.myfirstapp.utils.ApiUtils;

public class AuthFragment extends Fragment {

    private AutoCompleteTextView mLoginField;
    private EditText mPasswordField;
    private Button mEnterButton;
    private Button mRegisterButton;


    public static AuthFragment newInstance() {

        Bundle args = new Bundle();

        AuthFragment fragment = new AuthFragment();
        fragment.setArguments(args);
        return fragment;
    }


    private View.OnClickListener mOnClickEnterButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            User user = null;


            ApiUtils.getApiRx(mLoginField.getText().toString(), mPasswordField.getText().toString(), true)
                    .getUser().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableSingleObserver<User>() {
                        @Override
                        public void onSuccess(User user) {
                            startActivity(new Intent(getActivity(), AlbumsActivity.class));
                            getActivity().finish();
                        }

                        @Override
                        public void onError(Throwable e) {
                            mLoginField.setError("Неверный email");
                            mPasswordField.setError("Или пароль");
                            showMessage("Ошибка валидации");
                        }
                    });
        }
    };


//
//                    .enqueue(new retrofit2.Callback<User>() {
//                Handler handler = new Handler(getActivity().getMainLooper());
//
//                @Override
//                public void onResponse(retrofit2.Call<User> call, final retrofit2.Response<User> response) {
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (response.isSuccessful()) {
//                                User user = null;
//                                user = response.body();
//                                Intent startProfileActivityIntent = new Intent(getActivity(), ProfileActivity.class);
//                                startProfileActivityIntent.putExtra(ProfileActivity.USER_KEY, user);
////                                startActivity(startProfileActivityIntent);
//                                startActivity(new Intent(getActivity(), AlbumsActivity.class));
//                                getActivity().finish();
//
//                            } else {
//                                mLoginField.setError("Неверный email");
//                                mPasswordField.setError("Или пароль");
//                                switch (response.code()) {
//                                    case 401:
//                                        showMessage("Не авторизован");
//                                        break;
//                                    case 500:
//                                        showMessage("Внутренняя ошибка сервера");
//                                        break;
//                                    default:
////                                        showMessage("Ошибка валидации");
////                                        break;
////                                }
////                            }
//                        }
//                    });
//                }

//    @Override
//    public void onFailure(retrofit2.Call<User> call, final Throwable t) {
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                mLoginField.setError("Неверный email");
//                mPasswordField.setError("Или пароль");
//                showMessage("Ошибка валидации");
//            }
//        });
//    }
//});
//        }
//        };

private View.OnClickListener mOnClickRegisterButtonListener=new View.OnClickListener(){
@Override
public void onClick(View v){
        getFragmentManager().beginTransaction()
        .replace(R.id.fragment_container,RegistrationFragment.newInstance())
        .addToBackStack(RegistrationFragment.class.getName())
        .commit();
        }
        };


@Nullable
@Override
public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState){
        View v=inflater.inflate(R.layout.fr_auth,container,false);

        mLoginField=v.findViewById(R.id.login);

        mPasswordField=v.findViewById(R.id.password);
        mEnterButton=v.findViewById(R.id.buttonEnter);
        mRegisterButton=v.findViewById(R.id.buttonRegister);

        mEnterButton.setOnClickListener(mOnClickEnterButtonListener);
        mRegisterButton.setOnClickListener(mOnClickRegisterButtonListener);

        return v;
        }

private boolean isEmailValid(){
        String text=mLoginField.getText().toString();
        if(!TextUtils.isEmpty(text)&&Patterns.EMAIL_ADDRESS.matcher(text).matches())
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
        Toast.makeText(getActivity(),getString(string),Toast.LENGTH_SHORT).show();
        }

private void showMessage(String string){
        Toast.makeText(getActivity(),string,Toast.LENGTH_SHORT).show();
        }
        }