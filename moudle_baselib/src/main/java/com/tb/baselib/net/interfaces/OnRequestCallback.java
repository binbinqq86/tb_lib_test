package com.tb.baselib.net.interfaces;

/**
 * Created by : tb on 2017/9/21 上午10:48.
 * Description :网络请求结果回调接口
 */
public interface OnRequestCallback<T> {
    /**
     * 成功返回
     *
     * @param responseCode 回复代码
     * @param requestCode  请求代码
     * @param response     回复实体，真正的数据部分
     */
    void onSuccess(final int responseCode, final int requestCode, final T response);
    
    /**
     * 失败返回
     *
     * @param responseCode 回复代码
     * @param requestCode  请求代码
     */
    void onFailure(final int responseCode, final int requestCode, String errMsg);
}
