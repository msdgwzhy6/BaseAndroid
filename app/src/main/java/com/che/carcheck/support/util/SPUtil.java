package com.che.carcheck.support.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.che.carcheck.support.config.BaseApplication;

/**
 * Created by yutianran on 16/2/25.
 */
public class SPUtil {

    private String PREFERENCE_NAME = "Che";
    private SharedPreferences mPreferences;

    /*静态内部类的单例模式*/
    public static SPUtil getInstance() {
        return SingletonHolder.mInstance;
    }

    private static class SingletonHolder {
        private static final SPUtil mInstance = new SPUtil();
    }

    /*初始配置*/
    public SPUtil() {
        mPreferences = BaseApplication.getContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    /*存-键值对*/
    public <T> boolean put(String key, T value) {
        Editor editor = mPreferences.edit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else {
        }
        return editor.commit();
    }

    /*取-键值对*/
    public <T> T get(String key, T def) {
        if (def instanceof String) {
            return (T) mPreferences.getString(key, (String) def);
        } else if (def instanceof Integer) {
            Integer res = mPreferences.getInt(key, (Integer) def);
            return (T) res;
        } else if (def instanceof Long) {
            Long res = mPreferences.getLong(key, (Long) def);
            return (T) res;
        } else if (def instanceof Float) {
            Float res = mPreferences.getFloat(key, (Float) def);
            return (T) res;
        } else if (def instanceof Boolean) {
            Boolean res = mPreferences.getBoolean(key, (Boolean) def);
            return (T) res;
        } else {
            return null;
        }
    }

    /*清空*/
    public  void clear() {
        Editor editor = mPreferences.edit();
        editor.clear();
        editor.commit();
    }

}
