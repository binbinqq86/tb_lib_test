package com.tb.baselib.mvp.presenter;

import com.tb.baselib.net.interfaces.OnRequestCallback;

import java.lang.reflect.Type;

/**
 * Created by : tb on 2017/9/30 上午10:34.
 * Description :mvp之presenter---Presenter负责做View和Model的中间人
 */
public interface IBasePresenter {
    /**
     * @param requestCode 请求code
     * @param url         请求地址
     * @param cls         服务器返回真实数据类型
     * @param param       请求参数
     * @param type        请求类型(get,post,delete,put...)
     * @param timeout     超时时间
     */
    void loadData(final int requestCode
            , final String url
            , final Type cls
            , final Object param
            , final String type
            , final long timeout);
    
    /**
     * @param requestCode 请求code
     * @param url         请求地址
     * @param cls         服务器返回真实数据类型
     * @param param       请求参数
     * @param type        请求类型(get,post,delete,put...)
     */
    void loadData(final int requestCode
            , final String url
            , final Type cls
            , final Object param
            , final String type);
    
    /**
     * @param requestCode 请求code
     * @param url         请求地址
     * @param cls         服务器返回真实数据类型
     * @param param       请求参数
     */
    void loadData(final int requestCode
            , final String url
            , final Type cls
            , final Object param);
    
    void detachView();
}
