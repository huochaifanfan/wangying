package com.wytianxiatuan.wytianxia.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.overrideview.banner.loader.ImageLoader;

/**
 * Created by liuju on 2018/1/20.
 */

public class BannerImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }
}
