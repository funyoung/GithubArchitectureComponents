package com.sogou.inputmethod.moment.ui.widgets;

import android.content.Context;
import android.net.Uri;

import com.sogou.inputmethod.moment.ui.others.GlideSimpleTarget;
import com.bumptech.glide.Glide;
import com.github.ielse.imagewatcher.ImageWatcher;

/**
 * @author yangfeng
 */
public class GlideImageWatcherLoader implements ImageWatcher.Loader {
    @Override
    public void load(Context context, Uri url, ImageWatcher.LoadCallback lc) {
        Glide.with(context).asBitmap().load(url).into(new GlideSimpleTarget(lc));
    }
}
