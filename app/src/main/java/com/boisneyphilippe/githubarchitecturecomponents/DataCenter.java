package com.boisneyphilippe.githubarchitecturecomponents;

import android.content.Context;

import com.boisneyphilippe.githubarchitecturecomponents.repositories.entity.Message;
import com.boisneyphilippe.githubarchitecturecomponents.repositories.entity.User;
import com.boisneyphilippe.githubarchitecturecomponents.ui.beans.CircleHeaderBean;
import com.boisneyphilippe.githubarchitecturecomponents.ui.beans.CircleItemBean;
import com.boisneyphilippe.githubarchitecturecomponents.ui.beans.CircleMessageBean;
import com.boisneyphilippe.githubarchitecturecomponents.ui.beans.CirclePostBean;
import com.boisneyphilippe.githubarchitecturecomponents.ui.beans.CommentBean;
import com.boisneyphilippe.githubarchitecturecomponents.ui.beans.OtherInfoBean;
import com.boisneyphilippe.githubarchitecturecomponents.ui.beans.PraiseBean;
import com.boisneyphilippe.githubarchitecturecomponents.ui.beans.UserBean;
import com.boisneyphilippe.githubarchitecturecomponents.utils.SpanUtils;

import java.util.ArrayList;
import java.util.List;

import static com.boisneyphilippe.githubarchitecturecomponents.ui.adapters.FriendCircleType.FRIEND_CIRCLE_TYPE_ONLY_WORD;
import static com.boisneyphilippe.githubarchitecturecomponents.ui.adapters.FriendCircleType.FRIEND_CIRCLE_TYPE_WORD_AND_IMAGES;
import static com.boisneyphilippe.githubarchitecturecomponents.ui.adapters.FriendCircleType.FRIEND_CIRCLE_TYPE_WORD_AND_URL;


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
            commentBean.setChildUserName(Constants.USER_NAME[(int) (Math.random() * 30)]);

            commentBean.setCommentContent(Constants.COMMENT_CONTENT[(int) (Math.random() * 30)]);
            commentBean.build(context);
            commentBeans.add(commentBean);
        }
        return commentBeans;
    }

    /**
     * @author KCrason
     * @date 2018/4/27
     */
    public static class Constants {
        public final static String[] USER_NAME = {
                "光速", "出单队", "精英队", "精英", "风启",
                "野狼盟", "篮球人", "秃鸡队", "奔驰财团", "永创第一",
                "胜羽队", "巨人队", "同路人", "18新秀", "奇迹",
                "无敌队", "给力", "天一", "飞虎队", "超越队",
                "NIKE队", "飞跃", "农夫山泉队", "兄弟盟", "完美之神",
                "百事队", "风之彩", "美特斯邦威", "我们", "跃起动力",
        };

        public final static String[] TIMES = {
                "1分钟前", "5分钟前", "10分钟前", "20分钟前", "1小时前",
                "2小时前", "3小时前", "4小时前", "4小时前", "5小时前",
                "6小时前", "7小时前", "8小时前", "9小时前", "10小时前",
                "11小时前", "一天前", "两天前", "三天前", "五天前",
                "十天前", "半个月前", "一个月前", "两个月前", "五个月前",
                "8个月前", "一年前", "二年前", "五年前", "10年前",
        };

        public final static String[] SOURCE = {
                "微信", "今日头条", "QQ", "京东", "百度",
                "阿里巴巴", "微博", "淘宝", "抖音", "内涵段子",
                "快手", "优酷", "爱奇艺", "天猫", "腾讯视频",
                "微视", "大智慧", "同花顺", "第五人格", "陌陌",
                "探探", "QQ斗地主", "大吉大利，今晚吃鸡", "Wifi万能钥匙", "QQ音乐",
                "网易云音乐", "酷狗音乐", "墨迹天气", "百度地图", "天天快报",
        };

        public final static String[] CONTENT = {
                "土地是以它的肥沃和收获而被估价的；才能也是土地，不过它生产的不是粮食，而是真理。如果只能滋生瞑想和幻想的话，即使再大的才能也只是砂地或盐池，那上面连小草也长不出来的。",
                "我需要三件东西：爱情友谊和图书。然而这三者之间何其相通！炽热的爱情可以充实图书的内容，图书又是人们最忠实的朋友。",
                "人生的磨难是很多的，所以我们不可对于每一件轻微的伤害都过于敏感。在生活磨难面前，精神上的坚强和无动于衷是我们抵抗罪恶和人生意外的最好武器",
                "爱情只有当它是自由自在时，才会叶茂花繁。认为爱情是某种义务的思想只能置爱情于死地。只消一句话：你应当爱某个人，就足以使你对这个人恨之入骨。",
                "温顺的青年人在图书馆里长大，他们相信他们的责任是应当接受西塞罗，洛克，培根发表的意见；他们忘了西塞罗，洛克与培根写这些书的时候，也不过是在图书馆里的青年人。",
                "较高级复杂的劳动，是这样一种劳动力的表现，这种劳动力比较普通的劳动力需要较高的教育费用，它的生产需要花费较多的劳动时间。因此，具有较高的价值。",
                "父亲子女兄弟姊妹等称谓，并不是简单的荣誉称号，而是一种负有完全确定的异常郑重的相互义务的称呼，这些义务的总和便构成这些民族的社会制度的实质部分。",
                "世界上没有才能的人是没有的。问题在于教育者要去发现每一位学生的禀赋、兴趣、爱好和特长，为他们的表现和发展提供充分的条件和正确引导。",
                "在人类历史的长河中，真理因为像黄金一样重，总是沉于河底而很难被人发现，相反地，那些牛粪一样轻的谬误倒漂浮在上面到处泛滥。",
                "要永远觉得祖国的土地是稳固地在你脚下，要与集体一起生活，要记住，是集体教育了你。那一天你若和集体脱离，那便是末路的开始。"
        };

        public final static String[] COMMENT_CONTENT = {
                "在强者的眼中，没有最好，只有更好。", "盆景秀木正因为被人溺爱，才破灭了成为栋梁之材的梦。",
                "永远都不要放弃自己，勇往直前，直至成功！", "没有平日的失败，就没有最终的成功。重要的是分析失败原因并吸取教训。",
                "蝴蝶如要在百花园里得到飞舞的欢乐，那首先得忍受与蛹决裂的痛苦。",
                "萤火虫的光点虽然微弱，但亮着便是向黑暗挑战。", "面对人生旅途中的挫折与磨难，我们不仅要有勇气，更要有坚强的信念。",
                "对坚强的人来说，不幸就像铁犁一样开垦着他内心的大地，虽然痛，却可以播种。", "想而奋进的过程，其意义远大于未知的结果。",
                "上有天，下有地，中间站着你自己，做一天人，尽一天人事儿。",
                "努力向上的开拓，才使弯曲的竹鞭化作了笔直的毛竹。", "只要你确信自己正确就去做。做了有人说不好，不做还是有人说不好，不要逃避批判。",
                "对没志气的人，路程显得远；对没有银钱的人，城镇显得远。", "生命力的意义在于拼搏，因为世界本身就是一个竞技场。",
                "通过云端的道路，只亲吻攀登者的足迹。", "不经巨大的困难，不会有伟大的事业。",
                "泉水，奋斗之路越曲折，心灵越纯洁。", "人的一生，可以有所作为的时机只有一次，那就是现在。",
                "对于攀登者来说，失掉往昔的足迹并不可惜，迷失了继续前时的方向却很危险。", "生命的道路上永远没有捷径可言，只有脚踏实地走下去。",
                "时间顺流而下，生活逆水行舟。", "一个人，只要知道付出爱与关心，她内心自然会被爱与关心充满。",
                "你要求的次数愈多，你就越容易得到你要的东西，而且连带地也会得到更多乐趣。", "有大快乐的人，必有大哀痛；有大成功的人，必有大孤独。",
                "成功等于目标，其他都是这句话的注解。", "生命之长短殊不重要，只要你活得快乐，在有生之年做些有意义的事，便已足够。",
                "路在自己脚下，没有人可以决定我的方向。", "命运是不存在的，它不过是失败者拿来逃避现实的借口。",
                "生命太过短暂，今天放弃了明天不一定能得到。", "改变自我，挑战自我，从现在开始。",
        };

    }
}