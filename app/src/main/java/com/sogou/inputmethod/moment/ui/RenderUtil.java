package com.sogou.inputmethod.moment.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

/**
 * @author yangfeng
 */
public class RenderUtil {
    private RenderUtil() {
    }

    /**
     * 加载图片到ImageView, 给定加载出错和占位的本地图片，最后显示的图片显示为圆角。
     * @param requestManager
     * @param imageView
     * @param url
     * @param placeholderId
     * @param radiusId
     */
    public static void rounding(RequestManager requestManager, ImageView imageView, String url, @DrawableRes int placeholderId, @DimenRes int radiusId) {
        RequestBuilder requestBuilder = requestManager.load(url)
                .error(placeholderId).placeholder(placeholderId);

        if (radiusId != 0) {
            int radius = imageView.getResources().getDimensionPixelOffset(radiusId);
            BitmapTransformation transformation = new RoundedCorners(radius);
            requestBuilder.apply(RequestOptions.bitmapTransform(transformation))
                    .thumbnail(loadTransform(imageView.getContext(), placeholderId, transformation));
        }

        requestBuilder.into(imageView);
    }

    private static RequestBuilder<Drawable> loadTransform(Context context, @DrawableRes int placeholderId, BitmapTransformation transformation) {
        return Glide.with(context)
                .load(placeholderId)
                .apply(new RequestOptions().centerCrop().transform(transformation));

    }
}
