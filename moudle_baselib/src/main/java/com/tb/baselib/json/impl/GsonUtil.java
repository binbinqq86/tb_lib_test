package com.tb.baselib.json.impl;

import com.google.gson.Gson;
import com.tb.baselib.json.IJson;

/**
 * Created by : tb on 2017/9/21 下午3:25.
 * Description :用来解析json数据的工具类，可替换其他第三方
 */
public class GsonUtil implements IJson {
    private Gson mGson = new Gson();
    
    @Override
    public String toJson(Object obj) {
        return mGson.toJson(obj);
    }
    
    @Override
    public Object fromJson(String json, Class<?> cls) {
        return mGson.fromJson(json, cls);
    }
}
