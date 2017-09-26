package com.tb.baselib.json;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by : tb on 2017/9/21 下午3:27.
 * Description :json数据处理通用接口
 */
public interface IJson {
    String toJson(Object obj);
    
    /**
     * 解析服务器返回的json
     * @param json 待解析的json数据
     * @param cls 解析的数据模型，如果里面包含泛型，则可以使用{@link TypeToken}
     * @return
     */
    Object fromJson(String json, Class<?> cls);
    
    /**
     * 解析服务器返回的json
     * @param json 待解析的json数据
     * @param type 解析的数据模型，用于解决{@link TypeToken}不能使用泛型的缺点
     * @return
     */
    Object fromJson(String json, Type type);
}
