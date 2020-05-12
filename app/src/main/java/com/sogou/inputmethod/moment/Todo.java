package com.sogou.inputmethod.moment;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * @author yangfeng
 */
public class Todo {
    // todo: 点击消息跳转初始化为0
    public static final int DEFAULT_MESSAGE_OFFSET = 10;

    private Todo() {
    }

    /* pending checklist
      1. 替换默认封面大图，circle_header_cover
      2. 替换默认头像图，circle_header_avatar
      3.
     */

    public static void showToast(Context context, @StringRes int textId) {
        Toast.makeText(context, textId, Toast.LENGTH_LONG).show();
    }

    public static void onMessageClicked(long id) {
        // todo: 发网络请求，告知服务器消息已读
    }
}
