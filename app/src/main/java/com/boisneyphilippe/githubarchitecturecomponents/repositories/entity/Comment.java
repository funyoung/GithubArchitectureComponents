package com.boisneyphilippe.githubarchitecturecomponents.repositories.entity;

/**
 * @author yangfeng
 */
public class Comment {
    private String userName;
    private String text;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
