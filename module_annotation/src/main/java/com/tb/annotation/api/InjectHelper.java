package com.tb.annotation.api;

import com.tb.annotation.info.ProxyInfo;

/**
 * @auther tb
 * @time 2017/12/15 下午4:36
 * @desc
 */
public class InjectHelper {
    public static void inject(Object o) {
        inject(o, o);
    }
    
    public static void inject(Object host, Object root) {
        String classFullName = host.getClass().getName() + ProxyInfo.ClassSuffix;
        try {
            Class proxy = Class.forName(classFullName);
            InjectBaseUrl injector = (InjectBaseUrl) proxy.newInstance();
            injector.inject(host, root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
