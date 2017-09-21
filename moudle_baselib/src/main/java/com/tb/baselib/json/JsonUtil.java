package com.tb.baselib.json;

import com.tb.baselib.json.impl.GsonUtil;

/**
 * Created by : tb on 2017/9/21 下午3:33.
 * Description :获取一个解析json数据的单例工具类
 */
public class JsonUtil {
    private static IJson mInstance;
    
    public static final IJson getInstance() {
        return JsonSingletonHolder.instance;
    }
    
    private static final class JsonSingletonHolder {
        private static final GsonUtil instance = new GsonUtil();
    }
}
