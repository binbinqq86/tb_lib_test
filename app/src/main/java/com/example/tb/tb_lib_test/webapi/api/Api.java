package com.example.tb.tb_lib_test.webapi.api;

import android.support.annotation.Keep;

import com.example.tb.tb_lib_test.BuildConfig;
import com.tb.baselib.annotation.baseapi.BaseApiUrl;
import com.tb.baselib.annotation.baseapi.InjectBaseApiUrl;
import com.tb.baselib.annotation.noproguard.NoProguard;
import com.tb.baselib.constant.BaseConstant;

/**
 * @auther tb
 * @time 2017/12/7 下午3:59
 * @desc 使用时必须先调用getInstance
 */
@BaseApiUrl(BuildConfig.TEST_API_URL + "/smart")
public class Api {
    private Api() {
        InjectBaseApiUrl.injectBaseUrl(this);
    }
    
    public static Api getInstance() {
        return SingletonApi.singletonApi;
    }
    
    private static final class SingletonApi {
        private static final Api singletonApi = new Api();
    }
    
    /**
     * 注解专用，勿删！！！不能被混淆！！！（通过反射调用）
     *
     * @param baseApiUrl
     */
    @NoProguard
    private void setBaseApiUrl(String baseApiUrl) {
        BaseConstant.BASE_API_URL = baseApiUrl;
    }
    
    /**
     * 测试api地址
     */
    public static final String URL_TEST = "/api/v1/dict";
}
