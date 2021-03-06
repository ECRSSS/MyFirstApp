package ru.qagods.myfirstapp.album;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ru.qagods.myfirstapp.R;
import ru.qagods.myfirstapp.application.App;
import ru.qagods.myfirstapp.comments.CommentsFragment;
import ru.qagods.myfirstapp.model.Album;
import ru.qagods.myfirstapp.model.Song;
import ru.qagods.myfirstapp.utils.ApiUtils;

public class DetailAlbumFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String ALBUM_KEY = "ALBUM_KEY";

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefresher;
    private View mErrorView;
    private Album mAlbum;

    private App app;

    @NonNull
    private final SongsAdapter mSongsAdapter = new SongsAdapter();

    public static DetailAlbumFragment newInstance(Album album) {
        Bundle args = new Bundle();
        args.putSerializable(ALBUM_KEY, album);

        DetailAlbumFragment fragment = new DetailAlbumFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_albums, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        app=(App)getActivity().getApplication();
        mRecyclerView = view.findViewById(R.id.recycler);
        mRefresher = view.findViewById(R.id.refresher);
        mRefresher.setOnRefreshListener(this);
        mErrorView = view.findViewById(R.id.error_view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAlbum = (Album) getArguments().getSerializable(ALBUM_KEY);

        getActivity().setTitle(mAlbum.getName());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mSongsAdapter);

        onRefresh();
    }

    @Override
    public void onRefresh() {
        mRefresher.post(() -> {
            mRefresher.setRefreshing(true);
            getAlbum();
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detalized_albm_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.goToComments){
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container,CommentsFragment.newInstance(mAlbum.getId()))
                    .addToBackStack(CommentsFragment.class.getSimpleName())
                    .commit();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getAlbum() {

        ApiUtils.getApiRx("", "", false).getAlbum(mAlbum.getId())
                .subscribeOn(Schedulers.io())
                .doOnSuccess(new Consumer<Album>() {
                    @Override
                    public void accept(Album album) throws Exception {
                        List<Song> songs=album.getSongs();
                        songs.forEach(s->s.setAlbumkey(album.getId()));
                        app.getMusicDao().insertSongs(songs);
                    }
                })
                .onErrorReturn(throwable -> {
                    if(ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass())){
                        Album album=new Album();
                        album.setSongs(app.getMusicDao().getSongsById(mAlbum.getId()));
                        return album;
                    }
                    return null;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mRefresher.setRefreshing(true))
                .doFinally(() -> mRefresher.setRefreshing(false))
                .subscribe(album -> {
                    mErrorView.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mSongsAdapter.addData(album.getSongs(), true);
                }, throwable -> {
                    mErrorView.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                });
    }

}