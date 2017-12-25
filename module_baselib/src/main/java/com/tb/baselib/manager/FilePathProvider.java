package com.tb.baselib.manager;

import android.os.Environment;

/**
 * Created by : tb on 2017/9/30 下午4:06.
 * Description :统一sd卡目录管理类
 */
public class FilePathProvider {
    
    /**
     * 外部存储是否可用
     *
     * @return
     */
    public static boolean isExternalEnable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) || !Environment.isExternalStorageRemovable();
    }
}
