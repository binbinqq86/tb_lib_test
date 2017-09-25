package com.example.tb.tb_lib_test;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.tb.baselib.base.activity.BaseActivityWithViewStatus;
import com.tb.baselib.image.ImageLoaderUtil;
import com.tb.baselib.net.ApiRequesterUtil;
import com.tb.baselib.net.impl.OKHttpRequester;
import com.tb.baselib.net.interfaces.OnRequestCallback;
import com.tb.baselib.util.LogUtils;
import com.tb.baselib.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivityWithViewStatus {
    private static final String TAG = "MainActivity";
    @BindView(R.id.iv)
    ImageView iv;
    
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
        showLoadingView("loading");
//        showLoadEmptyView(R.mipmap.ic_launcher,"empty");
        postTest();
    }
    
    private void imageTest() {
        Log.e(TAG, "imageTest: " + (iv == null));
        String url = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/logo_white_fe6da1ec.png";
        ImageLoaderUtil.getInstance().getIImageDisplay().displayByUrl(mActivityContext, iv, url);
    }
    
    private void postTest() {
        String url = "http://pre.jcyapi.easybao.com/api/easybao/mobile/public/page/v1";
        TestParam param = new TestParam();
        param.pageNo = 1;
        ApiRequesterUtil.getInstance().getIApiRequester().post(1000, url, TestBean.class, param, new OnRequestCallback() {
            @Override
            public void onSuccess(int responseCode, int requestCode, Object response) {
//                LogUtils.e(response.toString());
                showContentView();
                imageTest();
            }
            
            @Override
            public void onFailure(int responseCode, int requestCode, String errMsg) {
                showLoadErrorView(R.mipmap.ic_launcher, "error", "retry", 0, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ToastUtils.showBottom("retry click");
                        postTest();
                    }
                });
            }
        });
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
        iv = (ImageView) findViewById(R.id.iv);
    }
    
    @Override
    protected void initListeners() {
        
    }
    
    @Override
    protected void requestData() {
        
    }
}
