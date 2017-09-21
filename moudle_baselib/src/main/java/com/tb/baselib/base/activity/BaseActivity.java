package com.tb.baselib.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.tb.baselib.R;

/**
 * Created by : tb on 2017/9/19 下午5:10.
 * Description :Activity基类
 */
public abstract class BaseActivity extends AppCompatActivity {
    /**
     * 真实的Activity布局
     */
    private int contentLayoutID;
    /**
     * 真实的Activity布局
     */
    protected View contentView;
    /**
     * 真实的Activity布局容器
     */
    protected ViewGroup rootView;
    /**
     * 默认使用toolbar作为标题栏，如不满足可自定义
     */
    protected Toolbar toolbar;
    /**
     * 自定义的标题栏视图
     */
    private View toolbarView;
    /**
     * 自定义的标题栏视图容器
     */
    private ViewGroup toolbarRootView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariables();
        setContentView(R.layout.baselib_base_layout);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbarRootView= (ViewGroup) findViewById(R.id.root_toolbar);
        rootView= (ViewGroup) findViewById(R.id.root_content);
        initToolbar();
        setSupportActionBar(toolbar);
        if(toolbarView!=null){
            toolbarRootView.addView(toolbarView);
        }else{
            toolbarRootView.setVisibility(View.GONE);
        }
        if(contentView==null){
            throw new IllegalArgumentException("please invoke setActivityView(int layoutId) first...");
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initViews(savedInstanceState);
        initListeners();
        requestData();
    }
    
    /**
     * 设置真实的布局内容，放super.onCreate(savedInstanceState)前调用
     * @param layoutId
     */
    protected void setActivityView(int layoutId){
        this.contentLayoutID=layoutId;
        this.contentView=View.inflate(this,layoutId,null);
    }
    
    /**
     * 设置自定义的标题栏，放super.onCreate(savedInstanceState)前调用
     * @param layoutId
     */
    protected void setToolbarSelfView(int layoutId){
        this.toolbarView=View.inflate(this,layoutId,null);
    }
    
    /**
     * 初始化toolbar
     */
    protected void initToolbar(){
        
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
