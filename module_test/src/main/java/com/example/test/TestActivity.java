package com.example.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tb.baselib.base.activity.BaseActivityWithViewStatus;
import com.tb.baselib.constant.ARouterPath;

/**
 * Created by : tb on 2017/9/26 下午3:37.
 * Description :ARouter测试activity
 */
@Route(path = ARouterPath.TEST)
public class TestActivity extends BaseActivityWithViewStatus{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setActivityView(R.layout.test);
        super.onCreate(savedInstanceState);
    }
    
    @Override
    protected void initVariables() {
        
    }
    
    @Override
    protected void initViews(Bundle savedInstanceState) {
        
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
