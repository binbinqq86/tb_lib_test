package com.tb.annotation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @auther tb
 * @time 2017/12/13 下午3:09
 * @desc 用来通过注解获取baseApiUrl的annotation
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface BaseApiUrl {
    /**
     * @return 返回网络请求地址的前缀
     */
    String value() default "";
}
