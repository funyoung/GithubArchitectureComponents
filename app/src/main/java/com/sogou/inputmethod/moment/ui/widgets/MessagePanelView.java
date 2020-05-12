package com.sogou.inputmethod.moment.ui.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.boisneyphilippe.githubarchitecturecomponents.R;
import com.sogou.inputmethod.moment.ui.beans.CircleMessageBean;
import com.sogou.inputmethod.moment.ui.RenderUtil;
import com.bumptech.glide.Glide;

/**
 * @author yangfeng
 */
public class MessagePanelView extends ConstraintLayout  {
    @Nullable
    private CircleMessageBean messageBean;

    private ImageView avatarView;
    private TextView titleView;

    public MessagePanelView(Context context) {
        super(context);
        init();
    }

    public MessagePanelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MessagePanelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.view_message_panel, this, false);
        avatarView = itemView.findViewById(R.id.message_avatar);
        titleView = itemView.findViewById(R.id.message_title);
        addView(itemView);
    }

    public void setBean(CircleMessageBean messageBean) {
        this.messageBean = messageBean;
        if (null == messageBean) {
            setVisibility(GONE);
        } else {
            setVisibility(VISIBLE);
            titleView.setText(messageBean.getMessage());
            RenderUtil.rounding(Glide.with(this), avatarView, messageBean.getAvatar(), R.drawable.circle_header_avatar, R.dimen.circle_message_avatar_radius);
        }
    }

    public CircleMessageBean getBean() {
        return messageBean;
    }

    public void consume() {
        setBean(null);
    }
}
