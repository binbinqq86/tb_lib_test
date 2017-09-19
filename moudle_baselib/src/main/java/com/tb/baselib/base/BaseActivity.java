package com.tb.baselib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by : tb on 2017/9/19 下午5:10.
 * Description :Activity基类
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariables();
        initViews(savedInstanceState);
        initListeners();
        requestData();
    }
    
    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量
     */
    protected abstract void initVariables();
    
    /**
     * 加载layout布局文件，初始化控件
     * @param savedInstanceState
     */
    protected abstract void initViews(Bundle savedInstanceState);
    
    /**
     * 为所有控件加上事件方法
     */
    protected abstract void initListeners();
    
    /**
     * 向服务器请求具体数据
     */
    protected abstract void requestData();
}
