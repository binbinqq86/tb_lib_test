package com.tb.baselib.base.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;

import com.tb.baselib.util.EventBusHelper;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by : tb on 2017/9/30 下午4:30.
 * Description :注册eventBus，用不到eventBus无需继承此类，仍继承{@link BaseActivityWithViewStatus}即可
 */
public abstract class BasicActivity extends BaseActivityWithViewStatus {
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBusHelper.register(this);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusHelper.unregister(this);
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(Message msg) {
        
    }
}
