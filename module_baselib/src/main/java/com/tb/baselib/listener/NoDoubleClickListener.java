package com.tb.baselib.listener;

import android.view.View;

import java.util.Calendar;

public abstract class NoDoubleClickListener implements View.OnClickListener {
    //两次点击事件的最小事件间隔,间隔内的点击不反应
    public static final int MIN_CLICK_DELAY_TIME = 1000;
    //最近一次有效的按钮点击时刻
    private long mLastClickTime = 0;
    
    /**
     * 有效点击事件需要执行的逻辑
     *
     * @param view
     */
    public abstract void onNoDoubleClick(View view);
    
    @Override
    public void onClick(View view) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - mLastClickTime > MIN_CLICK_DELAY_TIME) {
            mLastClickTime = currentTime;
            onNoDoubleClick(view);
        }
    }
}