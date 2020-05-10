package com.boisneyphilippe.githubarchitecturecomponents.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.boisneyphilippe.githubarchitecturecomponents.R;

/**
 * @author yangfeng
 */
public class HeaderPanelView extends ConstraintLayout  {
    public HeaderPanelView(Context context) {
        super(context);
        init();
    }

    public HeaderPanelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HeaderPanelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.view_header_panel, this, false);
        addView(itemView);
    }

}