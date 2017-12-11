package com.tb.baselib.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.TextView;

import com.tb.baselib.R;

import java.lang.ref.WeakReference;

/**
 * Created by : tb on 2017/11/13 下午1:22.
 * Description :自定义进度条
 */
public class CustomProgressDialog extends Dialog{
    private static WeakReference<Context> mContext;
    
    private TextView mMessage;
    private static CustomProgressDialog instance;
    public CustomProgressDialog(@NonNull Context context) {
        super(context, R.style.baselib_progressLoadingDialog);
        init();
    }
    
    private void init(){
        setContentView(R.layout.baselib_dialog_progress_loading);
        mMessage = (TextView) findViewById(R.id.textView1);
    }
    
    public static void show(Context context, boolean canCancel,boolean canCancelOnTouchOutside, String text) {
        if (context == null) return;
        if (instance != null && mContext.get() != null && mContext.get().equals(context)) {
            instance.setCancelable(canCancel);
            instance.setCanceledOnTouchOutside(canCancelOnTouchOutside);
            instance.show();
        } else {
            mContext = new WeakReference<>(context);
            instance = new CustomProgressDialog(mContext.get());
            instance.setCancelable(canCancel);
            instance.setCanceledOnTouchOutside(canCancel);
            instance.show();
        }
        if(!TextUtils.isEmpty(text)){
            instance.mMessage.setText(text);
        }
    }
    
    public static void close(){
        if(instance!=null){
            instance.dismiss();
            instance=null;
        }
    }
}
