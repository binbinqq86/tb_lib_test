package com.tb.baselib.interfaces;

/**
 * @auther tb
 * @time 2017/12/25 下午3:48
 * @desc 文件下载相关回调监听
 */
public interface OnDownloadFile {
    void onPreDownload();
    
    void onDownloadProgressing();
    
    void onDownloadCompleted();
    
    void onDownloadFail();
}
