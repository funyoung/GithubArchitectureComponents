package com.boisneyphilippe.githubarchitecturecomponents.beans;

import android.support.annotation.NonNull;

import com.boisneyphilippe.githubarchitecturecomponents.data.database.entity.User;

import static com.boisneyphilippe.githubarchitecturecomponents.Constants.FriendCircleType.FRIEND_CIRCLE_TYPE_HEADER;

/**
 *
 */
public class CircleHeaderBean extends CircleItemBean {
    @NonNull
    private final User user;
    public CircleHeaderBean(@NonNull User user) {
        super(FRIEND_CIRCLE_TYPE_HEADER);
        this.user = user;
    }

    public String getUserName() {
        return user.getName();
    }

    public String getCover() {
        return null;
    }

    public String getAvatar() {
        return user.getAvatar_url();
    }
}
