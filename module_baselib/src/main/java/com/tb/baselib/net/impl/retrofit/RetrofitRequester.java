package com.tb.baselib.net.impl.retrofit;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.tb.baselib.BuildConfig;
import com.tb.baselib.base.BaseBean;
import com.tb.baselib.constant.BaseConstant;
import com.tb.baselib.interfaces.OnDownloadFile;
import com.tb.baselib.json.impl.GsonUtil;
import com.tb.baselib.mvp.model.IBaseModel;
import com.tb.baselib.net.BaseResponse;
import com.tb.baselib.net.HttpConstant;
import com.tb.baselib.net.interfaces.OnRequestCallback;
import com.tb.baselib.util.LogUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @auther tb
 * @time 2017/12/29 下午3:33
 * @desc Retrofit实现的具体的请求任务类
 */
public class RetrofitRequester implements IBaseModel {
    private static final String TAG = "RetrofitRequester";
    /**
     * 采用线程安全的hashTable
     * K: 超时时间
     * V: 对应的OkHttpClient实例
     */
    private Map<Long, Retrofit> mInstanceMap = new Hashtable<>();

    private RetrofitRequester() {
    }

    private static final class SingletonRetrofit {
        private static final RetrofitRequester instance = new RetrofitRequester();
    }

    public static RetrofitRequester getInstance() {
        return SingletonRetrofit.instance;
    }

    private Retrofit getRetrofitBuilder(long timeout) {
        if (mInstanceMap.containsKey(new Long(timeout))) {
            return mInstanceMap.get(new Long(timeout));
        } else {
            //https相关内容
//            SSLSocketFactory[] socketFactory = new SSLSocketFactory[1];
//            X509TrustManager[] trustManager = new X509TrustManager[1];
//            SSLCertificatesInit.init(socketFactory, trustManager, CertificatesManager.getPayCerInputStream());
            OkHttpClient.Builder builder = new OkHttpClient()
                    .newBuilder()
//                    .sslSocketFactory(socketFactory[0], trustManager[0])
                    .connectTimeout(timeout, TimeUnit.MILLISECONDS)
                    .writeTimeout(timeout, TimeUnit.MILLISECONDS)
                    .readTimeout(timeout, TimeUnit.MILLISECONDS)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request()
                                    .newBuilder()
                                    .addHeader("content-type", HttpConstant.CONTENT_TYPE)
                                    .build();
                            return chain.proceed(request);
                        }
                    });
            if (BuildConfig.IS_DEBUG_MODE) {
                //增加一个okhttp的日志打印拦截器
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(loggingInterceptor);
            }
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BaseConstant.BASE_API_URL)
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            mInstanceMap.put(new Long(timeout), retrofit);
            return retrofit;
        }
    }

    @Override
    public void post(int requestCode, String url, Class cls, Object param, OnRequestCallback callback) {
        this.post(requestCode, url, cls, param, callback, HttpConstant.HTTP_DEFAULT_TIME_OUT);
    }

    @Override
    public void post(int requestCode, String url, Class cls, Object param, OnRequestCallback callback, long timeout) {

    }

    @Override
    public void get(int requestCode, String url, Class cls, OnRequestCallback callback) {
        this.get(requestCode, url, cls, callback, HttpConstant.HTTP_DEFAULT_TIME_OUT);
    }

    @Override
    public void get(final int requestCode, String url, final Class cls, final OnRequestCallback callback, long timeout) {
        getRetrofitBuilder(timeout).create(RetrofitService.class).get(url, new HashMap<String, String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse o) {
                        if (callback != null) {
                            if (o != null) {
                                LogUtils.json(GsonUtil.getInstance().toJson(o));
                                if (o.getCode() == 0) {//后台返回成功
//                                    String json="[{\"name\":\"tb0\",\"json\":\"mock0\"},{\"name\":\"tb1\",\"json\":\"mock1\"}]";
//                                    o.setData(json);
                                    if (o.getData() == null) {
                                        o.setData(new Object());
                                    }
                                    if (o.getData().toString().startsWith("[{")) {
                                        callback.onSuccess(200, requestCode, GsonUtil.getInstance().fromJsonList(o.getData().toString(), cls));
                                    } else if (o.getData().toString().startsWith("{")) {
                                        callback.onSuccess(200, requestCode, GsonUtil.getInstance().fromJsonObject(o.getData().toString(), cls));
                                    } else {
                                        callback.onSuccess(200, requestCode, o.getData());
                                    }
                                } else {
                                    callback.onFailure(o.getCode(), requestCode, o.getMessage());
                                }
                            } else {
                                callback.onFailure(0, requestCode, "数据请求失败");
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d(String.format(HttpConstant.DEBUG_FORMAT, String.valueOf("0"), String.valueOf(requestCode), "", e.getMessage()));
                        if (callback != null) {
                            callback.onFailure(0, requestCode, e.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void delete(int requestCode, String url, Class cls, Object param, OnRequestCallback callback) {
        this.delete(requestCode, url, cls, param, callback, HttpConstant.HTTP_DEFAULT_TIME_OUT);
    }

    @Override
    public void delete(int requestCode, String url, Class cls, Object param, OnRequestCallback callback, long timeout) {

    }

    @Override
    public void put(int requestCode, String url, Class cls, Object param, OnRequestCallback callback) {
        this.put(requestCode, url, cls, param, callback, HttpConstant.HTTP_DEFAULT_TIME_OUT);
    }

    @Override
    public void put(int requestCode, String url, Class cls, Object param, OnRequestCallback callback, long timeout) {

    }

    @Override
    public void downLoadFile(String url, OnDownloadFile onDownloadFile) {

    }
}
