package com.tb.baselib.net.impl;

import android.os.Handler;
import android.os.Looper;

import com.tb.baselib.constant.BaseConstant;
import com.tb.baselib.constant.ExceptionCode;
import com.tb.baselib.json.JsonUtil;
import com.tb.baselib.net.BaseResponse;
import com.tb.baselib.mvp.model.IBaseModel;
import com.tb.baselib.net.interfaces.OnRequestCallback;
import com.tb.baselib.util.LogUtils;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.reflect.Type;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by : tb on 2017/9/21 上午11:52.
 * Description :OKHttp实现的具体的请求任务类
 */
public class OKHttpRequester implements IBaseModel {
    private static final String TAG = "OKHttpRequester";
    private static final Handler mHandler = new Handler(Looper.getMainLooper());
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String CONTENT_TYPE = "application/json";
    private static final String DEBUG_FORMAT = "RESP CODE: %1$s, RESQ CODE %2$s, JSON:%3$s, EXCEPTION:%4$s";
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
    
    private OKHttpRequester() {
    }
    
    public static final OKHttpRequester getInstance() {
        return OKHttpSingletonHolder.instance;
    }
    
    private static final class OKHttpSingletonHolder {
        private static final OKHttpRequester instance = new OKHttpRequester();
    }
    
    @Override
    public void post(int requestCode, String url, final Type cls, Object param, OnRequestCallback callback) {
        this.post(requestCode, url, cls, param, callback, BaseConstant.HTTP_DEFAULT_TIME_OUT);
    }
    
    @Override
    public void post(final int requestCode, String url, final Type cls, Object param, final OnRequestCallback callback, long timeout) {
        doRequest(BaseConstant.POST, requestCode, url, cls, param, callback, timeout);
    }
    
    @Override
    public void get(int requestCode, String url, Type cls, OnRequestCallback callback) {
        this.get(requestCode, url, cls, callback, BaseConstant.HTTP_DEFAULT_TIME_OUT);
    }
    
    @Override
    public void get(int requestCode, String url, Type cls, OnRequestCallback callback, long timeout) {
        doRequest(BaseConstant.GET, requestCode, url, cls, null, callback, timeout);
    }
    
    @Override
    public void delete(int requestCode, String url, Type cls, Object param, OnRequestCallback callback) {
        this.delete(requestCode, url, cls, param, callback, BaseConstant.HTTP_DEFAULT_TIME_OUT);
    }
    
    @Override
    public void delete(int requestCode, String url, Type cls, Object param, OnRequestCallback callback, long timeout) {
        doRequest(BaseConstant.DELETE, requestCode, url, cls, param, callback, timeout);
    }
    
    @Override
    public void put(int requestCode, String url, Type cls, Object param, OnRequestCallback callback) {
        this.put(requestCode, url, cls, param, callback, BaseConstant.HTTP_DEFAULT_TIME_OUT);
    }
    
    @Override
    public void put(int requestCode, String url, Type cls, Object param, OnRequestCallback callback, long timeout) {
        doRequest(BaseConstant.PUT, requestCode, url, cls, param, callback, timeout);
    }
    
    private void doRequest(final String type, final int requestCode, String url, final Type cls, Object param, final OnRequestCallback callback, long timeout) {
        try {
            Request.Builder builder = new Request.Builder()
                    .url(BaseConstant.BASE_API_URL + url)
                    .addHeader("content-type", CONTENT_TYPE);
            if (param != null) {
                String jsonParam = JsonUtil.getInstance().getJsonUtil().toJson(param);
                RequestBody requestBody = RequestBody.create(JSON, jsonParam);
                LogUtils.json(jsonParam);
                if (type.equalsIgnoreCase(BaseConstant.POST)) {
                    builder.post(requestBody);
                } else if (type.equalsIgnoreCase(BaseConstant.PUT)) {
                    builder.put(requestBody);
                } else if (type.equalsIgnoreCase(BaseConstant.DELETE)) {
                    builder.delete(requestBody);
                }
            }
            Request request = builder.build();
            LogUtils.d("method:" + type + "===" + url);
            getInstance(timeout).newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(final Call call, final IOException e) {
                    try {
                        if (call.request().body() != null) {
                            LogUtils.d(String.format(DEBUG_FORMAT, String.valueOf("0"), String.valueOf(requestCode), String.valueOf(call.request().body().toString()), ""));
                        }
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (callback != null) {
                                    try {
                                        callback.onFailure(ExceptionCode.NO_INTERNET, requestCode, "数据请求失败");
                                    } catch (Exception e) {
                                        LogUtils.d(String.format(DEBUG_FORMAT, String.valueOf("0"), String.valueOf(requestCode), "", e.getMessage()));
                                    }
                                }
                            }
                        });
                    } catch (final Exception e1) {
                        LogUtils.d(String.format(DEBUG_FORMAT, String.valueOf("-1"), String.valueOf(requestCode), String.valueOf(e1.getMessage()), ""));
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (callback != null) {
                                    try {
                                        callback.onFailure(ExceptionCode.THROW_EXCEPTION, requestCode, "数据请求失败");
                                    } catch (Exception e) {
                                        LogUtils.d(String.format(DEBUG_FORMAT, String.valueOf("0"), String.valueOf(requestCode), "", e.getMessage()));
                                    }
                                }
                            }
                        });
                    }
                }
                
                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    try {
                        final String json = response.body() == null ? "null" : response.body().string();
                        LogUtils.json(json);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (callback != null) {
                                    try {
                                        if (200 == response.code()) {
                                            //此处只处理了data 为 object 的情况{"code":"0","message":"success","data":{}}的情况
                                            //data 为 array 的情况{"code":"0","message":"success","data":[]}的情况需另作处理（new TypeToken<ArrayList<JavaBean>>(){}.getType()）
                                            //简易处理方式如下：
                                            Object respObj = null;
                                            //String json="{\"code\":0,\"message\":\"success\",\"data\":[{\"name\":\"tb\",\"json\":\"mock\"}]}";
                                            if (json.replace(" ", "").contains("\"data\":{")) {
                                                respObj = JsonUtil.getInstance().getJsonUtil().fromJson(json, cls);
                                            } else if (json.replace(" ", "").contains("\"data\":[")) {
                                                respObj = JsonUtil.getInstance().getJsonUtil().fromJsonArray(json, cls);
                                            }
                                            if (respObj instanceof BaseResponse) {
                                                callback.onSuccess(response.code(), requestCode, ((BaseResponse) respObj).getData());
                                            } else {
                                                callback.onSuccess(response.code(), requestCode, respObj);
                                            }
                                        } else if (response.code() >= 500) {
                                            callback.onFailure(response.code(), requestCode, "服务器错误");
                                        } else {
                                            callback.onFailure(response.code(), requestCode, "网络错误");
                                        }
                                    } catch (Exception e) {
                                        LogUtils.d(String.format(DEBUG_FORMAT, String.valueOf("0"), String.valueOf(requestCode), "", e.getMessage()));
                                    }
                                }
                            }
                        });
                    } catch (final Exception e2) {
                        LogUtils.d("Response Exception -> " + String.format(DEBUG_FORMAT, String.valueOf(response.code()), String.valueOf(requestCode), "null", String.valueOf(e2.getMessage())));
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (callback != null) {
                                    try {
                                        callback.onFailure(ExceptionCode.THROW_EXCEPTION, requestCode, "数据解析异常");
                                    } catch (Exception e) {
                                        LogUtils.d(String.format(DEBUG_FORMAT, String.valueOf("0"), String.valueOf(requestCode), "", e.getMessage()));
                                    }
                                }
                            }
                        });
                    }
                }
            });
        } catch (final Exception e3) {
            LogUtils.d(String.format(DEBUG_FORMAT, "", String.valueOf(requestCode), String.valueOf(e3.getMessage()), ""));
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (callback != null) {
                        try {
                            callback.onFailure(ExceptionCode.THROW_EXCEPTION, requestCode, e3.getMessage());
                        } catch (Exception e) {
                            LogUtils.d(String.format(DEBUG_FORMAT, String.valueOf("0"), String.valueOf(requestCode), "", e.getMessage()));
                        }
                    }
                }
            });
        }
    }
}
