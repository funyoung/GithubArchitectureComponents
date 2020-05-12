package com.boisneyphilippe.githubarchitecturecomponents.repositories.entity;

/**
 * Home page of moment
 * @author yangfeng
 */
public class HomePage extends Page {
    private User user;
    private String backgroundImageURL;
    private Message message;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBackgroundImageURL() {
        return backgroundImageURL;
    }

    public void setBackgroundImageURL(String backgroundImageURL) {
        this.backgroundImageURL = backgroundImageURL;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
