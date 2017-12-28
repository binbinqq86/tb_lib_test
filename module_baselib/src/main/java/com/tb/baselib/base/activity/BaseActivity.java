package com.tb.baselib.base.activity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.tb.baselib.R;
import com.tb.baselib.listener.NoDoubleClickListener;
import com.tb.baselib.mvp.model.IBaseModel;
import com.tb.baselib.mvp.presenter.BasePresenterImpl;
import com.tb.baselib.mvp.view.IBaseView;
import com.tb.baselib.net.ApiRequesterUtil;

import butterknife.ButterKnife;

/**
 * Created by : tb on 2017/9/19 下午5:10.
 * Description :Activity基类
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseView {
    protected BasePresenterImpl mBasePresenter;
    protected Context mActivityContext;
    protected Context mApplicationContext;
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
    
    private IBaseModel iBaseModel;
    protected NoDoubleClickListener noDoubleClickListener;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityContext = this;
        mApplicationContext = getApplicationContext();
        setIBaseModel();
        mBasePresenter = new BasePresenterImpl(this, iBaseModel);
        initVariables();
        initNoDoubleClickListener();
        setContentView(R.layout.baselib_base_layout);
        setUpToolbar();
        setUpContentView();
        initViews(this.contentView, this.toolbarView, savedInstanceState);
        initListeners();
        loadData();
    }
    
    /**
     * 设置具体的Activity的View
     */
    private void setUpContentView() {
        rootView = (ViewGroup) findViewById(R.id.root_content);
        this.contentLayoutID = getContentLayoutID();
        if (contentLayoutID > 0) {
            try {
                this.contentView = View.inflate(this, this.contentLayoutID, null);
                if (contentView == null) {
//                    throw new NullPointerException("contentView must not be null,please invoke getContentLayoutID() first...");
                } else {
                    rootView.addView(contentView, 0);
                }
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 设置toolbar相关
     */
    private void setUpToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarRootView = (ViewGroup) findViewById(R.id.root_toolbar);
        initToolbar();
        setSupportActionBar(toolbar);
        int toobarLayoutId = getToolbarSelfViewID();
        if (toobarLayoutId > 0) {
            try {
                this.toolbarView = View.inflate(this, toobarLayoutId, null);
                if (toolbarView != null) {
                    toolbarRootView.addView(toolbarView);
                } else {
                    toolbarRootView.setVisibility(View.GONE);
                }
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    
    private void initNoDoubleClickListener() {
        noDoubleClickListener = new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                BaseActivity.this.onNoDoubleClick(view);
            }
        };
    }
    
    /**
     * 返回Activity布局文件
     *
     * @return
     */
    protected abstract int getContentLayoutID();
    
    /**
     * 设置自定义的标题栏
     *
     * @return
     */
    protected int getToolbarSelfViewID() {
        return -1;
    }
    
    /**
     * 初始化toolbar
     */
    protected void initToolbar() {
        
    }
    
    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量
     */
    protected void initVariables() {
    }
    
    /**
     * 加载layout布局文件，初始化控件
     *
     * @param contentView
     * @param toolbarView
     * @param savedInstanceState
     */
    protected abstract void initViews(View contentView, View toolbarView, Bundle savedInstanceState);
    
    /**
     * 为所有控件加上事件方法
     */
    protected void initListeners() {
    }
    
    /**
     * 向服务器请求具体数据
     */
    protected void loadData() {
    }
    
    protected void onNoDoubleClick(View v) {
    }
    
    /**
     * 设置网络请求框架，默认为okHttp3
     * 详见：{@link ApiRequesterUtil#setRequestStrategy(IBaseModel)}
     */
    protected void setIBaseModel() {
        this.iBaseModel = ApiRequesterUtil.getInstance().getIApiRequester();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBasePresenter != null) {
            mBasePresenter.detachView();
            mBasePresenter = null;
        }
    }
}
