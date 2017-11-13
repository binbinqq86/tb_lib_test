package com.example.tb.tb_lib_test;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tb.baselib.base.activity.BaseActivityWithViewStatus;
import com.tb.baselib.image.ImageLoaderUtil;
import com.tb.baselib.manager.ActivityLauncher;
import com.tb.baselib.net.ApiRequesterUtil;
import com.tb.baselib.net.interfaces.OnRequestCallback;
import com.tb.baselib.widget.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @auther tb
 * @time 2017/11/13 下午12:00
 * @desc
*/
public class MainActivity extends BaseActivityWithViewStatus {
    private static final String TAG = "MainActivity";
    @BindView(R.id.iv)
    ImageView iv;
    
    private Handler mHandler=new Handler(Looper.getMainLooper());
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //使用butterknife
        View view= View.inflate(this,R.layout.app_activity_main,null);
        setActivityView(view);
        ButterKnife.bind(this,view);
        //不使用butterknife
//        setActivityView(R.layout.app_activity_main);
        //设置自定义toolbar
//        setToolbarSelfView(R.layout.app_activity_main);
        super.onCreate(savedInstanceState);
        //默认点击finish，此处可以重写事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showCenter("ARouter test");
                ActivityLauncher.test(MainActivity.this);
            }
        });
    }
    
    @Override
    protected void initToolbar() {
        super.initToolbar();
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setTitle(getString(R.string.app_name));
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
    protected void loadData() {
        String url = "http://pre.jcyapi.easybao.com/api/easybao/mobile/public/page/v1";
        TestParam param = new TestParam();
        param.pageNo = 1;
        mBasePresenter.loadData(1000,url,TestBean.class,param);
    }
    
    @Override
    public void showLoadingView() {
        showLoadingView("loading");
    }
    
    @Override
    public void onSuccess(int responseCode, int requestCode, Object response) {
        //此处也可以显示其他页面，比如空页面等。。。
        //showLoadEmptyView(R.mipmap.ic_launcher,"empty");
        showContentView();
//        TextView tv=new TextView(this);
//        tv.setBackgroundColor(Color.YELLOW);
//        tv.setText("aaaaa");
//        tv.setTextSize(33);
//        showSelfView(tv);
        imageTest();
    }
    
    @Override
    public void onFailure(int responseCode, int requestCode, String errMsg) {
        showLoadErrorView(R.mipmap.ic_launcher, errMsg, "retry", 0, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showBottom("retry click");
                loadData();
            }
        });
    }
    
    
    private void imageTest() {
//        Log.e(TAG, "imageTest: " + (iv == null));
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
}
