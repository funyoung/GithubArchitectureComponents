package com.sogou.inputmethod.moment.ui.beans;

import android.content.Context;
import android.text.SpannableStringBuilder;

import com.sogou.inputmethod.moment.utils.SpanUtils;

/**
 * @author yangfeng
 */
public class CommentBean {

    private String childUserName;

    private int parentUserId;

    private int childUserId;

    private String commentContent;

    public String getChildUserName() {
        return childUserName;
    }

    public void setChildUserName(String childUserName) {
        this.childUserName = childUserName;
    }

    public int getParentUserId() {
        return parentUserId;
    }

    public void setParentUserId(int parentUserId) {
        this.parentUserId = parentUserId;
    }

    public int getChildUserId() {
        return childUserId;
    }

    public void setChildUserId(int childUserId) {
        this.childUserId = childUserId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }


    /**
     * 富文本内容
     */
    private SpannableStringBuilder commentContentSpan;

    public SpannableStringBuilder getCommentContentSpan() {
        return commentContentSpan;
    }

    public void build(Context context) {
        commentContentSpan = SpanUtils.makeSingleCommentSpan(context, childUserName, commentContent);
    }
}
