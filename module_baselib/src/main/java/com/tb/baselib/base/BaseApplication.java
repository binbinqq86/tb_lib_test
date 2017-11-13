package com.tb.baselib.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.tb.baselib.BuildConfig;
import com.tb.baselib.util.LogUtils;
import com.tb.baselib.widget.ToastUtils;

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
        initComponent();
    }
    
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    
    private void initComponent(){
        //初始化日志
        LogUtils.init();
        //初始化toast
        ToastUtils.init();
        //ARouter
        if (BuildConfig.IS_DEBUG_MODE) {// 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();// 打印日志
            ARouter.openDebug();// 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this);// 尽可能早，推荐在Application中初始化
    }
}
