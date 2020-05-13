package com.sogou.inputmethod.moment.ui.others;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sogou.inputmethod.moment.utils.Utils;

/**
 * @author KCrason
 * @date 2018/5/2
 */
public class FriendsCircleAdapterDivideLine extends RecyclerView.ItemDecoration {
    private int mDivideHeight;

    public FriendsCircleAdapterDivideLine() {
        mDivideHeight = Utils.dp2px(0.5f);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 0, 0, mDivideHeight);
    }
}