package com.sogou.inputmethod.moment.ui;

import android.content.Context;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.boisneyphilippe.githubarchitecturecomponents.R;
import com.github.ielse.imagewatcher.ImageWatcher;
import com.sogou.inputmethod.moment.utils.Utils;

import java.util.List;

public class DotIndexProvider implements ImageWatcher.IndexProvider {
    private boolean initLayout;
    private IndicatorView indicatorView;

    @Override
    public View initialView(Context context) {
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        indicatorView = new IndicatorView(context);
        indicatorView.setLayoutParams(lp);

        DisplayMetrics d = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(d);
        int size = (int) (20 * d.density + 0.5);
        lp.setMargins(0, 0, 0, Utils.getBottomKeyboardHeight() + size);

        initLayout = false;
        return indicatorView;
    }

    @Override
    public void onPageChanged(ImageWatcher imageWatcher, int position, List<Uri> dataList) {
        indicatorView.select(dataList.size(), position, R.drawable.b_gray_dcdcdc_oval, R.drawable.b_yellow_ffb100_oval, initLayout);
    }
}
