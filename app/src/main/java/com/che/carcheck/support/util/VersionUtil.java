package com.che.carcheck.support.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.che.carcheck.support.config.BaseApplication;

/**
 * 作者：余天然 on 16/5/4 下午4:59
 */
public class VersionUtil {

    //返回程序版本号
    public static int getVersionCode() {
        int versionCode = 0;
        try {
            PackageManager pm = BaseApplication.getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(BaseApplication.getContext().getPackageName(), 0);
            versionCode = pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    //获得程序版本名
    public static String getVersionName() {
        String versionName = "";
        try {
            PackageManager pm = BaseApplication.getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(BaseApplication.getContext().getPackageName(), 0);
            versionName = pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return versionName;
    }
}
