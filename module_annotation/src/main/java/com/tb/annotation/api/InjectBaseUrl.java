package com.tb.annotation.api;

import com.tb.annotation.annotation.NoProguard;

/**
 * @auther tb
 * @time 2017/12/21 下午2:09
 * @desc
 */
@NoProguard
public interface InjectBaseUrl<T> {
    void inject(T t,Object o);
}
