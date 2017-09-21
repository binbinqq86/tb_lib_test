package com.tb.baselib.net;

import com.tb.baselib.net.impl.OKHttpRequester;
import com.tb.baselib.net.interfaces.IApiRequester;

/**
 * Created by : tb on 2017/9/21 下午4:52.
 * Description :获取一个网络请求的单例工具类
 */
public class ApiRequesterUtil {
    private static IApiRequester mInstance;
    
    public static final IApiRequester getInstance() {
        return RequesterSingletonHolder.instance;
    }
    
    private static final class RequesterSingletonHolder {
        private static final IApiRequester instance = new OKHttpRequester();
    }
}
