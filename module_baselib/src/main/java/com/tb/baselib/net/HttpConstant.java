package com.tb.baselib.net;

import okhttp3.MediaType;

/**
 * @auther tb
 * @time 2017/12/30 下午4:42
 * @desc http常量
 */
public class HttpConstant {
    public static final String POST = "POST";
    public static final String GET = "GET";
    public static final String DELETE = "DELETE";
    public static final String PUT = "PUT";
    public static final long HTTP_DEFAULT_TIME_OUT = 10 * 1000;
    
    public static final String CONTENT_TYPE = "application/json; charset=utf-8";
    public static final String DEBUG_FORMAT = "RESP CODE: %1$s, RESQ CODE %2$s, JSON:%3$s, EXCEPTION:%4$s";
    public static final MediaType JSON = MediaType.parse(CONTENT_TYPE);
    
}
