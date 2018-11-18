package ru.qagods.myfirstapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ru.qagods.myfirstapp.model.User;

public class SharedPreferencesHelper {
    public static final String SHARED_PREFS_NAME = "SHARED_PREFS_NAME";
    public static final String USERS_KEY = "USERS_KEY";
    public static final Type USERS_TYPE=new TypeToken<List<User>>(){}.getType();

    private SharedPreferences mSharedPreferences;
    private Gson mGson=new Gson();

    public SharedPreferencesHelper(Context context) {
        this.mSharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME,Context.MODE_PRIVATE);
    }

    public List<User> getUsers(){
        try {
            List<User> users = mGson.fromJson(mSharedPreferences.getString(USERS_KEY, USERS_KEY), USERS_TYPE);
            return users;
        }catch (JsonSyntaxException ex){
            Log.e("error","Нет юзеров",ex);
            return new ArrayList<>();
        }
    }

    public boolean addUser(User user){
        ArrayList<User> users=(ArrayList<User>) getUsers();
        if(users.contains(user)){return false;}
        else{
            users.add(user);
            String usersJson=mGson.toJson(users,USERS_TYPE);
            mSharedPreferences.edit().putString(USERS_KEY,usersJson).apply();
            return true;
        }
    }

    public boolean login(User user){
        ArrayList<User> users=(ArrayList<User>) getUsers();
        if(users.contains(user)){
            return true;
        }else
            return false;
    }

    public List<String> getSuccessLogins() {
        ArrayList<String> logins=new ArrayList<>();
        for(User user : getUsers()){
                logins.add(user.getmLogin());
        }
        return logins;
    }
}
