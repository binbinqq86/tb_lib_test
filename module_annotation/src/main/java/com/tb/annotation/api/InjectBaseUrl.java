package com.tb.annotation.api;

/**
 * @auther tb
 * @time 2017/12/21 下午2:09
 * @desc
 */
public interface InjectBaseUrl<T> {
    void inject(T t,Object o);
}
