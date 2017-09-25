package com.tb.baselib.image;

import com.tb.baselib.image.impl.GlideImageLoader;

/**
 * Created by : tb on 2017/9/25 下午12:01.
 * Description :获取一个图片加载的单例工具类
 */
public class ImageLoaderUtil {
    private IImageDisplay iImageDisplay;
    
    private ImageLoaderUtil() {
    }
    
    /**
     * 设置具体的图片加载器策略
     *
     * @param iImageDisplay
     * @return
     */
    public ImageLoaderUtil setStrategy(IImageDisplay iImageDisplay) {
        this.iImageDisplay = iImageDisplay;
        return ImageSingletonHolder.instance;
    }
    
    /**
     * 默认为glide
     *
     * @return
     */
    public IImageDisplay getIImageDisplay() {
        return this.iImageDisplay == null ? GlideImageLoader.getInstance() : this.iImageDisplay;
    }
    
    public static final ImageLoaderUtil getInstance() {
        return ImageSingletonHolder.instance;
    }
    
    private static final class ImageSingletonHolder {
        private static final ImageLoaderUtil instance = new ImageLoaderUtil();
    }
}
