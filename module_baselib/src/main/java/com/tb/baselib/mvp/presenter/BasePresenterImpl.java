package com.tb.baselib.mvp.presenter;

import android.text.TextUtils;
import android.view.TextureView;

import com.tb.baselib.mvp.view.IBaseView;
import com.tb.baselib.mvp.model.IBaseModel;
import com.tb.baselib.net.HttpConstant;
import com.tb.baselib.net.interfaces.OnRequestCallback;

import java.lang.reflect.Type;

/**
 * Created by : tb on 2017/9/30 上午11:03.
 * Description :负责处理业务逻辑代码，处理Model数据，然后将处理完的数据分发到View层
 */
public class BasePresenterImpl<T> implements IBasePresenter, OnRequestCallback<T> {
    private IBaseView iBaseView;
    private IBaseModel iBaseModel;
    
    public BasePresenterImpl(IBaseView iBaseView, IBaseModel iBaseModel) {
        this.iBaseView = iBaseView;
        this.iBaseModel = iBaseModel;
    }
    
    /**
     * view来展示数据结果
     *
     * @param responseCode 回复代码
     * @param requestCode  请求代码
     * @param response     回复实体，真正的数据部分
     */
    @Override
    public void onSuccess(int responseCode, int requestCode, T response) {
        iBaseView.onSuccess(responseCode, requestCode, response);
    }
    
    /**
     * view来展示数据结果
     *
     * @param responseCode 回复代码
     * @param requestCode  请求代码
     * @param errMsg
     */
    @Override
    public void onFailure(int responseCode, int requestCode, String errMsg) {
        iBaseView.onFailure(responseCode, requestCode, errMsg);
    }
    
    /**
     * model来负责具体的数据请求
     *
     * @param requestCode 请求code
     * @param url         请求地址
     * @param cls         服务器返回真实数据类型
     * @param param       请求参数
     * @param type        请求类型(get,post,delete,put...)
     * @param timeout     超时时间
     */
    @Override
    public void loadData(int requestCode, String url, Type cls, Object param, String type, long timeout) {
        iBaseView.showLoadingView();
        if (TextUtils.isEmpty(type)) {
            type = HttpConstant.POST;//默认为post
        }
        if (type.equalsIgnoreCase(HttpConstant.GET)) {
            iBaseModel.get(requestCode, url, cls, this, timeout);
        } else if (type.equalsIgnoreCase(HttpConstant.POST)) {
            iBaseModel.post(requestCode, url, cls, param, this, timeout);
        } else if (type.equalsIgnoreCase(HttpConstant.DELETE)) {
        
        } else if (type.equalsIgnoreCase(HttpConstant.PUT)) {
        
        } else {
        
        }
    }
    
    /**
     * model来负责具体的数据请求
     *
     * @param requestCode 请求code
     * @param url         请求地址
     * @param cls         服务器返回真实数据类型
     * @param param       请求参数
     * @param type        请求类型(get,post,delete,put...)
     */
    @Override
    public void loadData(int requestCode, String url, Type cls, Object param, String type) {
        loadData(requestCode, url, cls, param, type, HttpConstant.HTTP_DEFAULT_TIME_OUT);
    }
    
    /**
     * model来负责具体的数据请求
     *
     * @param requestCode 请求code
     * @param url         请求地址
     * @param cls         服务器返回真实数据类型
     * @param param       请求参数
     */
    @Override
    public void loadData(int requestCode, String url, Type cls, Object param) {
        iBaseView.showLoadingView();
        iBaseModel.post(requestCode, url, cls, param, this);
    }
    
    /**
     * 让Presenter不再持有Activity的引用，避免内存泄漏
     */
    @Override
    public void detachView() {
        iBaseView = null;
        iBaseModel = null;
    }
}
