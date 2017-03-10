package com.vincent.hss.utils;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.loader.ImageLoader;
import com.vincent.hss.R;

import java.io.File;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/9 16:02
 *
 * @version 1.0
 */

public class PicassoImageLoader implements ImageLoader {

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        /*Picasso.with(activity)//
                .load(new File(path))//
                .placeholder(R.mipmap.default_image)//
                .error(R.mipmap.default_image)//
                .resize(width, height)//
                .centerInside()//
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)//
                .into(imageView);*/
        Glide.with(activity)
                .load(new File(path))
                .placeholder(R.drawable.common_icon_placeholder)
                .error(R.mipmap.default_image)
                .override(width,height)
                .skipMemoryCache(false)
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {
        //这里是清除缓存的方法,根据需要自己实现
    }
}