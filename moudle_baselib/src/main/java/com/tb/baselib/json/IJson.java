package com.tb.baselib.json;

import com.google.gson.reflect.TypeToken;

/**
 * Created by : tb on 2017/9/21 下午3:27.
 * Description :
 */
public interface IJson {
    String toJson(Object obj);
    
    Object fromJson(String json, Class<?> cls);
}
