package com.tb.baselib.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tb.baselib.R;
import com.tb.baselib.base.BaseApplication;
import com.tb.baselib.util.CommonUtils;

/**
 * 全局统一的toast
 */
public class ToastUtils {
    private static Toast mToast;
    private static TextView mMessageTv;
    
    /**
     * 使用之前必须先在application中初始化
     *
     */
    public static void init() {
        mToast = Toast.makeText(BaseApplication.application, "", Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        LayoutInflater inflate = (LayoutInflater)
                BaseApplication.application.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.baselib_layout_toast, null);
        mToast.setView(v);
        mMessageTv = (TextView) v.findViewById(R.id.tv_message);
        mMessageTv.setText("");
    }
    
    /**
     * 弹出toast 默认居中
     *
     * @param msg
     */
    public static void showCenter(String msg) {
        if (mToast == null || TextUtils.isEmpty(msg)) {
            return;
        }
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mMessageTv.setText(msg);
        mToast.show();
    }
    
    /**
     * 在底部显示toast
     *
     * @param msg
     */
    public static void showBottom(String msg) {
        if (mToast == null) {
            return;
        }
        mToast.setGravity(Gravity.BOTTOM, 0, (int) CommonUtils.dp2Px(64));
        mMessageTv.setText(msg);
        mToast.show();
    }
    
    /**
     * toast取消
     */
    public static void cancel() {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
    }
    
}
