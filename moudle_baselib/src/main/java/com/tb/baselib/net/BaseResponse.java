package com.tb.baselib.net;

import com.tb.baselib.base.BaseBean;

/**
 * Created by : tb on 2017/9/21 下午4:18.
 * Description :服务器返回数据封装
 */
public class BaseResponse<T> extends BaseBean{
    /**
     * 返回码
     */
    private int code;
    /**
     * 错误信息
     */
    private String errMsg;
    /**
     * 实体数据
     */
    private T data;
    
    public int getCode() {
        return code;
    }
    
    public void setCode(int code) {
        this.code = code;
    }
    
    public String getErrMsg() {
        return errMsg;
    }
    
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
    
    public T getData() {
        return data;
    }
    
    public void setData(T data) {
        this.data = data;
    }
    
    @Override
    public String toString() {
        return "BaseResponse{" +
                "code=" + code +
                ", errMsg='" + errMsg + '\'' +
                ", data=" + data +
                '}';
    }
}
