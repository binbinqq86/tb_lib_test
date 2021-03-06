package com.example.tb.tb_lib_test.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tb.tb_lib_test.R;
import com.example.tb.tb_lib_test.webapi.api.Apis;
import com.example.tb.tb_lib_test.webapi.api.ServerUrls;
import com.example.tb.tb_lib_test.webapi.bean.TestBean;
import com.example.tb.tb_lib_test.webapi.param.TestParam;
import com.tb.baselib.base.activity.BaseActivityWithViewStatus;
import com.tb.baselib.constant.BaseConstant;
import com.tb.baselib.image.impl.GlideImageLoader;
import com.tb.baselib.manager.ActivityLauncher;
import com.tb.baselib.manager.PermissionMgr;
import com.tb.baselib.net.HttpConstant;
import com.tb.baselib.util.NumberUtils;
import com.tb.baselib.widget.ToastUtils;

import java.math.BigDecimal;

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
    @BindView(R.id.textView)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentLayoutID() {
        return R.layout.app_activity_main;
    }

    @Override
    protected int getToolbarSelfViewID() {
        return R.layout.app_self_toolbar;
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
    protected void initViews(View contentView, View toolbarView, Bundle savedInstanceState) {
//        textView = (TextView) contentView.findViewById(R.id.textView);
//        iv = (ImageView) contentView.findViewById(R.id.iv);
        //或者使用butterKnife
        ButterKnife.bind(this, contentView);
    }

    @Override
    protected void initListeners() {
        //默认点击finish，此处可以重写事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showCenter("ARouter test");
                ActivityLauncher.test(MainActivity.this);
            }
        });
        textView.setOnClickListener(noDoubleClickListener);
    }

    @Override
    protected void onNoDoubleClick(View v) {
        super.onNoDoubleClick(v);
        switch (v.getId()) {
            case R.id.textView:
                ToastUtils.showBottom(BaseConstant.BASE_API_URL);
                PermissionMgr.checkCallPhonePermission(MainActivity.this, new PermissionMgr.PermissionGrantListener() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void permissionHasGranted(String permission) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:17085347782"));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        MainActivity.this.startActivity(intent);
                    }
                });
                break;
        }
    }

//    @Override
//    protected IBaseModel getIBaseModel() {
//        //采用retrofit封装请求，默认直接使用okhttp
//        return RetrofitRequester.getInstance();
//    }

    @Override
    protected void loadData() {
        Apis.test(mBasePresenter);
//        Apis.test1(mBasePresenter);
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

//        ArrayList<TestBean> bean= (ArrayList<TestBean>) response;
//        textView.setText(bean.get(0).getName()+"\n"+bean.get(0).getJson());
        if (response instanceof TestBean) {
            TestBean bean = (TestBean) response;
            textView.setText(bean.getName() + "\n" + bean.getJson());
        }
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
        GlideImageLoader.getInstance().displayWith(mActivityContext, iv, url);
    }
}
