package com.tb.annotation.api;

import java.lang.reflect.Constructor;

/**
 * @auther tb
 * @time 2017/12/15 下午4:36
 * @desc
 */
public class InjectHelper {
    public static void inject(Object host) {
        String classFullName = host.getClass().getName() + "$$InjectBaseUrl";
        try {
            Class proxy = Class.forName(classFullName);
            Constructor constructor = proxy.getConstructor(host.getClass());
            constructor.newInstance(host);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
