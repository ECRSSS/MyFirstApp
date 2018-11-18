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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ru.qagods.myfirstapp.model.User;
import ru.qagods.myfirstapp.utils.SharedPreferencesHelper;

public class AuthFragment extends Fragment {

    private AutoCompleteTextView mLoginField;
    private EditText mPasswordField;
    private Button mEnterButton;
    private Button mRegisterButton;
    private SharedPreferencesHelper mSharedPreferencesHelper;

    private ArrayAdapter<String> mLoginedUsers;

    public static AuthFragment newInstance() {

        Bundle args = new Bundle();

        AuthFragment fragment = new AuthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private View.OnFocusChangeListener mOnLoginFocusListener=new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus){
                mLoginField.showDropDown();
            }
        }
    };


    private View.OnClickListener mOnClickEnterButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            User user = mSharedPreferencesHelper.login(mLoginField.getText().toString(),mPasswordField.getText().toString());
            if (user!=null) {
                Intent startProfileActivityIntent = new Intent(getActivity(), ProfileActivity.class);
                startProfileActivityIntent.putExtra(ProfileActivity.USER_KEY, user);
                startActivity(startProfileActivityIntent);
            } else {
                showMessage(R.string.loginError);
            }
        }
    };
    private View.OnClickListener mOnClickRegisterButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, RegistrationFragment.newInstance())
                    .addToBackStack(RegistrationFragment.class.getName())
                    .commit();
        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_auth, container, false);
        mSharedPreferencesHelper = new SharedPreferencesHelper(getActivity());

        mLoginedUsers = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                mSharedPreferencesHelper.getSuccessLogins());

        mLoginField = v.findViewById(R.id.login);
        mLoginField.setAdapter(mLoginedUsers);
        mLoginField.setOnFocusChangeListener(mOnLoginFocusListener);

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
