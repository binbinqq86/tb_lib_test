package com.tb.baselib.base;

import android.app.Application;

import com.tb.baselib.util.ToastUtils;

/**
 * Created by : tb on 2017/9/20 下午3:40.
 * Description :
 */
public class BaseApplication extends Application{
    public static BaseApplication application;
    @Override
    public void onCreate() {
        super.onCreate();
        application=this;
        ToastUtils.init();
    }
}
