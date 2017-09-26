package com.tb.baselib.net.interfaces;

import com.tb.baselib.net.BaseResponse;

import java.lang.reflect.Type;

/**
 * Created by : tb on 2017/9/21 上午11:37.
 * Description :公共请求接口，具体实现则由具体第三方框架完成(可自由更换网络请求框架)
 */
public interface IApiRequester {
    
    /**
     * post请求
     *
     * @param requestCode 请求code
     * @param url         请求地址
     * @param cls         服务器返回真实数据类型
     * @param param       请求参数
     * @param callback    回调监听
     */
    void post(final int requestCode
            , final String url
            , final Type cls
            , final Object param
            , final OnRequestCallback callback
    );
    
    /**
     * post请求
     *
     * @param requestCode 请求code
     * @param url         请求地址
     * @param cls         服务器返回真实数据类型
     * @param param       请求参数
     * @param callback    回调监听
     * @param timeout     超时时间
     */
    void post(final int requestCode
            , final String url
            , final Type cls
            , final Object param
            , final OnRequestCallback callback
            , final long timeout
    );
}
