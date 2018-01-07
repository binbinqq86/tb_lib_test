package com.tb.baselib.json.impl;

import android.support.annotation.Keep;

import com.google.gson.Gson;
import com.tb.baselib.json.IJson;
import com.tb.baselib.net.BaseResponse;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by : tb on 2017/9/21 下午3:25.
 * Description :用来解析json数据的工具类，可替换其他第三方
 */
public class GsonUtil implements IJson {
    private GsonUtil() {
    }

    public static final GsonUtil getInstance() {
        return GsonSingletonHolder.instance;
    }

    private static final class GsonSingletonHolder {
        private static final GsonUtil instance = new GsonUtil();
    }

    private Gson mGson = new Gson();

    @Override
    public String toJson(Object obj) {
        return mGson.toJson(obj);
    }

    @Override
    public Object fromJsonObject(String json, Class<?> cls) {
        return mGson.fromJson(json, cls);
    }

    @Override
    public Object fromJson(String json, Type type) {
        return mGson.fromJson(json, getType(BaseResponse.class, type));
    }

    @Override
    public Object fromJsonList(String json, Type type) {
        return mGson.fromJson(json, getType(List.class, type));
    }

    @Override
    public Object fromJsonArray(String json, Type type) {
        return mGson.fromJson(json, getType(BaseResponse.class, getType(List.class, type)));
    }

    /**
     * 用于处理泛型数据
     *
     * @param raw  服务器返回的原始数据模型
     * @param args 需要解析的真实的数据模型(包含在原始model里面，可以传递多个类型的数据，也可以嵌套)
     * @return
     */
    private ParameterizedType getType(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }
}
