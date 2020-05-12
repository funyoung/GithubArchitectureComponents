package com.boisneyphilippe.githubarchitecturecomponents.repositories.entity;

import java.util.List;

/**
 * @author yangfeng
 */
public class ContentData {
    private String text;
    private List<Image> images;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
