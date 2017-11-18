package com.tb.baselib.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.tb.baselib.base.BaseApplication;

/**
 * Created by : tb on 2017/9/30 下午4:05.
 * Description :sharedpreference管理类
 */
public class AppSPManager {
    private static final Context mContext = BaseApplication.application;
    
    /**
     * 获取默认的sharedPreference
     *
     * @return
     */
    public static SharedPreferences getDefaultSP() {
        return getSPByName(BaseApplication.LAST_PACKAGE_NAME);
    }
    
    /**
     * 根据指定的名称获取sharedPreference
     *
     * @param spName
     * @return
     */
    public static SharedPreferences getSPByName(String spName) {
        return mContext.getSharedPreferences(spName, Context.MODE_PRIVATE);
    }
    
    /**
     * 根据key保存一个valye到指定名称的sharedPreference
     *
     * @param spName
     * @param key
     * @param value
     */
    public static void saveValue(String spName, String key, Object value) {
        SharedPreferences sharedPreferences = getSPByName(spName);
        putValue(key, value, sharedPreferences);
    }
    
    /**
     * 根据key保存一个value到默认的sharedPreference
     *
     * @param key
     * @param value
     */
    public static void saveValue(String key, Object value) {
        SharedPreferences sharedPreferences = getDefaultSP();
        putValue(key, value, sharedPreferences);
    }
    
    private static void putValue(String key, Object value, SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        }
        editor.commit();
    }
    
    /**
     * 获取指定的sharedPreference中的一个key对应的value
     *
     * @param spName
     * @param type
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T getValue(String spName, Class<T> type, String key) {
        Object value = null;
        SharedPreferences sharedPreferences = getSPByName(spName);
        return getT(type, key, value, sharedPreferences);
    }
    
    /**
     * 根据key获取默认的sharedPreference中的值
     *
     * @param type
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T getValue(Class<T> type, String key) {
        Object value = null;
        SharedPreferences sharedPreferences = getDefaultSP();
        return getT(type, key, value, sharedPreferences);
    }
    
    private static <T> T getT(Class<T> type, String key, Object value, SharedPreferences sharedPreferences) {
        if (type.getSimpleName().equals(Boolean.class.getSimpleName())) {
            value = sharedPreferences.getBoolean(key, false);
        } else if (type.getSimpleName().equals(Integer.class.getSimpleName())) {
            value = sharedPreferences.getInt(key, -1);
        } else if (type.getSimpleName().equals(String.class.getSimpleName())) {
            value = sharedPreferences.getString(key, "");
        } else if (type.getSimpleName().equals(Long.class.getSimpleName())) {
            value = sharedPreferences.getLong(key, -1L);
        } else if (type.getSimpleName().equals(Float.class.getSimpleName())) {
            value = sharedPreferences.getFloat(key, -1f);
        }
        return (T) value;
    }
    
    /**
     * 移除指定sharedPreference中的一个key对应的value
     *
     * @param spName
     * @param key
     */
    public static void remove(String spName, String key) {
        try {
            SharedPreferences sharedPreferences = getSPByName(spName);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(key);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 移除默认sharedPreference的一个key对应的value
     *
     * @param key
     */
    public static void remove(String key) {
        try {
            SharedPreferences sharedPreferences = getDefaultSP();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(key);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
