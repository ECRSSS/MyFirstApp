package ru.qagods.myfirstapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ru.qagods.myfirstapp.model.User;

public class AuthFragment extends Fragment {

    private EditText mLoginField;
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
            if (isEmailValid() && isPasswordValid()) {
                Intent startProfileActivityIntent = new Intent(getActivity(), ProfileActivity.class);
                User user = new User(mLoginField.getText().toString(), mPasswordField.getText().toString());
                startProfileActivityIntent.putExtra(ProfileActivity.USER_KEY, user);
                startActivity(startProfileActivityIntent);
            } else {
                showMessage(R.string.accessDeniedToastText);
            }
        }
    };
    private View.OnClickListener mOnClickRegisterButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container,RegistrationFragment.newInstance())
                    //.addToBackStack(null)
                    .commit();
        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_auth, container, false);

        mLoginField = v.findViewById(R.id.login);
        mPasswordField = v.findViewById(R.id.password);
        mEnterButton = v.findViewById(R.id.buttonEnter);
        mRegisterButton = v.findViewById(R.id.buttonRegister);

        mEnterButton.setOnClickListener(mOnClickEnterButtonListener);
        mRegisterButton.setOnClickListener(mOnClickRegisterButtonListener);
        return v;
    }

    private boolean isEmailValid() {
        String text = mLoginField.getText().toString();
        if (!TextUtils.isEmpty(text) && Patterns.EMAIL_ADDRESS.matcher(text).matches())
            return true;
        else
            return false;
    }

    private boolean isPasswordValid() {
        String text = mLoginField.getText().toString();
        if (!TextUtils.isEmpty(text))
            return true;
        else
            return false;
    }

    private void showMessage(@StringRes int string) {
        Toast.makeText(getActivity(), getString(string), Toast.LENGTH_SHORT).show();
    }
}
