package com.tb.baselib.net.impl.retrofit;

import com.tb.baselib.net.BaseResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @auther tb
 * @time 2017/12/29 下午4:35
 * @desc Retrofit的专用接口(map不能为null，size可以为0)
 * 注意：Retrofit不接受二次泛型
 * 此处不采用注解指定URL，用@URL来统一放完整带拼接参数的url（比如get,delete参数）
 */
public interface RetrofitService {
    @POST()
    @FormUrlEncoded
    Observable<BaseResponse> post(@Url String url, @FieldMap Map<String, String> maps);
    
    @POST()
    Observable<BaseResponse> postBody(@Url String url, @Body Object object);
    
    @GET()
    Observable<BaseResponse> get(@Url String url, @QueryMap Map<String, String> maps);
    
    @DELETE()
    Observable<BaseResponse> delete(@Url String url, @QueryMap Map<String, String> maps);
    
    @PUT()
    Observable<BaseResponse> put(@Url String url, @QueryMap Map<String, String> maps);
    
    @POST()
    Observable<BaseResponse> putBody(@Url String url, @Body Object object);
    
    @Multipart
    @POST()
    Observable<BaseResponse> uploadFlie(@Url String fileUrl, @Part("description") RequestBody description, @Part("files") MultipartBody.Part file);
    
    @Multipart
    @POST()
    Observable<BaseResponse> uploadFiles(@Url String url, @PartMap() Map<String, RequestBody> maps);
    
    @Multipart
    @POST()
    Observable<ResponseBody> uploadFiles(@Url String url, @Part() List<MultipartBody.Part> parts);
    
    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);
    
    @POST()
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Observable<ResponseBody> postJson(@Url String url, @Body RequestBody jsonBody);
    
    @POST()
    Observable<ResponseBody> postBody(@Url String url, @Body RequestBody body);
}
