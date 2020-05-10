package com.boisneyphilippe.githubarchitecturecomponents.ui.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.Toast;

import com.boisneyphilippe.githubarchitecturecomponents.R;
import com.boisneyphilippe.githubarchitecturecomponents.adapters.FriendCircleAdapter;
import com.boisneyphilippe.githubarchitecturecomponents.beans.FriendCircleBean;
import com.boisneyphilippe.githubarchitecturecomponents.interfaces.OnPraiseOrCommentClickListener;
import com.boisneyphilippe.githubarchitecturecomponents.others.DataCenter;
import com.boisneyphilippe.githubarchitecturecomponents.others.FriendsCircleAdapterDivideLine;
import com.boisneyphilippe.githubarchitecturecomponents.utils.Utils;
import com.boisneyphilippe.githubarchitecturecomponents.widgets.CommentPanelView;
import com.boisneyphilippe.githubarchitecturecomponents.widgets.GlideImageWatcherLoader;
import com.bumptech.glide.Glide;
import com.github.ielse.imagewatcher.ImageWatcher;
import com.github.ielse.imagewatcher.ImageWatcherHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements
        OnPraiseOrCommentClickListener, ImageWatcher.OnPictureLongPressListener {

    private static String USER_LOGIN = "JakeWharton";

    private Disposable mDisposable;

    private FriendCircleAdapter mFriendCircleAdapter;

    private ImageWatcher imageWatcher;

    @BindView(R.id.recyclerview) RecyclerView recyclerView;
    @BindView(R.id.emoji_panel_view) CommentPanelView commentPanelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        this.showFragment(savedInstanceState);
        ButterKnife.bind(this, this);


        imageWatcher = ImageWatcherHelper.with(this) // 一般来讲，ImageWatcher尺寸占据全屏
                .setLoader(new GlideImageWatcherLoader())
                //.setIndexProvider(new DotIndexProvider()) // 自定义
                .create();

        commentPanelView.initEmojiPanel(DataCenter.emojiDataSources);
        configRecyclerView(recyclerView, imageWatcher);

//        Utils.showSwipeRefreshLayout(this::asyncMakeData);
        asyncMakeData();
    }

//    private void showFragment(Bundle savedInstanceState){
//        if (savedInstanceState == null) {
//
//            UserProfileFragment fragment = new UserProfileFragment();
//
//            Bundle bundle = new Bundle();
//            bundle.putString(UserProfileFragment.UID_KEY, USER_LOGIN);
//            fragment.setArguments(bundle);
//
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.fragment_container, fragment, null)
//                    .commit();
//        }
//    }

    private void configRecyclerView(RecyclerView recyclerView, ImageWatcher imageWatcher) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                updateScrolledUi(newState == RecyclerView.SCROLL_STATE_IDLE);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new FriendsCircleAdapterDivideLine());
        mFriendCircleAdapter = new FriendCircleAdapter(this, recyclerView, imageWatcher);
        recyclerView.setAdapter(mFriendCircleAdapter);

        imageWatcher.setTranslucentStatus(Utils.calcStatusBarHeight(this));
        imageWatcher.setErrorImageRes(R.mipmap.error_picture);
        imageWatcher.setOnPictureLongPressListener(this);
    }

    private void updateScrolledUi(boolean idle) {
        if (idle) {
            Glide.with(this).resumeRequests();
        } else {
            Glide.with(this).pauseRequests();
        }
    }


    private void asyncMakeData() {
        mDisposable = Single.create((SingleOnSubscribe<List<FriendCircleBean>>) emitter ->
                emitter.onSuccess(DataCenter.makeFriendCircleBeans(this)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((friendCircleBeans, throwable) -> {
                    if (friendCircleBeans != null && throwable == null) {
                        mFriendCircleAdapter.setFriendCircleBeans(friendCircleBeans);
                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }


    @Override
    public void onPraiseClick(int position) {
        Toast.makeText(this, "You Click Praise!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCommentClick(int position) {
//        Toast.makeText(this, "you click comment", Toast.LENGTH_SHORT).show();
        commentPanelView.showEmojiPanel();
    }

    @Override
    public void onBackPressed() {
        if (!imageWatcher.handleBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public void onPictureLongPress(ImageView var1, Uri var2, int var3) {
    }
}
