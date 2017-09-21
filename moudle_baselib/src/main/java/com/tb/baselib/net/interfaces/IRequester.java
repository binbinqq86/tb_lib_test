package com.tb.baselib.net.interfaces;

import java.lang.reflect.Type;

/**
 * Created by : tb on 2017/9/21 上午11:37.
 * Description :公共请求接口，具体实现则由具体第三方框架完成(可自由更换网络请求框架)
 */
public interface IRequester {
    
    /**
     * post请求
     *
     * @param requestCode 请求code
     * @param url         请求地址
     * @param base        基类
     * @param data        data类型数据
     * @param param       请求参数
     * @param listener    回调监听
     */
    void post(final int requestCode
            , final String url
            , final Class<?> base
            , final Type data
            , final Object param
            , final OnRequestCallback listener
    );
    
    /**
     * post请求
     *
     * @param requestCode 请求code
     * @param url         请求地址
     * @param base        基类
     * @param data        data类型数据
     * @param param       请求参数
     * @param listener    回调监听
     * @param timeout     超时时间
     */
    void post(final int requestCode
            , final String url
            , final Class<?> base
            , final Type data
            , final Object param
            , final OnRequestCallback listener
            , final long timeout
    );
    
    /**
     * get请求
     *
     * @param requestCode 请求code
     * @param url         请求地址
     * @param cls         异步请求实体类
     * @param listener    回调监听
     */
    void get(final int requestCode
            , final String url
            , final Class cls
            , final OnRequestCallback listener
    );
}
