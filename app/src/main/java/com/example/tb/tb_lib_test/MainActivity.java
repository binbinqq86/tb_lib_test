package com.example.tb.tb_lib_test;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.tb.baselib.base.activity.BaseActivityWithViewStatus;
import com.tb.baselib.net.ApiRequesterUtil;
import com.tb.baselib.net.impl.OKHttpRequester;
import com.tb.baselib.net.interfaces.OnRequestCallback;
import com.tb.baselib.util.LogUtils;
import com.tb.baselib.util.ToastUtils;

public class MainActivity extends BaseActivityWithViewStatus {
    private static final String TAG = "MainActivity";
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
                postTest();
            }
        });
//        showContentView();
    }
    
    private void postTest() {
        String url="http://pre.jcyapi.easybao.com/api/easybao/mobile/public/page/v1";
        TestParam param=new TestParam();
        param.pageNo=1;
        ApiRequesterUtil.getInstance().post(1000, url, TestBean.class, param, new OnRequestCallback() {
            @Override
            public void onSuccess(int responseCode, int requestCode, Object response) {
                LogUtils.e(response.toString());
            }
    
            @Override
            public void onFailure(int responseCode, int requestCode, String errMsg) {
        
            }
        });
        LogUtils.e("eeeeeeeee");
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
