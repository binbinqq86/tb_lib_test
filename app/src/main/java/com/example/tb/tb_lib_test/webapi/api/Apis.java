package com.example.tb.tb_lib_test.webapi.api;

import com.example.tb.tb_lib_test.constant.RequestCode;
import com.example.tb.tb_lib_test.webapi.bean.TestBean;
import com.example.tb.tb_lib_test.webapi.param.TestParam;
import com.tb.baselib.mvp.presenter.BasePresenterImpl;
import com.tb.baselib.net.HttpConstant;

/**
 * Created by TianBin on 2018/1/7 18:52.
 * Description :api集合
 */

public class Apis {
    public static void test(BasePresenterImpl mBasePresenter) {
        TestParam param = new TestParam();
//        param.pageNo = 1;
        mBasePresenter.loadData(RequestCode.REQUEST_CODE_TEST, ServerUrls.URL_TEST, TestBean.class, null);
    }

    public static void test1(BasePresenterImpl mBasePresenter) {
        mBasePresenter.loadData(RequestCode.REQUEST_CODE_TEST1, ServerUrls.URL_TEST_1 + "?regionId=1", TestBean.class, null, HttpConstant.GET);
    }
}
