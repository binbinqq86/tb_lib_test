package com.tb.baselib.image;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by : tb on 2017/9/25 下午1:51.
 * Description :图片展示接口
 */
public interface IImageDisplay {
    /**
     * 加载网络图片
     *
     * @param mContext
     * @param imageView
     * @param url
     */
    void displayByUrl(Context mContext, ImageView imageView, String url);
}
