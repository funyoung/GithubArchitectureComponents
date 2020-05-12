package com.boisneyphilippe.githubarchitecturecomponents.beans;

/**
 * Bean for render, which contains 3 type of view: header, message and post list item.
 * After retrieving data from remote server, parsing these kind of beans, them save add
 * into a list for rendering within a recycler view.
 *
 * @author yangfeng
 */
public abstract class CircleItemBean {
    private final int viewType;

    protected CircleItemBean(int viewType) {
        this.viewType = viewType;
    }

    public int getViewType() {
        return viewType;
    }

    public long getId() {
        return 0;
    }
}
