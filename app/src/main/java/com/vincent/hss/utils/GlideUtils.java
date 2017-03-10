package com.vincent.hss.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.vincent.hss.activity.RoomDetailActivity;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/9 20:19
 *
 * @version 1.0
 */

public class GlideUtils {

    /**
     * 图片到控件的最大宽高
     * @param context
     * @param imageView
     * @param imgId
     */
    public static void loadingImgMax(final Context context, final ImageView imageView, final int imgId){
        Glide.with(context).load(imgId).asBitmap().into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                int imageWidth = resource.getWidth();
                int imageHeight = resource.getHeight();
                int height = ScreenUtils.getScreenWidth(context) * imageHeight / imageWidth;
                ViewGroup.LayoutParams para = imageView.getLayoutParams();
                para.height = height;
                imageView.setLayoutParams(para);
                Glide.with(context).load(imgId).asBitmap().into(imageView);
            }
        });
    }
    /**
     * 图片到控件的最大宽高
     * @param context
     * @param imageView
     * @param imgId
     */
    public static void loadingImgMax(final Context context, final ImageView imageView, final String imgId){
        Glide.with(context).load(imgId).asBitmap().into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                int imageWidth = resource.getWidth();
                int imageHeight = resource.getHeight();
                int height = ScreenUtils.getScreenWidth(context) /10*3;
                int width = ScreenUtils.getScreenWidth(context);
                ViewGroup.LayoutParams para = imageView.getLayoutParams();
                para.height = height;
                para.width =width;
                imageView.setLayoutParams(para);
                Glide.with(context).load(imgId).asBitmap().into(imageView);
            }
        });
    }
}
