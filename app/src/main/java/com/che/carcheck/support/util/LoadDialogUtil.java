package com.che.carcheck.support.util;

import android.app.Activity;
import android.app.Dialog;

import com.che.carcheck.support.view.LoadDialog;

/**
 * 作者：余天然 on 16/5/4 下午4:44
 */
public class LoadDialogUtil {

    private static Dialog progressDialog;
    public static Activity activity;


    //静态内部类的单例模式
    public static LoadDialogUtil getInstance(Activity _activity) {
        activity = _activity;
        return SingletonHolder.mInstance;
    }

    private static class SingletonHolder {
        private static final LoadDialogUtil mInstance = new LoadDialogUtil(activity);
    }

    public LoadDialogUtil(Activity activity) {
        progressDialog = new LoadDialog(activity);
    }

    public void showDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                return;
            }
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismissDialog() {
        try {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
