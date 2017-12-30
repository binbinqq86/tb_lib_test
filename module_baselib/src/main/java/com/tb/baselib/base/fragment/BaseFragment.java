package com.tb.baselib.base.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tb.baselib.R;
import com.tb.baselib.listener.NoDoubleClickListener;
import com.tb.baselib.mvp.model.IBaseModel;
import com.tb.baselib.mvp.presenter.BasePresenterImpl;
import com.tb.baselib.mvp.view.IBaseView;
import com.tb.baselib.net.impl.OKHttpRequester;

/**
 * Created by : tb on 2017/9/20 下午4:33.
 * Description :fragment基类
 */
public abstract class BaseFragment extends Fragment implements IBaseView {
    protected BasePresenterImpl mBasePresenter;
    protected Context mContext;
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
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mApplicationContext = context.getApplicationContext();
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iBaseModel=getIBaseModel();
        mBasePresenter = new BasePresenterImpl(this, iBaseModel);
        initVariables();
        initNoDoubleClickListener();
    }
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.baselib_base_layout, container, false);
        if (!(getActivity() instanceof AppCompatActivity)) {
            throw new IllegalArgumentException("please use AppCompatActivity...");
        }
        setUpToolbar(view);
        setUpContentView(view);
        initViews(this.contentView, this.toolbarView, savedInstanceState);
        initListeners();
        loadData();
        return view;
    }
    
    private void setUpContentView(View view) {
        rootView = (ViewGroup) view.findViewById(R.id.root_content);
        this.contentLayoutID = getContentLayoutID();
        if (contentLayoutID > 0) {
            try {
                this.contentView = View.inflate(mContext, this.contentLayoutID, null);
                if (contentView == null) {
//                    throw new NullPointerException("contentView must not be null,please invoke getContentLayoutID() first...");
                } else {
                    rootView.addView(contentView, 0);
                }
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            } catch (InflateException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void setUpToolbar(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbarRootView = (ViewGroup) view.findViewById(R.id.root_toolbar);
        initToolbar();
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        int toobarLayoutId = getToolbarSelfViewID();
        if (toobarLayoutId > 0) {
            try {
                this.toolbarView = View.inflate(mContext, toobarLayoutId, null);
                if (toolbarView != null) {
                    toolbarRootView.addView(toolbarView);
                } else {
                    toolbarRootView.setVisibility(View.GONE);
                }
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            } catch (InflateException e) {
                e.printStackTrace();
            }
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }
    
    private void initNoDoubleClickListener() {
        noDoubleClickListener = new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                BaseFragment.this.onNoDoubleClick(view);
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
     */
    protected IBaseModel getIBaseModel() {
        return OKHttpRequester.getInstance();
    }
    
    @Override
    public void onSuccess(int responseCode, int requestCode, Object response) {
    
    }
    
    @Override
    public void onFailure(int responseCode, int requestCode, String errMsg) {
    
    }
    
    @Override
    public void showLoadingView() {
    
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBasePresenter != null) {
            mBasePresenter.detachView();
            mBasePresenter = null;
        }
    }
}
