package com.example.test;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tb.baselib.base.activity.BaseActivityWithViewStatus;
import com.tb.baselib.constant.ARouterPath;

/**
 * Created by : tb on 2017/9/26 下午3:37.
 * Description :ARouter测试activity
 */
@Route(path = ARouterPath.TEST)
public class TestActivity extends BaseActivityWithViewStatus {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    protected int getContentLayoutID() {
        return R.layout.test;
    }
    
    @Override
    protected void initToolbar() {
        super.initToolbar();
        toolbar.setBackgroundColor(getResources().getColor(R.color.baselib_colorAccent));
        toolbar.setTitle(getString(R.string.baselib_app_name));
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_close_clear_cancel);
    }
    
    @Override
    protected void initVariables() {
        
    }
    
    @Override
    protected void initViews(View contentView, View toolbarView, Bundle savedInstanceState) {
        
    }
    
    @Override
    protected void initListeners() {
        
    }
    
    @Override
    protected void loadData() {
        
    }
    
    @Override
    public void showLoadingView() {
        
    }
    
    @Override
    public void onSuccess(int responseCode, int requestCode, Object response) {
        
    }
    
    @Override
    public void onFailure(int responseCode, int requestCode, String errMsg) {
        
    }
}
