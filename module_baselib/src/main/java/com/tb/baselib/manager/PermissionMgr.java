package com.tb.baselib.manager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;

import com.tb.baselib.listener.NoDoubleClickListener;
import com.tb.baselib.util.AppUtils;
import com.tb.baselib.util.LogUtils;
import com.tb.baselib.widget.CommonDialogFragment;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * @auther tb
 * @time 2017/12/25 上午10:52
 * @desc 权限管理
 */
public class PermissionMgr {
    private static final String TAG = "PermissionMgr";
    
    /**
     * 电话
     */
    public static void checkCallPhonePermission(final Activity activity, final PermissionGrantListener listener) {
        reqPermission(activity, "电话", listener, null, Manifest.permission.CALL_PHONE);
    }
    
    /**
     * 相机
     */
    public static void checkCameraPermission(final Activity activity, final PermissionGrantListener listener) {
        reqPermission(activity, "相机", listener, null, Manifest.permission.CAMERA);
    }
    
    /**
     * 相机
     */
    public static void checkCameraPermission(final Activity activity, final PermissionGrantListener listener, final PermissionDeniedListener diniedListener) {
        reqPermission(activity, "相机", listener, diniedListener, Manifest.permission.CAMERA);
    }
    
    /**
     * 写入sd卡权限
     */
    public static void checkSDcardPermission(final Activity activity, final PermissionGrantListener listener) {
        reqPermission(activity, "存储", listener, null, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }
    
    /**
     * 位置
     */
    public static void checkLocationPermission(final Activity activity, final PermissionGrantListener listener) {
        reqPermission(activity, "位置信息", listener, null, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
    }
    
    /**
     * 访问设备信息
     */
    public static void checkReadStatePermission(final Activity activity, final PermissionGrantListener listener) {
        reqPermission(activity, "访问设备信息", listener, null, Manifest.permission.READ_PHONE_STATE);
    }
    
    private static void reqPermission(final Activity activity, final String contentMsg, final PermissionGrantListener listener, final PermissionDeniedListener diniedListener, final String... permissions) {
        if (!checkActivityValid(activity)) {
            return;
        }
        RxPermissions rxPermission = new RxPermissions(activity);
        rxPermission.requestEach(permissions)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(final Permission permission) {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            if (listener != null) {
                                listener.permissionHasGranted(permission.name);
                            }
                            LogUtils.i(permission.name + " is granted.");
                        } else {
                            if (permission.shouldShowRequestPermissionRationale) {
                                // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                                LogUtils.i(permission.name + " is denied. More info should be provided.");
                                if (diniedListener != null) {
                                    diniedListener.permissionHasDenied(permission.name);
                                }
                            } else {
                                //加这个判断是为了一次请求多个同一权限系列 防止多次弹框
                                if (permission.name.equals(permissions[0])) {
                                    //此处是防止首次用户拒绝并不再提示后，不弹出dialog，下次再弹出
                                    if (!AppSPManager.getValue(Boolean.class, permissions[0])) {
                                        AppSPManager.saveValue(permissions[0], true);
                                        if (diniedListener != null) {
                                            diniedListener.permissionHasDenied(permission.name);
                                        }
                                    } else {
                                        if (!checkActivityValid(activity)) {
                                            return;
                                        }
                                        String msg = String.format("请在设置-应用-%s-权限中开启[%s]权限，才能正常使用此功能哦！", AppUtils.getAppName(), contentMsg);
                                        new CommonDialogFragment.Builder()
                                                .setTitleText("提示 :")
                                                .setTitleTextGravity(Gravity.LEFT)
                                                .setContentText(msg)
                                                .setRightButtonText("去设置")
                                                .setRightButtonClickListener(new NoDoubleClickListener() {
                                                    @Override
                                                    public void onNoDoubleClick(View view) {
                                                        // 打开设置
                                                        startAppSettings(activity);
                                                    }
                                                })
                                                .setLeftButtonClickListener(new NoDoubleClickListener() {
                                                    @Override
                                                    public void onNoDoubleClick(View view) {
                                                        if (diniedListener != null) {
                                                            diniedListener.permissionHasDenied(permission.name);
                                                        }
                                                    }
                                                })
                                                .setDialogWidthProportion(2f / 3f)//设置dialog宽度占屏幕百分比
                                                .forbidCanceled()
                                                .forbidCanceledOnTouchOutside()
                                                .create()
                                                .show(activity.getFragmentManager());
                                    }
                                    // 用户拒绝了该权限，并且选中『不再询问』
                                    LogUtils.i(permission.name + " is denied.");
                                }
                            }
                        }
                    }
                });
    }
    
    private static boolean checkActivityValid(Activity activity) {
        if (activity == null || activity.isFinishing()) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (activity.isDestroyed()) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 启动应用设置
     */
    public static void startAppSettings(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + activity.getPackageName()));
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(intent);
        } else {
            intent = new Intent();
            intent.setAction("android.intent.action.MAIN");
            intent.setClassName("com.android.settings", "com.android.settings.ManageApplications");
            if (intent.resolveActivity(activity.getPackageManager()) != null) {
                activity.startActivity(intent);
            }
        }
    }
    
    /**
     * 授权成功
     */
    public interface PermissionGrantListener {
        void permissionHasGranted(String permission);
    }
    
    /**
     * 授权拒绝
     */
    public interface PermissionDeniedListener {
        void permissionHasDenied(String permission);
    }
}
