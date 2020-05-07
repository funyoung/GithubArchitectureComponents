package com.boisneyphilippe.githubarchitecturecomponents.ui.fragments;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.boisneyphilippe.githubarchitecturecomponents.R;
import com.boisneyphilippe.githubarchitecturecomponents.data.database.entity.User;
import com.boisneyphilippe.githubarchitecturecomponents.view_models.UserProfileViewModel;
import com.boisneyphilippe.githubarchitecturecomponents.view_models.Injection;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileFragment extends Fragment implements AppBarLayout.OnOffsetChangedListener {

    // FOR DATA
    public static final String UID_KEY = "uid";
    /**
     * todo: instance dependency
     */
    private final ViewModelProvider.Factory viewModelFactory = Injection.injectUserProfileViewModelFactory();
    private UserProfileViewModel viewModel;

//    // FOR DESIGN
//    @BindView(R.id.user_avatar) ImageView imageView;
//    @BindView(R.id.fragment_user_profile_username) TextView username;
//    @BindView(R.id.fragment_user_profile_company) TextView company;
//    @BindView(R.id.fragment_user_profile_website) TextView website;

    @BindView(R.id.app_bar) AppBarLayout appBar;

    /**
     * 大布局背景，遮罩层
     */
    @BindView(R.id.bg_content) View bgContent;
    /**
    /**
     * 展开状态下toolbar显示的内容
     */
    @BindView(R.id.include_toolbar_open) View toolbarOpen;
    /**
     * 展开状态下toolbar的遮罩层
     */
    @BindView(R.id.bg_toolbar_open) View bgToolbarOpen;
    /**
     * 收缩状态下toolbar显示的内容
     */
    @BindView(R.id.include_toolbar_close) View toolbarClose;
    /**
     * 收缩状态下toolbar的遮罩层
     */
    @BindView(R.id.bg_toolbar_close) View bgToolbarClose;

    public UserProfileFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.circle_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.configureViewModel();

        appBar.addOnOffsetChangedListener(this);
    }

    // -----------------
    // CONFIGURATION
    // -----------------

    private void configureViewModel(){
        String userLogin = getArguments().getString(UID_KEY);
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
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        appBar.removeOnOffsetChangedListener(this);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxAlpha = 255;
        int r = Color.red(Color.DKGRAY);
        int g = Color.green(Color.DKGRAY);
        int b = Color.blue(Color.DKGRAY);

        //垂直方向偏移量
        int offset = Math.abs(verticalOffset);
        //最大偏移距离
        int scrollRange = appBarLayout.getTotalScrollRange();
        if (offset <= scrollRange / 2) {//当滑动没超过一半，展开状态下toolbar显示内容，根据收缩位置，改变透明值
            toolbarOpen.setVisibility(View.VISIBLE);
            toolbarClose.setVisibility(View.GONE);
            //根据偏移百分比 计算透明值
            float scale2 = (float) offset / (scrollRange / 2);
            int alpha2 = (int) (maxAlpha * scale2);
            bgToolbarOpen.setBackgroundColor(Color.argb(alpha2, r, g, b));
        } else {//当滑动超过一半，收缩状态下toolbar显示内容，根据收缩位置，改变透明值
            toolbarClose.setVisibility(View.VISIBLE);
            toolbarOpen.setVisibility(View.GONE);
            float scale3 = (float) (scrollRange  - offset) / (scrollRange / 2);
            int alpha3 = (int) (maxAlpha * scale3);
            bgToolbarClose.setBackgroundColor(Color.argb(alpha3, r, b, b));
        }
        //根据偏移百分比计算扫一扫布局的透明度值
        float scale = (float) offset / scrollRange;
        int alpha = (int) (maxAlpha * scale);
        bgContent.setBackgroundColor(Color.argb(alpha, r, g, b));
    }
}
