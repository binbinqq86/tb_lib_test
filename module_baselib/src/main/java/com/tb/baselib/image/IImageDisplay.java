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
     * @param mContext 不要传递Application
     * @param imageView 要显示图片的控件
     * @param param 可以为url,file,uri,resourceId,byte[]...
     */
    void displayWith(Context mContext, ImageView imageView, Object param);
}
