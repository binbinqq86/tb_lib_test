package com.tb.annotation.compiler;

import com.tb.annotation.annotation.BaseApiUrl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @auther tb
 * @time 2017/12/13 下午3:11
 * @desc 用来处理获取网络请求前缀的注解的compiler(运行时专用，已废弃)
 */
@Deprecated
public class InjectBaseApiUrl {
    public static void injectBaseUrl(Object obj) {
        Class<?> cls = obj.getClass();
        if (cls.isAnnotationPresent(BaseApiUrl.class)) {
            // 得到这个类的BaseApiUrl注解
            BaseApiUrl mBaseApiUrl = (BaseApiUrl) cls.getAnnotation(BaseApiUrl.class);
            // 得到注解的值
            String baseApiUrl = mBaseApiUrl.value();
            // 使用反射调用SET_BASE_API_URL
            try {
                Method method = cls.getDeclaredMethod("setBaseApiUrl", String.class);
                method.setAccessible(true);
                method.invoke(obj, baseApiUrl);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
