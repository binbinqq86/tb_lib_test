package com.tb.baselib.mvp.presenter;

import com.tb.baselib.mvp.view.IBaseView;
import com.tb.baselib.mvp.model.IBaseModel;
import com.tb.baselib.net.interfaces.OnRequestCallback;

import java.lang.reflect.Type;

/**
 * Created by : tb on 2017/9/30 上午11:03.
 * Description :Model负责数据的处理和业务逻辑
 */
public class BasePresenterImpl<T> implements IBasePresenter,OnRequestCallback<T>{
    private IBaseView iBaseView;
    private IBaseModel iBaseModel;
    
    public BasePresenterImpl(IBaseView iBaseView, IBaseModel iBaseModel) {
        this.iBaseView = iBaseView;
        this.iBaseModel = iBaseModel;
    }
    
    /**
     * view来展示数据结果
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
     * @param responseCode 回复代码
     * @param requestCode  请求代码
     * @param errMsg
     */
    @Override
    public void onFailure(int responseCode, int requestCode, String errMsg) {
        iBaseView.onFailure(responseCode, requestCode, errMsg);
    }
    
    /**
     * model来负责具体的数据请求和逻辑
     * @param requestCode 请求code
     * @param url         请求地址
     * @param cls         服务器返回真实数据类型
     * @param param       请求参数
     * @param timeout     超时时间
     */
    @Override
    public void loadData(int requestCode, String url, Type cls, Object param, long timeout) {
        iBaseView.showLoadingView();
        iBaseModel.post(requestCode, url, cls, param, this, timeout);
    }
    
    /**
     * model来负责具体的数据请求和逻辑
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
}
