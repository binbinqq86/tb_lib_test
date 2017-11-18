package com.tb.baselib.util;

import android.os.CountDownTimer;
/**
 * @auther tb
 * @time 2017/11/18 下午3:01
 * @desc 计时器
*/
public class TimeCount extends CountDownTimer {
    private OnTimeListener mListener;
    private boolean mIsTiming = false;
    
    public interface OnTimeListener {
        void onTimerTick(long millisUntilFinished);
        
        void onTimeFinish();
    }
    
    /**
     *
     * @param millisInFuture 总时长
     * @param countDownInterval 计时的时间间隔
     * @param listener
     */
    public TimeCount(long millisInFuture, long countDownInterval, OnTimeListener listener) {
        super(millisInFuture, countDownInterval);
        mListener = listener;
    }
    
    public boolean isTiming() {
        return mIsTiming;
    }
    
    @Override
    public void onFinish() {// 计时完毕时触发
        mIsTiming = false;
        
        mListener.onTimeFinish();
    }
    
    @Override
    public void onTick(long millisUntilFinished) {// 计时过程显示
        mIsTiming = true;
        
        mListener.onTimerTick(millisUntilFinished);
    }
    
    public void cancelTimer(){
        cancel();
        mListener=null;
        mIsTiming=false;
    }
}
