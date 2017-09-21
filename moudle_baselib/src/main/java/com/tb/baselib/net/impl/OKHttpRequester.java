package com.tb.baselib.net.impl;

import com.tb.baselib.net.interfaces.IApiRequester;
import com.tb.baselib.net.interfaces.OnRequestCallback;

import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by : tb on 2017/9/21 上午11:52.
 * Description :OKHttp实现的具体的请求任务类
 */
public class OKHttpRequester implements IApiRequester {
    private static final long DEFAULT_TIMEOUT = 10 * 1000;
    /**
     * 采用线程安全的hashTable
     * K: 超时时间
     * V: 对应的OkHttpClient实例
     */
    private Map<Long, OkHttpClient> mInstanceMap = new Hashtable<>();
    
    private OkHttpClient getInstance(long timeout) {
        if (mInstanceMap.containsKey(new Long(timeout))) {
            return mInstanceMap.get(new Long(timeout));
        } else {
            //https相关内容
//            SSLSocketFactory[] socketFactory = new SSLSocketFactory[1];
//            X509TrustManager[] trustManager = new X509TrustManager[1];
//            SSLCertificatesInit.init(socketFactory, trustManager, CertificatesManager.getPayCerInputStream());
            OkHttpClient client = new OkHttpClient()
                    .newBuilder()
//                    .sslSocketFactory(socketFactory[0], trustManager[0])
                    .connectTimeout(timeout, TimeUnit.MILLISECONDS)
                    .writeTimeout(timeout, TimeUnit.MILLISECONDS)
                    .readTimeout(timeout, TimeUnit.MILLISECONDS)
                    .build();
            mInstanceMap.put(new Long(timeout), client);
            return client;
        }
    }
    
    @Override
    public void post(int requestCode, String url, Class<?> bean, Object param, OnRequestCallback listener) {
        this.post(requestCode, url, bean, param, listener, DEFAULT_TIMEOUT);
    }
    
    @Override
    public void post(int requestCode, String url, Class<?> bean, Object param, OnRequestCallback listener, long timeout) {
        
    }
}
