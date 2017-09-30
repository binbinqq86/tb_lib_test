package com.tb.baselib.mvp.view;

import com.tb.baselib.net.interfaces.OnRequestCallback;

/**
 * Created by : tb on 2017/9/30 上午10:34.
 * Description :mvp之view(将数据与视图绑定)---View负责展示数据
 */
public interface IBaseView extends OnRequestCallback {
    /**
     * 显示数据加载中页面
     */
    void showLoadingView();
}
