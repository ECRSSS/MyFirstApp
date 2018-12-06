package ru.qagods.myfirstapp.comments;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ru.qagods.myfirstapp.R;
import ru.qagods.myfirstapp.application.App;
import ru.qagods.myfirstapp.model.comment.Comment;
import ru.qagods.myfirstapp.model.comment.PostComment;
import ru.qagods.myfirstapp.model.comment.PostCommentResponce;
import ru.qagods.myfirstapp.utils.ApiUtils;

public class CommentsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public static final String ID_KEY = "ID_KEY";

    @NonNull
    private final CommentAdapter mCommentAdapter = new CommentAdapter();
    private int lastCommentsCount = -1;

    private int mAlbumId;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefresher;
    private View mErrorView;
    private View mNoCommentsView;

    private EditText mNewComment;
    private Button mPostButton;

    private App app;

    public static CommentsFragment newInstance(int albumId) {
        Bundle args = new Bundle();
        args.putInt(ID_KEY, albumId);
        CommentsFragment fragment = new CommentsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onRefresh() {
        mRefresher.post(() -> {
            mRefresher.setRefreshing(true);
            getComments();
        });

    }

    private void getComments() {

        ApiUtils.getApiRx("", "", false).getComments()
                .subscribeOn(Schedulers.io())
                .doOnSuccess(comments -> app.getMusicDao().insertComments(comments))
                .onErrorReturn(throwable -> {
                    if (ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass())) {
                        getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),"Подключение отсутсвует",Toast.LENGTH_SHORT).show());
                        return app.getMusicDao().getCommentsByAlbumId(mAlbumId);
                    }
                    return null;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mRefresher.setRefreshing(true))
                .doFinally(() -> mRefresher.setRefreshing(false))

                .subscribe(comments -> {
                    comments = comments.stream().filter(c -> c.getAlbumId() == mAlbumId).collect(Collectors.toList());
                    mErrorView.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mCommentAdapter.addData(comments, true);
                    if (lastCommentsCount == -1) {
                        lastCommentsCount = mCommentAdapter.getItemCount();
                    } else {
                        if (mCommentAdapter.getItemCount() == lastCommentsCount) {
                            Toast.makeText(getActivity(), "Новых комментариев нет", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Комментарии обновлены", Toast.LENGTH_SHORT).show();
                        }
                        lastCommentsCount = mCommentAdapter.getItemCount();
                    }
                    if (mCommentAdapter.getItemCount() == 0) {
                        mNoCommentsView.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAlbumId = getArguments().getInt(ID_KEY);
        getActivity().setTitle("Комментарии к альбому");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mCommentAdapter);
        app = (App) getActivity().getApplication();
        onRefresh();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_comments, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.recyclerComments);
        mRefresher = view.findViewById(R.id.refresherComments);
        mRefresher.setOnRefreshListener(this);
        mErrorView = view.findViewById(R.id.error_view);
        mNoCommentsView = view.findViewById(R.id.no_comments_view);

        mNewComment = view.findViewById(R.id.et_new_comment);
        mNewComment.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                mPostButton.callOnClick();
                return true;

            }
            return false;
        });
        mPostButton = view.findViewById(R.id.btn_send_comment);
        mPostButton.setOnClickListener(v -> {
            if (TextUtils.isEmpty(mNewComment.getText().toString())) {
                Toast.makeText(getActivity(), "Сообщение пусто", Toast.LENGTH_SHORT).show();
            } else {
                postMessage(mNewComment.getText().toString());
            }
        });
    }

    private void postMessage(String message) {
        PostComment postComment = new PostComment(message, mAlbumId);
        ApiUtils.getApiRxWithoutDataConverter().postComment(postComment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mRefresher.setRefreshing(true))
                .doFinally(() -> mRefresher.setRefreshing(false))
                .subscribe(comment -> {
                    getMessage(comment.getId());
                }, throwable -> {
                    Toast.makeText(getActivity(), throwable.toString(), Toast.LENGTH_SHORT).show();
                });
    }

    private void getMessage(int id) {
        ApiUtils.getApiRx("", "", false).getCommentById(id)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(comment -> {
                    app.getMusicDao().insertComment(comment);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mRefresher.setRefreshing(true))
                .doFinally(() -> mRefresher.setRefreshing(false))
                .subscribe(comment -> {
                    List<Comment> comments = new ArrayList<>();
                    comments.add(comment);
                    mCommentAdapter.addData(comments, false);
                    lastCommentsCount = mCommentAdapter.getItemCount();
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mNoCommentsView.setVisibility(View.GONE);
                }, throwable -> {
                    Toast.makeText(getActivity(), throwable.toString(), Toast.LENGTH_SHORT).show();
                });
    }
}
