package ru.qagods.myfirstapp.model;

public class User {
    private String mLogin;
    private String mPassword;

    public User(String mLogin, String mPassword) {
        this.mLogin = mLogin;
        this.mPassword = mPassword;
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
}
