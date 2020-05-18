package com.sogou.inputmethod.moment.utils;

import android.content.Context;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupMenu;

import com.boisneyphilippe.githubarchitecturecomponents.R;
import com.sogou.inputmethod.moment.App;
import com.sogou.inputmethod.moment.ui.interfaces.OnItemClickPopupMenuListener;

import java.lang.reflect.Method;

/**
 * @author KCrason
 * @date 2018/5/6
 */
public class Utils {

    public static int dp2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, App.context.getResources().getDisplayMetrics());
    }

    public static int getScreenWidth() {
        return App.context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(){
        return App.context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int calcStatusBarHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    public static boolean calculateShowCheckAllText(String content) {
        Paint textPaint = new Paint();
        textPaint.setTextSize(Utils.dp2px(16f));
        float textWidth = textPaint.measureText(content);
        float maxContentViewWidth = Utils.getScreenWidth() - Utils.dp2px(74f);
        float maxLines = textWidth / maxContentViewWidth;
        return maxLines > 4;
    }

    public static void showPopupMenu(Context context, OnItemClickPopupMenuListener onItemClickPopupMenuListener, int position, View view) {
        PopupMenu popup = new PopupMenu(context, view);
        popup.getMenuInflater().inflate(R.menu.popup_menu_start, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.copy:
                    if (onItemClickPopupMenuListener != null) {
                        onItemClickPopupMenuListener.onItemClickCopy(position);
                    }
                    break;
                case R.id.collection:
                    if (onItemClickPopupMenuListener != null) {
                        onItemClickPopupMenuListener.onItemClickCollection(position);
                    }
                    break;
                default:
                    break;
            }
            return true;
        });
        popup.show(); //showing popup menu
    }

    public static int getBottomKeyboardHeight(){
        int screenHeight =  getAccurateScreenDpi()[1];
        DisplayMetrics dm = App.context.getResources().getDisplayMetrics();
        int heightDifference = screenHeight - dm.heightPixels;
        return heightDifference;
    }

    public static int[] getAccurateScreenDpi()
    {
        int[] screenWH = new int[2];
        WindowManager windowManager = (WindowManager) App.context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            Class<?> c = Class.forName("android.view.Display");
            Method method = c.getMethod("getRealMetrics",DisplayMetrics.class);
            method.invoke(display, dm);
            screenWH[0] = dm.widthPixels;
            screenWH[1] = dm.heightPixels;
        }catch(Exception e){
            e.printStackTrace();
        }
        return screenWH;
    }
}
