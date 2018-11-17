package ru.qagods.myfirstapp;

import android.support.v4.app.Fragment;

public class AuthActivity extends SingleFragmentActivity{
    @Override
    public Fragment getFragment() {
        return AuthFragment.newInstance();
    }
}
