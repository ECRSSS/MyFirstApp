package ru.qagods.myfirstapp.albums;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.qagods.myfirstapp.R;
import ru.qagods.myfirstapp.album.DetailAlbumFragment;
import ru.qagods.myfirstapp.model.Albums;
import ru.qagods.myfirstapp.utils.ApiUtils;


/**
 * @author Azret Magometov
 */

public class AlbumsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefresher;
    private View mErrorView;

    @NonNull
    private final AlbumsAdapter mAlbumAdapter = new AlbumsAdapter(album -> {
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, DetailAlbumFragment.newInstance(album))
                .addToBackStack(DetailAlbumFragment.class.getSimpleName())
                .commit();
    });

    public static AlbumsFragment newInstance() {
        return new AlbumsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_albums, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.recycler);
        mRefresher = view.findViewById(R.id.refresher);
        mRefresher.setOnRefreshListener(this);
        mErrorView = view.findViewById(R.id.error_view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Альбомы");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAlbumAdapter);

        onRefresh();
    }

    @Override
    public void onRefresh() {
        mRefresher.post(() -> {
            mRefresher.setRefreshing(true);
            getAlbums();
        });
    }

    private void getAlbums() {
        ApiUtils.getApi("", "", false).getAlbums()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    mRefresher.setRefreshing(true);
                })
                .doFinally(() -> mRefresher.setRefreshing(false))
                .subscribe(albums -> {
                    mErrorView.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mAlbumAdapter.addData(albums.getData(), true);
                }, throwable -> {
                    mErrorView.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                });
    }

}
