package com.boisneyphilippe.githubarchitecturecomponents.ui.others;

import android.content.Context;

import com.boisneyphilippe.githubarchitecturecomponents.Constants;
import com.boisneyphilippe.githubarchitecturecomponents.ui.beans.CircleHeaderBean;
import com.boisneyphilippe.githubarchitecturecomponents.ui.beans.CircleItemBean;
import com.boisneyphilippe.githubarchitecturecomponents.ui.beans.CircleMessageBean;
import com.boisneyphilippe.githubarchitecturecomponents.ui.beans.CirclePostBean;
import com.boisneyphilippe.githubarchitecturecomponents.ui.beans.CommentBean;
import com.boisneyphilippe.githubarchitecturecomponents.ui.beans.OtherInfoBean;
import com.boisneyphilippe.githubarchitecturecomponents.ui.beans.PraiseBean;
import com.boisneyphilippe.githubarchitecturecomponents.ui.beans.UserBean;
import com.boisneyphilippe.githubarchitecturecomponents.repositories.entity.Message;
import com.boisneyphilippe.githubarchitecturecomponents.repositories.entity.User;
import com.boisneyphilippe.githubarchitecturecomponents.utils.SpanUtils;

import java.util.ArrayList;
import java.util.List;

import static com.boisneyphilippe.githubarchitecturecomponents.Constants.FriendCircleType.FRIEND_CIRCLE_TYPE_ONLY_WORD;
import static com.boisneyphilippe.githubarchitecturecomponents.Constants.FriendCircleType.FRIEND_CIRCLE_TYPE_WORD_AND_IMAGES;
import static com.boisneyphilippe.githubarchitecturecomponents.Constants.FriendCircleType.FRIEND_CIRCLE_TYPE_WORD_AND_URL;


/**
 * @author KCrason
 * @date 2018/5/2
 */
public class DataCenter {
    public static List<CircleItemBean> makeFriendCircleBeans(Context context, User user) {
        List<CircleItemBean> circlePostBeans = new ArrayList<>();
        circlePostBeans.add(new CircleHeaderBean(user));
        Message message = new Message("msg_id", 6, user.getAvatar_url(), user.getName() + "来新消息了");
        circlePostBeans.add(new CircleMessageBean(message));

        for (int i = 0; i < 1000; i++) {
            final CirclePostBean circlePostBean;
            int randomValue = (int) (Math.random() * 300);
            if (randomValue < 100) {
                circlePostBean = new CirclePostBean(FRIEND_CIRCLE_TYPE_ONLY_WORD);
            } else if (randomValue < 200) {
                circlePostBean = new CirclePostBean(FRIEND_CIRCLE_TYPE_WORD_AND_IMAGES);
            } else {
                circlePostBean = new CirclePostBean(FRIEND_CIRCLE_TYPE_WORD_AND_URL);
            }
            circlePostBean.setCommentBeans(makeCommentBeans(context));
            circlePostBean.setImageUrls(makeImages());
            List<PraiseBean> praiseBeans = makePraiseBeans();
            circlePostBean.setPraiseSpan(SpanUtils.makePraiseSpan(context, praiseBeans));
            circlePostBean.setPraiseBeans(praiseBeans);
            circlePostBean.setContent(Constants.CONTENT[(int) (Math.random() * 10)]);

            UserBean userBean = new UserBean();
            userBean.setUserName(Constants.USER_NAME[(int) (Math.random() * 30)]);
            userBean.setUserAvatarUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1588931838505&di=80343c9fc5f894c801c8bfe76080809a&imgtype=0&src=http%3A%2F%2F01.minipic.eastday.com%2F20170319%2F20170319000016_0d58c8f901682ca78d62ee887d5b07cc_6.jpeg");
            circlePostBean.setUserBean(userBean);


            OtherInfoBean otherInfoBean = new OtherInfoBean();
            otherInfoBean.setTime(Constants.TIMES[(int) (Math.random() * 20)]);
            int random = (int) (Math.random() * 30);
            if (random < 20) {
                otherInfoBean.setSource(Constants.SOURCE[random]);
            } else {
                otherInfoBean.setSource("");
            }
            circlePostBean.setOtherInfoBean(otherInfoBean);
            circlePostBeans.add(circlePostBean);
        }
        return circlePostBeans;
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
