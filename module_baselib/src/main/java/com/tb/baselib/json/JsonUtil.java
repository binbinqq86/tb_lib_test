package com.tb.baselib.json;

import com.tb.baselib.json.impl.GsonUtil;

/**
 * Created by : tb on 2017/9/21 下午3:33.
 * Description :获取一个解析json数据的单例工具类
 */
public class JsonUtil {
    private IJson iJson;
    
    private JsonUtil() {
    }
    
    /**
     * 设置具体的json解析策略
     *
     * @param jJson
     * @return
     */
    public JsonUtil setJsonStrategy(IJson jJson) {
        this.iJson = iJson;
        return JsonSingletonHolder.instance;
    }
    
    /**
     * 默认为Gson
     *
     * @return
     */
    public IJson getJsonUtil() {
        return this.iJson == null ? GsonUtil.getInstance() : this.iJson;
    }
    
    public static final JsonUtil getInstance() {
        return JsonSingletonHolder.instance;
    }
    
    private static final class JsonSingletonHolder {
        private static final JsonUtil instance = new JsonUtil();
    }
}
