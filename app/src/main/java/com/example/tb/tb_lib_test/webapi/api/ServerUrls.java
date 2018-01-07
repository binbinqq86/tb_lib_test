package com.example.tb.tb_lib_test.webapi.api;

import com.example.tb.tb_lib_test.BuildConfig;
import com.tb.annotation.annotation.BaseApiUrl;
import com.tb.annotation.annotation.NoProguard;
import com.tb.annotation.api.InjectHelper;
import com.tb.baselib.constant.BaseConstant;

/**
 * api请求地址集合，统一放在此处
 *
 * @auther tb
 * @time 2017/12/7 下午3:59
 * @desc application中调用getInstance初始化服务器api前缀
 */
@BaseApiUrl(BuildConfig.TEST_API_URL + "/smart/")
@NoProguard
public class ServerUrls {
    private ServerUrls() {
//        InjectBaseApiUrl.injectBaseUrl(this);
        InjectHelper.inject(this);
    }

    public static ServerUrls getInstance() {
        return SingletonServerUrls.SINGLETON_SERVER_URLS;
    }

    private static final class SingletonServerUrls {
        private static final ServerUrls SINGLETON_SERVER_URLS = new ServerUrls();
    }

    /**
     * 注解专用，勿删！！！不能被混淆！！！（通过反射调用）(废弃，改用编译时注解)
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
    public static final String URL_TEST = "api/v1/dict";
    public static final String URL_TEST_1 = "api.cli/v1/client/device";

}
