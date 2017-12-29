package com.tb.baselib.net.impl.retrofit;

import com.tb.baselib.net.BaseResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @auther tb
 * @time 2017/12/29 下午4:35
 * @desc Retrofit的专用接口
 */
public interface RetrofitService {
    @GET("")
    Observable<BaseResponse> doGet();
}
