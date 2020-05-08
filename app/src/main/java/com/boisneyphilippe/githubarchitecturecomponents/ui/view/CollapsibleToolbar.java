package com.boisneyphilippe.githubarchitecturecomponents.ui.view;

import android.content.Context;
import android.support.constraint.motion.MotionLayout;
import android.support.design.widget.AppBarLayout;
import android.util.AttributeSet;
import android.view.ViewParent;

public class CollapsibleToolbar extends MotionLayout implements AppBarLayout.OnOffsetChangedListener {
    public CollapsibleToolbar(Context context) {
        super(context);
    }

    public CollapsibleToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CollapsibleToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        setProgress(-verticalOffset / Float.valueOf(appBarLayout.getTotalScrollRange()));
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewParent viewParent = getParent();
        if (viewParent instanceof AppBarLayout) {
            ((AppBarLayout) viewParent).addOnOffsetChangedListener(this);
        }
    }

}
