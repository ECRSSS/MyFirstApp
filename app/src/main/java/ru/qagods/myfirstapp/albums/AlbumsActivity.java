package ru.qagods.myfirstapp.albums;

import android.support.v4.app.Fragment;

import ru.qagods.myfirstapp.SingleFragmentActivity;


public class AlbumsActivity extends SingleFragmentActivity {

    @Override
    public Fragment getFragment() {
        return AlbumsFragment.newInstance();
    }
}
