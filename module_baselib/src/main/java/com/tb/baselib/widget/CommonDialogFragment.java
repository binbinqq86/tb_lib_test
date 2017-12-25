package com.tb.baselib.widget;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.tb.baselib.R;
import com.tb.baselib.base.BaseApplication;
import com.tb.baselib.listener.NoDoubleClickListener;
import com.tb.baselib.util.LogUtils;

/**
 * @auther tb
 * @time 2017/12/25 上午11:46
 * @desc 通用dialog, 可自定义内容view，自定义dialog宽度占屏幕宽度百分比
 */
public class CommonDialogFragment extends DialogFragment {
    
    private static final String TAG = "CommonDialogFragment";


    /*这个方法建议废弃掉*/
    
    /**
     * 两个按钮提示框
     *
     * @param content       内容
     * @param leftText      左按钮文案
     * @param rightText     右按钮文案
     * @param listenerLeft  左按钮事件
     * @param listenerRight 右按钮事件
     */
    public void init(String content, String leftText, String rightText, View.OnClickListener listenerLeft, View.OnClickListener listenerRight) {
        mParams.leftButtonClickListener = listenerLeft;
        mParams.rightButtonClickListener = listenerRight;
        mParams.contentText = content;
        mParams.leftButtonText = leftText;
        mParams.rightButtonText = rightText;
    }
    
    /**
     * 单按钮提示框
     *
     * @param content    内容
     * @param buttonText 按钮文案
     */
    public void init(String content, String buttonText) {
        mParams.contentText = content;
        mParams.singleButtonText = buttonText;
    }
    
    public void init(String content, String buttonText, View.OnClickListener listener) {
        mParams.singleButtonClickListener = listener;
        this.init(content, buttonText);
    }
    
    public void setCanceledOnTouchOutside(boolean isCancelable) {
        mParams.canCanceledOnTouchOutside = isCancelable;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.baselib_ButtonDialog);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        if (mParams != null) {
            //解决cancelable无效的问题
            dialog.setCanceledOnTouchOutside(mParams.canCanceledOnTouchOutside);
            setCancelable(mParams.cancelable);
        }
        createContentView(dialog);
        configDialogSize(dialog);
        return dialog;
    }
    
    private void createContentView(Dialog dialog) {
        View dialogView = LayoutInflater.from(getActivity()).inflate(
                R.layout.baselib_dialog,
                (ViewGroup) dialog.getWindow().getDecorView(),
                false);
        dialog.setContentView(dialogView);
        findViews(dialogView);
        if (createByBuild) {
            bindData();
            LogUtils.i("createContentView  --- bindData()");
        } else {
            initView();
            LogUtils.i("createContentView  --- initView()");
        }
    }
    
    protected <T extends View> T $(View view, @IdRes int id) {
        return (T) view.findViewById(id);
    }
    
    private void bindData() {
        // 标题
        if (!TextUtils.isEmpty(mParams.titleText)) {
            mHolder.dialog_title.setVisibility(View.VISIBLE);
            mHolder.dialog_title.setText(mParams.titleText);
            mHolder.dialog_title.setGravity(mParams.titleGravity);
        } else {
            mHolder.dialog_title.setVisibility(View.GONE);
        }
        // 正文
        if (!TextUtils.isEmpty(mParams.contentText)) {
            mHolder.dialog_content.setText(mParams.contentText);
        }
        // 底部左右按钮
        mHolder.dialog_left_btn.setText(!TextUtils.isEmpty(mParams.leftButtonText) ? mParams.leftButtonText : getString(R.string.baselib_cancel));
        mHolder.dialog_right_btn.setText(!TextUtils.isEmpty(mParams.rightButtonText) ? mParams.rightButtonText : getString(R.string.baselib_confirm));
        //底部左右按钮文字颜色
        if (mParams.leftButtonTextColor != 0) {
            mHolder.dialog_left_btn.setTextColor(mParams.leftButtonTextColor);
        }
        if (mParams.rightButtonTextColor != 0) {
            mHolder.dialog_right_btn.setTextColor(mParams.rightButtonTextColor);
        }
        mHolder.dialog_left_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mParams.leftButtonClickListener != null) {
                    mParams.leftButtonClickListener.onClick(mHolder.dialog_left_btn);
                }
            }
        });
        mHolder.dialog_right_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mParams.rightButtonClickListener != null) {
                    mParams.rightButtonClickListener.onClick(mHolder.dialog_right_btn);
                }
            }
        });
        mHolder.dialog_left_btn.setVisibility(View.VISIBLE);
        mHolder.v_bottom_split.setVisibility(View.VISIBLE);
        mHolder.dialog_right_btn.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(mParams.singleButtonText)) {
            mHolder.v_bottom_split.setVisibility(View.GONE);
            mHolder.dialog_right_btn.setVisibility(View.GONE);
            
            mHolder.dialog_left_btn.setText(mParams.singleButtonText);
            if (mParams.singleButtonTextColor != 0) {
                mHolder.dialog_left_btn.setTextColor(mParams.singleButtonTextColor);
            }
            mHolder.dialog_left_btn.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View view) {
                    dismiss();
                    if (mParams.singleButtonClickListener != null) {
                        mParams.singleButtonClickListener.onClick(view);
                    }
                }
            });
        } else {
            mHolder.dialog_right_btn.setVisibility(View.VISIBLE);
            
        }
        // 内容有自定义View
        if (mParams.contentView != null) {
            mHolder.dialog_content_view.removeAllViews();
            mHolder.dialog_content_view.addView(mParams.contentView);
        }
    }
    
    
    private void findViews(View dialogView) {
        mHolder.dialog_title = $(dialogView, R.id.dialog_title);
        mHolder.dialog_content = $(dialogView, R.id.dialog_content);
        mHolder.dialog_content_view = $(dialogView, R.id.dialog_content_view);
        mHolder.dialog_left_btn = $(dialogView, R.id.dialog_left_btn);
        mHolder.dialog_right_btn = $(dialogView, R.id.dialog_right_btn);
        mHolder.v_bottom_split = $(dialogView, R.id.v_bottom_split);
    }
    
    
    private void initView() {
        if (TextUtils.isEmpty(mParams.titleText)) {
            mHolder.dialog_title.setVisibility(View.GONE);
        } else {
            mHolder.dialog_title.setText(mParams.titleText);
        }
        mHolder.dialog_content.setText(mParams.contentText);
        if (mParams.leftButtonClickListener != null) {
            mHolder.dialog_left_btn.setVisibility(View.VISIBLE);
            mHolder.dialog_left_btn.setText(mParams.leftButtonText);
            mHolder.dialog_left_btn.setOnClickListener(mParams.leftButtonClickListener);
        }
        if (mParams.rightButtonClickListener != null) {
            mHolder.dialog_right_btn.setVisibility(View.VISIBLE);
            mHolder.v_bottom_split.setVisibility(View.VISIBLE);
            mHolder.dialog_right_btn.setText(mParams.rightButtonText);
            mHolder.dialog_right_btn.setOnClickListener(mParams.rightButtonClickListener);
        }
        if (!TextUtils.isEmpty(mParams.singleButtonText)) {
            mHolder.dialog_left_btn.setVisibility(View.VISIBLE);
            mHolder.dialog_left_btn.setText(mParams.singleButtonText);
            mHolder.dialog_left_btn.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View view) {
                    dismiss();
                    if (mParams.singleButtonClickListener != null) {
                        mParams.singleButtonClickListener.onClick(view);
                    }
                }
            });
        }
    }
    
    public void show(FragmentManager manager) {
        super.show(manager, TAG);
    }
    
    public int show(FragmentTransaction transaction) {
        return super.show(transaction, TAG);
    }
    
    public static class Builder {
        
        private Params mParams = new Params();
        
        public Builder setTitleText(String title) {
            mParams.titleText = title;
            return this;
        }
        
        public Builder setTitleTextGravity(int gravity) {
            mParams.titleGravity = gravity;
            return this;
        }
        
        public Builder setContentText(String content) {
            mParams.contentText = content;
            return this;
        }
        
        public Builder setLeftButtonTextColor(int buttonTextColor) {
            mParams.leftButtonTextColor = buttonTextColor;
            return this;
        }
        
        public Builder setRightButtonTextColor(int buttonTextColor) {
            mParams.rightButtonTextColor = buttonTextColor;
            return this;
        }
        
        public Builder setSingleButtonTextColor(int buttonTextColor) {
            mParams.singleButtonTextColor = buttonTextColor;
            return this;
        }
        
        public Builder setLeftButtonText(String buttonText) {
            mParams.leftButtonText = buttonText;
            return this;
        }
        
        public Builder setRightButtonText(String buttonText) {
            mParams.rightButtonText = buttonText;
            return this;
        }
        
        public Builder setSingleButtonText(String buttonText) {
            mParams.singleButtonText = buttonText;
            return this;
        }
        
        public Builder setDialogWidthProportion(float proportion) {
            mParams.proportion = proportion;
            return this;
        }
        
        public Builder setLeftButtonClickListener(View.OnClickListener listener) {
            mParams.leftButtonClickListener = listener;
            return this;
        }
        
        
        public Builder setRightButtonClickListener(View.OnClickListener listener) {
            mParams.rightButtonClickListener = listener;
            return this;
        }
        
        public Builder setSingleButtonClickListener(View.OnClickListener listener) {
            mParams.singleButtonClickListener = listener;
            return this;
        }
        
        public Builder setOnDismissListener(DialogInterface.OnDismissListener listener) {
            mParams.onDismissListener = listener;
            return this;
        }
        
        public Builder setContentView(View contentView) {
            mParams.contentView = contentView;
            return this;
        }
        
        /**
         * 禁止点击外部取消弹框
         *
         * @return
         */
        public Builder forbidCanceledOnTouchOutside() {
            mParams.canCanceledOnTouchOutside = false;
            return this;
        }
        
        /**
         * 禁止按返回键取消弹框
         *
         * @return
         */
        public Builder forbidCanceled() {
            mParams.cancelable = false;
            return this;
        }
        
        public CommonDialogFragment create() {
            // set default values
            if (TextUtils.isEmpty(mParams.leftButtonText)) {
                mParams.leftButtonText = BaseApplication.application.getString(R.string.baselib_cancel);
            }
            if (TextUtils.isEmpty(mParams.rightButtonText)) {
                mParams.rightButtonText = BaseApplication.application.getString(R.string.baselib_confirm);
            }
            CommonDialogFragment dialogFragment = CommonDialogFragment.newInstance(mParams);
            return dialogFragment;
        }
        
    }
    
    static class Params {
        View.OnClickListener leftButtonClickListener;
        View.OnClickListener rightButtonClickListener;
        View.OnClickListener singleButtonClickListener;
        DialogInterface.OnDismissListener onDismissListener;
        boolean canCanceledOnTouchOutside = true;
        boolean cancelable = true;
        float proportion = 5f / 6f; //dialog宽度占屏幕宽度百分比
        String titleText = null;
        int titleGravity = Gravity.CENTER; // title对齐方式
        String contentText = null;
        String leftButtonText = null;
        String rightButtonText = null;
        String singleButtonText = null;
        int leftButtonTextColor;
        int rightButtonTextColor;
        int singleButtonTextColor;
        View contentView = null; //添加用户自定义view
    }
    
    private static class ViewHolder {
        TextView dialog_title;
        TextView dialog_content;
        Button dialog_left_btn;
        Button dialog_right_btn;
        View v_bottom_split;
        ViewGroup dialog_content_view;
    }
    
    private ViewHolder mHolder = new ViewHolder();
    
    private Params mParams = new Params();
    
    private boolean createByBuild;//区分是否通过Build来构建的Dialog
    
    private static CommonDialogFragment newInstance(Params params) {
        CommonDialogFragment dialogFragment = new CommonDialogFragment();
        dialogFragment.mParams = params;
        dialogFragment.createByBuild = true;
        return dialogFragment;
    }
    
    /**
     * 设置dialog宽度
     *
     * @param dialog
     */
    protected void configDialogSize(Dialog dialog) {
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        
        int orientation = getResources().getConfiguration().orientation;
        DisplayMetrics screenMetrics = new DisplayMetrics();
        dialogWindow.getWindowManager().getDefaultDisplay()
                .getMetrics(screenMetrics);
        
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // 横屏状态下以高度为基准
            lp.height = (int) (screenMetrics.heightPixels * mParams.proportion);
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        } else {
            // 竖屏状态下以宽度为基准
            lp.width = (int) (screenMetrics.widthPixels * mParams.proportion);
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        }
        dialogWindow.setAttributes(lp);
    }
}
