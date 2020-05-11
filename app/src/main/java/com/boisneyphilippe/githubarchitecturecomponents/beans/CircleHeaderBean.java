package com.boisneyphilippe.githubarchitecturecomponents.beans;

import com.boisneyphilippe.githubarchitecturecomponents.data.database.entity.User;

import static com.boisneyphilippe.githubarchitecturecomponents.Constants.FriendCircleType.FRIEND_CIRCLE_TYPE_HEADER;

/**
 *
 */
public class CircleHeaderBean extends CircleItemBean {
    private final User user;
    public CircleHeaderBean(User user) {
        super(FRIEND_CIRCLE_TYPE_HEADER);
        this.user = user;
    }
}
