package com.boisneyphilippe.githubarchitecturecomponents.ui.beans;

import android.support.annotation.NonNull;

import com.boisneyphilippe.githubarchitecturecomponents.repositories.entity.Message;

import static com.boisneyphilippe.githubarchitecturecomponents.ui.adapters.FriendCircleType.FRIEND_CIRCLE_TYPE_MESSAGE;

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
