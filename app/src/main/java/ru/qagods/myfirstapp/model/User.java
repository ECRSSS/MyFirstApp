package ru.qagods.myfirstapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    @SerializedName("email")
    private String mLogin;

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    @SerializedName("name")
    private String mName;
    @SerializedName("password")
    private String mPassword;
    private boolean isLogined;

    public boolean isLogined() {
        return isLogined;
    }

    public void setLogined(boolean logined) {
        isLogined = logined;
    }

    public User(String mLogin, String mName, String mPassword) {
        this.mLogin = mLogin;
        this.mPassword = mPassword;
        this.mName=mName;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmLogin() {

        return mLogin;
    }

    public void setmLogin(String mLogin) {
        this.mLogin = mLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return mLogin.equalsIgnoreCase(user.mLogin) &&
                Objects.equals(mPassword, user.mPassword);
    }

    @Override
    public String toString() {
        return "User{" +
                "mLogin='" + mLogin + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", isLogined=" + isLogined +
                '}';
    }

    @Override
    public int hashCode() {

        return Objects.hash(mLogin, mPassword);
    }
}
