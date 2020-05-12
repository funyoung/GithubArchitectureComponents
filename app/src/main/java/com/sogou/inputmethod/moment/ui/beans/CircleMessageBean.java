package com.sogou.inputmethod.moment.ui.beans;

import android.support.annotation.NonNull;

import com.sogou.inputmethod.moment.repositories.entity.Message;

import static com.sogou.inputmethod.moment.Constants.FriendCircleType.FRIEND_CIRCLE_TYPE_MESSAGE;

/**
 * Adapter between CircleItemBean and Message
 * @author yangfeng
 */
public class CircleMessageBean extends CircleItemBean {
    @NonNull
    private final Message message;
    public CircleMessageBean(@NonNull Message message) {
        super(FRIEND_CIRCLE_TYPE_MESSAGE);
        this.message = message;
    }

    public String getAvatar() {
        return message.getAvatar_url();
    }

    public String getMessage() {
        return message.getMessage();
    }

    public long getMomentID() {
        return message.getMomentID();
    }
}
