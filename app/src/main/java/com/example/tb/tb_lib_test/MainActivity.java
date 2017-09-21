package com.example.tb.tb_lib_test;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.tb.baselib.base.activity.BaseActivityWithViewStatus;
import com.tb.baselib.util.ToastUtils;

public class MainActivity extends BaseActivityWithViewStatus {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setActivityView(R.layout.activity_main);
//        setToolbarSelfView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        //默认点击finish，此处可以重写事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showCenter("111");
//                Toast.makeText(MainActivity.this,"222",Toast.LENGTH_LONG).show();
            }
        });
//        showLoadingView("loading");
//        showLoadEmptyView(R.mipmap.ic_launcher,"empty");
        showLoadErrorView(R.mipmap.ic_launcher, "error", "retry", 0, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showBottom("retry click");
            }
        });
//        showContentView();
    }
    
    @Override
    protected void initToolbar() {
        super.initToolbar();
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setTitle("tb的库测试");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        toolbar.setLogo(R.mipmap.ic_launcher_round);
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
    protected void requestData() {
        
    }
}