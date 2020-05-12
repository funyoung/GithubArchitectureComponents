package com.sogou.inputmethod.moment.ui.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boisneyphilippe.githubarchitecturecomponents.R;

/**
 * @author yangfeng
 */
public class TitleBarView extends LinearLayout {
    private static final float MIN_ALPHA = 0f;
    private static final float MAX_ALPHA = 1f;

    private ImageView iconView;
    private TextView titleView;

    public TitleBarView(Context context) {
        super(context);
        init();
    }

    public TitleBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TitleBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.include_title_bar_view, this, false);
        iconView = itemView.findViewById(R.id.iv_back);
        titleView = itemView.findViewById(R.id.title);

        int barHeight = getResources().getDimensionPixelOffset(R.dimen.toolbar_height);
        int avatarOffsetTop = getResources().getDimensionPixelOffset(R.dimen.circle_header_avatar_offset_top);
        int coverOffsetBottom = getResources().getDimensionPixelOffset(R.dimen.circle_header_cover_offset_bottom);
        configureThreshold(avatarOffsetTop, coverOffsetBottom, barHeight);

        addView(itemView);
    }

    /**
     * 开始淡出的偏移值
     */
    private int fadeOutOffsetMin;
    /**
     * 结束淡出的偏移值
     */
    private int fadeOutOffsetMax;

    /**
     * 开始淡入的偏移值
     */
    private int fadeInOffsetMin;
    /**
     * 开始淡入的偏移值
     */
    private int fadeInOffsetMax;

    /**
     * 复用同一个返回图标，仅实现先淡出，再淡入的效果
     * 当前状态：0 - 原始阶段，等待进入淡出
     * 1 - 淡出阶段，计算并应用alpha百分比
     * 2 - 淡出结束阶段，等待进入淡入（偏移继续增加）或者返回淡出（偏移减小）阶段
     * 3 - 淡入阶段，计算并应用alpha百分比
     * 4 - 淡入阶段结束，等待重回淡入阶段。
     * 从一个状态迁移到另一个状态时，需要考虑可能的重置：
     * 0 <-> 1: 无
     * 1 <-> 2: 无
     * 2 -> 3: 设置背景，替换返回图标，显示标题文本
     * 3 -> 2: 清除背景，替换返回图标，隐藏标题文本
     * 3 <-> 4: 无
     */
    private static final int STATE_INIT = 0;
    private static final int STATE_FADING_OUT = 1;
    private static final int STATE_FADING_OUT_END = 2;
    private static final int STATE_FADING_IN = 3;
    private static final int STATE_FADING_IN_END = 4;
    private int state;
    private void configureThreshold(int avatarOffsetTop, int coverOffsetBottom, int barHeight) {
        int halfHeight = barHeight / 2;
        int halfPauseHeight = halfHeight / 2;
        fadeOutOffsetMin = avatarOffsetTop - halfHeight;
        fadeOutOffsetMax = coverOffsetBottom - barHeight - halfPauseHeight;
        fadeInOffsetMin = coverOffsetBottom - barHeight  + halfPauseHeight;
        fadeInOffsetMax = coverOffsetBottom - halfHeight;
    }

    /**
     * @param offset
     */
    public void updateByOffset(int offset) {
        if (offset < fadeOutOffsetMin) {
            changeState(STATE_INIT);
            checkApplyingAlpha(MAX_ALPHA);
        } else if (offset >= fadeOutOffsetMin && offset < fadeOutOffsetMax) {
            changeState(STATE_FADING_OUT);
            applyFadeOutEffect(offset);
        } else if (offset >= fadeOutOffsetMax && offset < fadeInOffsetMin) {
            changeState(STATE_FADING_OUT_END);
            checkApplyingAlpha(MIN_ALPHA);
        } else if (offset >= fadeInOffsetMin && offset < fadeInOffsetMax) {
            changeState(STATE_FADING_IN);
            applyFadeInEffect(offset);
        } else {
            changeState(STATE_FADING_IN_END);
            checkApplyingAlpha(MAX_ALPHA);
        }
    }

    /**
     * [fadeOutOffsetMin - fadeOutOffsetMax] 对应alpha [1 - 0]
     * @param offset
     */
    private void applyFadeOutEffect(int offset) {
        if (offset < fadeOutOffsetMin) {
            checkApplyingAlpha(MAX_ALPHA);
        } else if (offset >= fadeOutOffsetMin && offset < fadeOutOffsetMax) {
            float alpha = ((float) fadeOutOffsetMax - offset) / (fadeOutOffsetMax - fadeOutOffsetMin);
            checkApplyingAlpha(alpha);
        } else {
            checkApplyingAlpha(MIN_ALPHA);
        }
    }

    private void checkApplyingAlpha(float alpha) {
        if (Math.abs(getAlpha() - alpha) > 0.001f) {
            setAlpha(alpha);
        }
    }

    /**
     * [fadeInOffsetMin - fadeInOffsetMax] 对应alpha [0 - 1]
     * @param offset
     */
    private void applyFadeInEffect(int offset) {
        if (offset < fadeInOffsetMin) {
            checkApplyingAlpha(MIN_ALPHA);
        } else if (offset >= fadeInOffsetMin && offset < fadeInOffsetMax) {
            float alpha = ((float) offset - fadeInOffsetMin) / (fadeInOffsetMax - fadeInOffsetMin);
            checkApplyingAlpha(alpha);
        } else {
            checkApplyingAlpha(MAX_ALPHA);
        }
    }

    private void changeState(int newState) {
        if (newState != state) {
            if (newState >= STATE_FADING_IN && state < STATE_FADING_IN ) {
                setBackgroundResource(R.color.circle_title_bar_background);
                titleView.setVisibility(VISIBLE);
                iconView.setImageResource(R.drawable.back_drawable_black);
                if (newState >= STATE_FADING_IN_END) {
                    setAlpha(MAX_ALPHA);
                }
            } else if (newState <= STATE_FADING_OUT_END && state > STATE_FADING_OUT_END) {
                setBackgroundResource(0);
                titleView.setVisibility(GONE);
                iconView.setImageResource(R.drawable.back_drawable);
                if (newState < STATE_FADING_OUT) {
                    setAlpha(MAX_ALPHA);
                }
            }

            state = newState;
        }
    }
}
