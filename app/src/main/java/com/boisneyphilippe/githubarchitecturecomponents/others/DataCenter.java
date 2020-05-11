package com.boisneyphilippe.githubarchitecturecomponents.others;

import android.content.Context;

import com.boisneyphilippe.githubarchitecturecomponents.Constants;
import com.boisneyphilippe.githubarchitecturecomponents.beans.CommentBean;
import com.boisneyphilippe.githubarchitecturecomponents.beans.FriendCircleBean;
import com.boisneyphilippe.githubarchitecturecomponents.beans.OtherInfoBean;
import com.boisneyphilippe.githubarchitecturecomponents.beans.PraiseBean;
import com.boisneyphilippe.githubarchitecturecomponents.beans.UserBean;
import com.boisneyphilippe.githubarchitecturecomponents.utils.SpanUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * @author KCrason
 * @date 2018/5/2
 */
public class DataCenter {
    public static List<FriendCircleBean> makeFriendCircleBeans(Context context) {
        List<FriendCircleBean> friendCircleBeans = new ArrayList<>();
        // todo: add header and message.
        FriendCircleBean header = new FriendCircleBean();
        header.setViewType(Constants.FriendCircleType.FRIEND_CIRCLE_TYPE_HEADER);
        friendCircleBeans.add(header);
        FriendCircleBean message = new FriendCircleBean();
        message.setViewType(Constants.FriendCircleType.FRIEND_CIRCLE_TYPE_MESSAGE);
        friendCircleBeans.add(message);

        for (int i = 0; i < 1000; i++) {
            FriendCircleBean friendCircleBean = new FriendCircleBean();
            int randomValue = (int) (Math.random() * 300);
            if (randomValue < 100) {
                friendCircleBean.setViewType(Constants.FriendCircleType.FRIEND_CIRCLE_TYPE_ONLY_WORD);
            } else if (randomValue < 200) {
                friendCircleBean.setViewType(Constants.FriendCircleType.FRIEND_CIRCLE_TYPE_WORD_AND_IMAGES);
            } else {
                friendCircleBean.setViewType(Constants.FriendCircleType.FRIEND_CIRCLE_TYPE_WORD_AND_URL);
            }
            friendCircleBean.setCommentBeans(makeCommentBeans(context));
            friendCircleBean.setImageUrls(makeImages());
            List<PraiseBean> praiseBeans = makePraiseBeans();
            friendCircleBean.setPraiseSpan(SpanUtils.makePraiseSpan(context, praiseBeans));
            friendCircleBean.setPraiseBeans(praiseBeans);
            friendCircleBean.setContent(Constants.CONTENT[(int) (Math.random() * 10)]);

            UserBean userBean = new UserBean();
            userBean.setUserName(Constants.USER_NAME[(int) (Math.random() * 30)]);
            userBean.setUserAvatarUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1588931838505&di=80343c9fc5f894c801c8bfe76080809a&imgtype=0&src=http%3A%2F%2F01.minipic.eastday.com%2F20170319%2F20170319000016_0d58c8f901682ca78d62ee887d5b07cc_6.jpeg");
            friendCircleBean.setUserBean(userBean);


            OtherInfoBean otherInfoBean = new OtherInfoBean();
            otherInfoBean.setTime(Constants.TIMES[(int) (Math.random() * 20)]);
            int random = (int) (Math.random() * 30);
            if (random < 20) {
                otherInfoBean.setSource(Constants.SOURCE[random]);
            } else {
                otherInfoBean.setSource("");
            }
            friendCircleBean.setOtherInfoBean(otherInfoBean);
            friendCircleBeans.add(friendCircleBean);
        }
        return friendCircleBeans;
    }


    private static List<String> makeImages() {
        List<String> imageBeans = new ArrayList<>();
        int randomCount = (int) (Math.random() * 9);
        if (randomCount == 0) {
            randomCount = randomCount + 1;
        } else if (randomCount == 8) {
            randomCount = randomCount + 1;
        }
        for (int i = 0; i < randomCount; i++) {
            imageBeans.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1588931838505&di=80343c9fc5f894c801c8bfe76080809a&imgtype=0&src=http%3A%2F%2F01.minipic.eastday.com%2F20170319%2F20170319000016_0d58c8f901682ca78d62ee887d5b07cc_6.jpeg");
        }
        return imageBeans;
    }


    private static List<PraiseBean> makePraiseBeans() {
        List<PraiseBean> praiseBeans = new ArrayList<>();
        int randomCount = (int) (Math.random() * 20);
        for (int i = 0; i < randomCount; i++) {
            PraiseBean praiseBean = new PraiseBean();
            praiseBean.setPraiseUserName(Constants.USER_NAME[(int) (Math.random() * 30)]);
            praiseBeans.add(praiseBean);
        }
        return praiseBeans;
    }


    private static List<CommentBean> makeCommentBeans(Context context) {
        List<CommentBean> commentBeans = new ArrayList<>();
        int randomCount = (int) (Math.random() * 20);
        for (int i = 0; i < randomCount; i++) {
            CommentBean commentBean = new CommentBean();
            if ((int) (Math.random() * 100) % 2 == 0) {
                commentBean.setCommentType(Constants.CommentType.COMMENT_TYPE_SINGLE);
                commentBean.setChildUserName(Constants.USER_NAME[(int) (Math.random() * 30)]);
            } else {
                commentBean.setCommentType(Constants.CommentType.COMMENT_TYPE_REPLY);
                commentBean.setChildUserName(Constants.USER_NAME[(int) (Math.random() * 30)]);
                commentBean.setParentUserName(Constants.USER_NAME[(int) (Math.random() * 30)]);
            }

            commentBean.setCommentContent(Constants.COMMENT_CONTENT[(int) (Math.random() * 30)]);
            commentBean.build(context);
            commentBeans.add(commentBean);
        }
        return commentBeans;
    }
}
