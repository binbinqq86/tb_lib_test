package com.tb.baselib.manager;

import android.app.Activity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.tb.baselib.constant.ARouterPath;

/**
 * Created by : tb on 2017/9/26 下午3:23.
 * Description :activity跳转
 */
public class ActivityLauncher {
    /**
     * 检查当前页面是否可用：false-不可用，true-可用
     * @param activity
     * @return
     */
    private static boolean checkActivity(Activity activity){
        if(activity==null){
            return false;
        }
        return !activity.isFinishing();
    }
    
    /**
     * 测试跳转
     * @param activity
     */
    public static void test(Activity activity){
        if(!checkActivity(activity)){
            return;
        }
        ARouter.getInstance().build(ARouterPath.TEST).navigation(activity);
    }
}
