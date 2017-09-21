package com.tb.baselib.util;

import android.content.Context;
import android.util.TypedValue;

import com.tb.baselib.base.BaseApplication;

/**
 * Created by : tb on 2017/9/20 下午3:36.
 * Description :普通的工具类，无法归类的都写到这里
 */
public class CommonUtils {
    /**
     * 获取dp
     * @param dp
     * @return
     */
    public static float getDP(float dp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp, BaseApplication.application.getResources().getDisplayMetrics());
    }
}
