package com.tb.baselib.base.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tb.baselib.R;

/**
 * Created by : tb on 2017/9/30 下午5:45.
 * Description :Fragment基类——带加载状态
 * rootView只能最多包含3个child:0-content,1-status,2-shadow
 */
public abstract class BaseFragmentWithViewStatus extends BaseFragment{
    /**
     * 显示加载空页面
     * @param emptyView 传空则显示默认页面
     */
    protected void showLoadEmptyView(View emptyView){
        if(rootView.getChildCount()==3){
            rootView.removeViewAt(1);
        }
        rootView.getChildAt(0).setVisibility(View.GONE);
        if(emptyView==null){
            emptyView= View.inflate(mContext, R.layout.baselib_layout_loading_empty,null);
        }
        rootView.addView(emptyView,1);
    }
    
    /**
     * 显示加载空页面
     * @param imgId 空页面图片资源id
     * @param emptyText 空页面文案
     */
    protected void showLoadEmptyView(int imgId,String emptyText){
        if(rootView.getChildCount()==3){
            rootView.removeViewAt(1);
        }
        rootView.getChildAt(0).setVisibility(View.GONE);
        View emptyView= View.inflate(mContext,R.layout.baselib_layout_loading_empty,null);
        if(imgId!=0){
            ((ImageView)emptyView.findViewById(R.id.baselib_iv_loading_empty)).setImageResource(imgId);
        }
        if(!TextUtils.isEmpty(emptyText)){
            ((TextView)emptyView.findViewById(R.id.baselib_tv_loading_empty)).setText(emptyText);
        }
        rootView.addView(emptyView,1);
    }
    
    /**
     * 显示加载失败页面
     * @param errorView 传空则显示默认页面
     */
    protected void showLoadErrorView(View errorView){
        if(rootView.getChildCount()==3){
            rootView.removeViewAt(1);
        }
        rootView.getChildAt(0).setVisibility(View.GONE);
        if(errorView==null){
            errorView= View.inflate(mContext,R.layout.baselib_layout_loading_error,null);
        }
        rootView.addView(errorView,1);
    }
    
    /**
     * 显示加载失败页面
     * @param imgId 失败页面图片资源id
     * @param errText 失败页面文案
     * @param btText 按钮文案
     * @param btBg 按钮背景
     * @param onClickListener 按钮点击事件
     */
    protected void showLoadErrorView(int imgId, String errText, String btText,int btBg, View.OnClickListener onClickListener){
        if(rootView.getChildCount()==3){
            rootView.removeViewAt(1);
        }
        rootView.getChildAt(0).setVisibility(View.GONE);
        View errorView= View.inflate(mContext,R.layout.baselib_layout_loading_error,null);
        
        if(imgId!=0){
            ((ImageView)errorView.findViewById(R.id.baselib_iv_loading_error)).setImageResource(imgId);
        }
        if(!TextUtils.isEmpty(errText)){
            ((TextView)errorView.findViewById(R.id.baselib_tv_loading_error)).setText(errText);
        }
        if(btBg!=0){
            ((Button)errorView.findViewById(R.id.baselib_bt_loading_retry)).setBackgroundResource(btBg);
        }
        if(!TextUtils.isEmpty(btText)){
            ((Button)errorView.findViewById(R.id.baselib_bt_loading_retry)).setText(btText);
        }
        if(onClickListener!=null){
            ((Button)errorView.findViewById(R.id.baselib_bt_loading_retry)).setOnClickListener(onClickListener);
        }
        rootView.addView(errorView,1);
    }
    
    /**
     * 显示加载中页面
     * @param loadingView 传空则显示默认页面
     */
    protected void showLoadingView(View loadingView){
        if(rootView.getChildCount()==3){
            rootView.removeViewAt(1);
        }
        rootView.getChildAt(0).setVisibility(View.GONE);
        if(loadingView==null){
            loadingView= View.inflate(mContext,R.layout.baselib_layout_loading,null);
        }
        rootView.addView(loadingView,1);
    }
    
    /**
     * 显示加载中页面
     * @param loadingText 加载文案，空则为默认值
     */
    protected void showLoadingView(String loadingText){
        if(rootView.getChildCount()==3){
            rootView.removeViewAt(1);
        }
        rootView.getChildAt(0).setVisibility(View.GONE);
        View loadingView= View.inflate(mContext,R.layout.baselib_layout_loading,null);
        
        if(!TextUtils.isEmpty(loadingText)){
            ((TextView)loadingView.findViewById(R.id.baselib_tv_loading)).setText(loadingText);
        }
        rootView.addView(loadingView,1);
    }
    
    /**
     * 显示内容页面
     */
    protected void showContentView(){
        if(rootView.getChildCount()==3){
            rootView.removeViewAt(1);
        }
        rootView.getChildAt(0).setVisibility(View.VISIBLE);
    }
}
