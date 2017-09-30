package com.tb.baselib.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tb.baselib.R;
import com.tb.baselib.mvp.model.IBaseModel;
import com.tb.baselib.mvp.presenter.BasePresenterImpl;
import com.tb.baselib.mvp.view.IBaseView;
import com.tb.baselib.net.ApiRequesterUtil;

/**
 * Created by : tb on 2017/9/20 下午4:33.
 * Description :fragment基类
 */
public abstract class BaseFragment extends Fragment implements IBaseView {
    /**
     * 默认已经采用okhttp实现，子类也可以通过自定义presenter来设置自己的网络请求框架
     * 详见：{@link ApiRequesterUtil#setRequestStrategy(IBaseModel)}
     */
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
    
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mApplicationContext = context.getApplicationContext();
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBasePresenter = new BasePresenterImpl(this, ApiRequesterUtil.getInstance().getIApiRequester());
        initVariables();
    }
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.baselib_base_layout, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbarRootView = (ViewGroup) view.findViewById(R.id.root_toolbar);
        rootView = (ViewGroup) view.findViewById(R.id.root_content);
        initToolbar();
        if(!(getActivity() instanceof AppCompatActivity)){
            throw new IllegalArgumentException("please use AppCompatActivity...");
        }
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        if (toolbarView != null) {
            toolbarRootView.addView(toolbarView);
        } else {
            toolbarRootView.setVisibility(View.GONE);
        }
        if (contentView == null) {
            throw new IllegalArgumentException("please invoke setActivityView(int layoutId) first...");
        } else {
            rootView.addView(contentView, 0);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        initViews(savedInstanceState);
        initListeners();
        loadData();
        return view;
    }
    
    /**
     * 设置真实的布局内容，放super.onCreateView()前调用
     *
     * @param layoutId
     */
    protected void setFragmentView(int layoutId) {
        this.contentLayoutID = layoutId;
        this.contentView = View.inflate(mContext, layoutId, null);
    }
    
    /**
     * 设置真实的布局内容，放super.onCreateView()前调用
     *
     * @param layout
     */
    protected void setFragmentView(View layout) {
        this.contentView = layout;
    }
    
    /**
     * 设置自定义的标题栏，放super.onCreate(savedInstanceState)前调用
     *
     * @param layoutId
     */
    protected void setToolbarSelfView(int layoutId) {
        this.toolbarView = View.inflate(mContext, layoutId, null);
    }
    
    /**
     * 初始化toolbar
     */
    protected void initToolbar() {
        
    }
    
    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量
     */
    protected abstract void initVariables();
    
    /**
     * 加载layout布局文件，初始化控件
     *
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
    protected abstract void loadData();
}
