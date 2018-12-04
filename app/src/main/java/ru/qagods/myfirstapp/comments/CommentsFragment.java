package ru.qagods.myfirstapp.comments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.stream.Collectors;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.qagods.myfirstapp.R;
import ru.qagods.myfirstapp.utils.ApiUtils;

public class CommentsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public static final String ID_KEY="ID_KEY";

    @NonNull
    private final CommentAdapter mCommentAdapter = new CommentAdapter();

    private int mAlbumId;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefresher;
    private View mErrorView;
    private View mNoCommentsView;

    private EditText mNewComment;
    private Button mPostButton;

    public static CommentsFragment newInstance(int albumId) {
        Bundle args = new Bundle();
        args.putInt(ID_KEY,albumId);
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

        ApiUtils.getApi("", "", false).getComments()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mRefresher.setRefreshing(true))
                .doFinally(() -> mRefresher.setRefreshing(false))
                .subscribe(comments -> {
                    comments=comments.stream().filter(c->c.getAlbumId()==mAlbumId).collect(Collectors.toList());
                    mErrorView.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mCommentAdapter.addData(comments, true);
                    if(mCommentAdapter.getItemCount()==0){
                        mNoCommentsView.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.GONE);
                    }
                }, throwable -> {
                    mErrorView.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAlbumId=getArguments().getInt(ID_KEY);
        getActivity().setTitle("Комментарии к альбому");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mCommentAdapter);
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
        mNoCommentsView =view.findViewById(R.id.no_comments_view);

        mNewComment=view.findViewById(R.id.et_new_comment);
        mPostButton=view.findViewById(R.id.btn_send_comment);
        mPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(mNewComment.getText().toString())){
                    Toast.makeText(getActivity(),"Сообщение пусто",Toast.LENGTH_SHORT).show();
                }else{
                    postMessage(mNewComment.getText().toString());
                }
            }
        });

    }

    private void postMessage(String message) {
    }
}