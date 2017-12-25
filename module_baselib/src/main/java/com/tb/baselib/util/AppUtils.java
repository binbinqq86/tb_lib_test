package com.tb.baselib.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.tb.baselib.base.BaseApplication;

/**
 * @auther tb
 * @time 2017/12/25 上午11:18
 * @desc 获取跟app相关的信息
 */
public class AppUtils {
    private AppUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }
    
    /**
     * 获取应用程序名称
     */
    public static String getAppName() {
        try {
            PackageManager packageManager = BaseApplication.application.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    BaseApplication.application.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return BaseApplication.application.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 获取应用程序版本名称信息
     *
     * @return 当前应用的版本名称
     */
    public static String getAppVersionName() {
        try {
            PackageManager packageManager = BaseApplication.application.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    BaseApplication.application.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
