package com.tb.baselib.net;

import com.tb.baselib.net.impl.OKHttpRequester;
import com.tb.baselib.mvp.model.IBaseModel;

/**
 * Created by : tb on 2017/9/21 下午4:52.
 * Description :获取一个网络请求的单例工具类
 */
public class ApiRequesterUtil {
    private IBaseModel iApiRequester;
    
    /**
     * 设置具体的网络请求策略
     *
     * @param iApiRequester
     * @return
     */
    public ApiRequesterUtil setRequestStrategy(IBaseModel iApiRequester) {
        this.iApiRequester = iApiRequester;
        return RequesterSingletonHolder.instance;
    }
    
    /**
     * 默认为okhttp
     *
     * @return
     */
    public IBaseModel getIApiRequester() {
        return this.iApiRequester == null ? OKHttpRequester.getInstance() : this.iApiRequester;
    }
    
    public static final ApiRequesterUtil getInstance() {
        return RequesterSingletonHolder.instance;
    }
    
    private static final class RequesterSingletonHolder {
        private static final ApiRequesterUtil instance = new ApiRequesterUtil();
    }
}
