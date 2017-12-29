package com.tb.baselib.net.impl.retrofit;

import com.tb.baselib.constant.BaseConstant;
import com.tb.baselib.mvp.model.IBaseModel;
import com.tb.baselib.net.BaseResponse;
import com.tb.baselib.net.interfaces.OnRequestCallback;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
    private static final String CONTENT_TYPE = "application/json; charset=utf-8";
    private static final MediaType JSON = MediaType.parse(CONTENT_TYPE);
    private static final String DEBUG_FORMAT = "RESP CODE: %1$s, RESQ CODE %2$s, JSON:%3$s, EXCEPTION:%4$s";
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
            OkHttpClient client = new OkHttpClient()
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
                                    .addHeader("content-type", CONTENT_TYPE)
                                    .build();
                            return chain.proceed(request);
                        }
                    })
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BaseConstant.BASE_API_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            mInstanceMap.put(new Long(timeout), retrofit);
            return retrofit;
        }
    }
    
    @Override
    public void post(int requestCode, String url, Type cls, Object param, OnRequestCallback callback) {
        this.post(requestCode, url, cls, param, callback, BaseConstant.HTTP_DEFAULT_TIME_OUT);
    }
    
    @Override
    public void post(int requestCode, String url, Type cls, Object param, OnRequestCallback callback, long timeout) {
    
    }
    
    @Override
    public void get(int requestCode, String url, Type cls, OnRequestCallback callback) {
        this.get(requestCode, url, cls, callback, BaseConstant.HTTP_DEFAULT_TIME_OUT);
    }
    
    @Override
    public void get(int requestCode, String url, Type cls, OnRequestCallback callback, long timeout) {
        getRetrofitBuilder(timeout).create(RetrofitService.class).doGet()
                .map(new Function<BaseResponse, Object>() {
                    @Override
                    public Object apply(BaseResponse baseResponse) throws Exception {
                        return baseResponse.getData();
                    }
                }).subscribe(new Observer<Object>() {
            @Override
            public void onComplete() {
            
            }
            
            @Override
            public void onSubscribe(Disposable d) {
            
            }
            
            @Override
            public void onNext(Object o) {
            
            }
            
            @Override
            public void onError(Throwable e) {
            
            }
        });
    }
    
    @Override
    public void delete(int requestCode, String url, Type cls, Object param, OnRequestCallback callback) {
        this.delete(requestCode, url, cls, param, callback, BaseConstant.HTTP_DEFAULT_TIME_OUT);
    }
    
    @Override
    public void delete(int requestCode, String url, Type cls, Object param, OnRequestCallback callback, long timeout) {
    
    }
    
    @Override
    public void put(int requestCode, String url, Type cls, Object param, OnRequestCallback callback) {
        this.put(requestCode, url, cls, param, callback, BaseConstant.HTTP_DEFAULT_TIME_OUT);
    }
    
    @Override
    public void put(int requestCode, String url, Type cls, Object param, OnRequestCallback callback, long timeout) {
    
    }
    
}
