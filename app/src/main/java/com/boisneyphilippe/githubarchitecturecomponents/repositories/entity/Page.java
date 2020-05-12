package com.boisneyphilippe.githubarchitecturecomponents.repositories.entity;

import java.util.List;

/**
 * Moment page.
 * @author yangfeng
 */
public class Page {
    private boolean hasNext;
    private long nextMomentID;
    private List<Moment> moments;

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public long getNextMomentID() {
        return nextMomentID;
    }

    public void setNextMomentID(long nextMomentID) {
        this.nextMomentID = nextMomentID;
    }

    public List<Moment> getMoments() {
        return moments;
    }

    public void setMoments(List<Moment> moments) {
        this.moments = moments;
    }
}
