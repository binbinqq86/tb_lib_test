package com.tb.baselib.mvp.model;

import com.tb.baselib.interfaces.OnDownloadFile;
import com.tb.baselib.net.BaseResponse;
import com.tb.baselib.net.interfaces.OnRequestCallback;

import java.lang.reflect.Type;

/**
 * Created by : tb on 2017/9/21 上午11:37.
 * Description :公共请求接口，具体实现则由具体第三方框架完成(可自由更换网络请求框架)
 * mvp之model---负责数据的处理
 */
public interface IBaseModel {

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
            , final Class cls
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
            , final Class cls
            , final Object param
            , final OnRequestCallback callback
            , final long timeout
    );

    /**
     * get请求
     *
     * @param requestCode 请求code
     * @param url         请求地址
     * @param cls         服务器返回真实数据类型
     * @param callback    回调监听
     */
    void get(final int requestCode
            , final String url
            , final Class cls
            , final OnRequestCallback callback);


    /**
     * get请求
     *
     * @param requestCode 请求code
     * @param url         请求地址
     * @param cls         服务器返回真实数据类型
     * @param callback    回调监听
     * @param timeout     超时时间
     */
    void get(final int requestCode
            , final String url
            , final Class cls
            , final OnRequestCallback callback
            , final long timeout);

    /**
     * delete请求
     *
     * @param requestCode 请求code
     * @param url         请求地址
     * @param cls         服务器返回真实数据类型
     * @param param       请求参数
     * @param callback    回调监听
     */
    void delete(final int requestCode
            , final String url
            , final Class cls
            , final Object param
            , final OnRequestCallback callback
    );

    /**
     * delete请求
     *
     * @param requestCode 请求code
     * @param url         请求地址
     * @param cls         服务器返回真实数据类型
     * @param param       请求参数
     * @param callback    回调监听
     * @param timeout     超时时间
     */
    void delete(final int requestCode
            , final String url
            , final Class cls
            , final Object param
            , final OnRequestCallback callback
            , final long timeout
    );

    /**
     * put请求
     *
     * @param requestCode 请求code
     * @param url         请求地址
     * @param cls         服务器返回真实数据类型
     * @param param       请求参数
     * @param callback    回调监听
     */
    void put(final int requestCode
            , final String url
            , final Class cls
            , final Object param
            , final OnRequestCallback callback
    );

    /**
     * put请求
     *
     * @param requestCode 请求code
     * @param url         请求地址
     * @param cls         服务器返回真实数据类型
     * @param param       请求参数
     * @param callback    回调监听
     * @param timeout     超时时间
     */
    void put(final int requestCode
            , final String url
            , final Class cls
            , final Object param
            , final OnRequestCallback callback
            , final long timeout
    );

    /**
     * 下载文件(一般为get)
     *
     * @param url            请求地址
     * @param onDownloadFile 文件下载回调监听
     */
    void downLoadFile(String url, OnDownloadFile onDownloadFile);
}
