package com.boisneyphilippe.githubarchitecturecomponents.beans;

import com.boisneyphilippe.githubarchitecturecomponents.data.database.entity.Message;

import static com.boisneyphilippe.githubarchitecturecomponents.Constants.FriendCircleType.FRIEND_CIRCLE_TYPE_MESSAGE;

/**
 *
 */
public class CircleMessageBean extends CircleItemBean {
    private final Message message;
    public CircleMessageBean(Message message) {
        super(FRIEND_CIRCLE_TYPE_MESSAGE);
        this.message = message;
    }
}
