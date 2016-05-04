package com.che.carcheck.support.util;

import android.util.TypedValue;

import com.che.carcheck.support.config.BaseApplication;

public class DensityUtil {

    private DensityUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static int getScreenWidth() {
        int width = BaseApplication.getContext().getResources().getDisplayMetrics().widthPixels;
        return width;
    }

    public static int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, BaseApplication.getContext().getResources().getDisplayMetrics());
    }

    public static int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, BaseApplication.getContext().getResources().getDisplayMetrics());
    }

    public static float px2dp(float pxVal) {
        final float scale = BaseApplication.getContext().getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    public static float px2sp(float pxVal) {
        return (pxVal / BaseApplication.getContext().getResources().getDisplayMetrics().scaledDensity);
    }
} 
