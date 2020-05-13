package com.sogou.inputmethod.moment.ui.activities;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.boisneyphilippe.githubarchitecturecomponents.R;
import com.sogou.inputmethod.moment.ui.adapters.FriendCircleAdapter;
import com.sogou.inputmethod.moment.ui.beans.CircleItemBean;
import com.sogou.inputmethod.moment.repositories.entity.User;
import com.sogou.inputmethod.moment.ui.interfaces.OnPraiseOrCommentClickListener;
import com.sogou.inputmethod.moment.DataCenter;
import com.sogou.inputmethod.moment.ui.others.FriendsCircleAdapterDivideLine;
import com.sogou.inputmethod.moment.utils.Utils;
import com.sogou.inputmethod.moment.ui.view_models.Injection;
import com.sogou.inputmethod.moment.ui.view_models.UserProfileViewModel;
import com.sogou.inputmethod.moment.ui.widgets.CommentPanelView;
import com.sogou.inputmethod.moment.ui.widgets.GlideImageWatcherLoader;
import com.sogou.inputmethod.moment.ui.widgets.TitleBarView;
import com.bumptech.glide.Glide;
import com.github.ielse.imagewatcher.ImageWatcher;
import com.github.ielse.imagewatcher.ImageWatcherHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        OnPraiseOrCommentClickListener, ImageWatcher.OnPictureLongPressListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static String USER_LOGIN = "JakeWharton";

    private final ViewModelProvider.Factory viewModelFactory = Injection.injectUserProfileViewModelFactory();
    private UserProfileViewModel viewModel;

    private FriendCircleAdapter mFriendCircleAdapter;

    private ImageWatcher imageWatcher;

    @BindView(R.id.title_bar_view) TitleBarView titleBarView;
    @BindView(R.id.recyclerview) RecyclerView recyclerView;
    @BindView(R.id.comment_panel_view) CommentPanelView commentPanelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);

//        this.showFragment(savedInstanceState);
        ButterKnife.bind(this, this);

        imageWatcher = ImageWatcherHelper.with(this) // 一般来讲，ImageWatcher尺寸占据全屏
                .setLoader(new GlideImageWatcherLoader())
                //.setIndexProvider(new DotIndexProvider()) // 自定义
                .create();

        configRecyclerView(recyclerView, imageWatcher);

        commentPanelView.setOnSendCommentListener(new CommentPanelView.OnSendCommentListener() {
            @Override
            public void onSendComment(String comment, int position) {
                // todo: send comment.
                Log.i(TAG, "onSendComment, position = " + position + ", " + comment);
            }
        });


        this.configureViewModel();
//        Utils.showSwipeRefreshLayout(this::asyncMakeData);
//        asyncMakeData();
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
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int offset = recyclerView.computeVerticalScrollOffset();
                    updateScrolledOffset(offset);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int offset = recyclerView.computeVerticalScrollOffset();
                updateScrolledOffset(offset);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new FriendsCircleAdapterDivideLine());
        mFriendCircleAdapter = new FriendCircleAdapter(this, imageWatcher);
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

    private void updateScrolledOffset(int offset) {
        if (null != titleBarView) {
            titleBarView.updateByOffset(offset);
        }
        Log.i(TAG, "updateScrolledOffset: " + offset);
    }

    // todo: make this task background.
    private void asyncMakeData(User user) {
        List<CircleItemBean> circlePostBeans = DataCenter.makeFriendCircleBeans(this, user);
        if (null != circlePostBeans) {
            mFriendCircleAdapter.setFriendCircleBeans(circlePostBeans);
        }
    }

    @Override
    public void onPraiseClick(int position) {
        Toast.makeText(this, "You Click Praise!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCommentClick(int position) {
//        Toast.makeText(this, "you click comment", Toast.LENGTH_SHORT).show();
        commentPanelView.showCommentPanel(position);
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

    // -----------------
    // CONFIGURATION
    // -----------------

    private void configureViewModel(){
        String userLogin = USER_LOGIN;
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(UserProfileViewModel.class);
        viewModel.init(userLogin);
        viewModel.getUser().observe(this, user -> updateUI(user));
    }

    // -----------------
    // UPDATE UI
    // -----------------

    private void updateUI(@Nullable User user){
        if (user != null){
//            Glide.with(this).load(user.getAvatar_url()).apply(RequestOptions.circleCropTransform()).into(imageView);
//            this.username.setText(user.getName());
//            this.company.setText(user.getCompany());
//            this.website.setText(user.getBlog());
//            Glide.with(this).load(user.getAvatar_url()).apply(RequestOptions.circleCropTransform()).into(headerAvatar);
//            RenderUtil.rounding(Glide.with(this), headerAvatar, user.getAvatar_url(), R.drawable.circle_header_avatar, R.dimen.circle_header_avatar_radius);
        }

        asyncMakeData(user);
    }

}
