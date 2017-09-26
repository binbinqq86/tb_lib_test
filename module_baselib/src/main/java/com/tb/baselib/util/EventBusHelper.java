package com.tb.baselib.util;

import android.os.Message;

import org.greenrobot.eventbus.EventBus;

/**
 * EventBus帮助类
 */
public class EventBusHelper {
    
    private static EventBus mEventBus = EventBus.getDefault();
    
    public static void register(Object subscriber) {
        if (mEventBus == null)
            return;
        if (subscriber == null)
            return;
        mEventBus.register(subscriber);
    }
    
    public static void unregister(Object subscriber) {
        if (mEventBus == null)
            return;
        if (subscriber == null)
            return;
        mEventBus.unregister(subscriber);
    }
    
    public static void post(int what, Object obj) {
        if (mEventBus == null)
            return;
        if (obj == null)
            return;
        
        Message msg = Message.obtain();
        msg.what = what;
        msg.obj = obj;
        mEventBus.post(msg);
    }
    
    public static void post(int what) {
        post(what, new Object());
    }
}
