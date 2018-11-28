package ru.qagods.myfirstapp;

import android.content.Intent;
import android.os.Handler;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import ru.qagods.myfirstapp.model.User;
import ru.qagods.myfirstapp.utils.ApiUtils;

import static ru.qagods.myfirstapp.RegistrationFragment.JSON;

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

            Request request = new Request.Builder()
                    .url(BuildConfig.SERVER_URL.concat("user/"))
                    .build();

            OkHttpClient okHttpClient = ApiUtils.
                    getBasicAuthClient(mLoginField.getText().toString(),
                            mPasswordField.getText().toString(), true);
            okHttpClient.newCall(request).enqueue(new Callback() {
                Handler handler = new Handler(getActivity().getMainLooper());

                @Override
                public void onFailure(Call call, IOException e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            showMessage(R.string.enqueue_error);
                        }
                    });
                }

                @Override
                public void onResponse(final Call call, final Response response) throws IOException {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (response.isSuccessful()) {
                                Gson gson = new Gson();
                                User user = null;
                                try {
                                    JsonObject json = gson.fromJson(response.body().string(), JsonObject.class);
                                    user = gson.fromJson(json.get("data"), User.class);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Intent startProfileActivityIntent = new Intent(getActivity(), ProfileActivity.class);
                                startProfileActivityIntent.putExtra(ProfileActivity.USER_KEY, user);
                                startActivity(startProfileActivityIntent);

                            } else {

                                showMessage(R.string.loginError);

                            }
                        }
                    });
                }
            });
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
