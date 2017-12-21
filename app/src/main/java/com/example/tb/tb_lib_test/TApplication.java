package com.example.tb.tb_lib_test;

import com.example.tb.tb_lib_test.webapi.api.Api;
import com.tb.baselib.base.BaseApplication;

/**
 * Created by : tb on 2017/9/20 下午3:32.
 * Description :
 */
public class TApplication extends BaseApplication{
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }
    
    private void init() {
        /**
         * 初始化服务器API前缀
         */
        Api.getInstance();
    }
}
