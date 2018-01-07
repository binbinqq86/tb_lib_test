package com.tb.baselib.image.impl;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tb.baselib.image.IImageDisplay;

/**
 * Created by : tb on 2017/9/25 下午2:05.
 * Description :用来加载图片的工具类，可替换其他第三方
 */
public class GlideImageLoader implements IImageDisplay {
    private GlideImageLoader() {
    }
    
    public static final GlideImageLoader getInstance() {
        return GlideSingletonHolder.instance;
    }
    
    private static final class GlideSingletonHolder {
        
        private static final GlideImageLoader instance = new GlideImageLoader();
    }
    
    @Override
    public void displayWith(Context mContext, ImageView imageView, Object param) {
        Glide.with(mContext).load(param)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }
    
}
