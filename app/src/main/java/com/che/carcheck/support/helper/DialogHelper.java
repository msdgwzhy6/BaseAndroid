package com.che.carcheck.support.helper;

import android.app.Activity;
import android.app.Dialog;

import com.che.carcheck.support.view.LoadDialog;

/**
 * 作者：余天然 on 16/5/4 下午4:44
 */
public class DialogHelper {

    private static Dialog progressDialog;
    public static Activity activity;
    int layoutResId;//加载动画的布局
    int imgResId;//加载动画的图片
    int gifResId;//加载动画的动画


    //静态内部类的单例模式
    public static DialogHelper getInstance(Activity _activity) {
        activity = _activity;
        return SingletonHolder.mInstance;
    }

    private static class SingletonHolder {
        private static final DialogHelper mInstance = new DialogHelper(activity);
    }

    public DialogHelper(Activity activity) {
        progressDialog = new LoadDialog(activity, layoutResId,imgResId,gifResId);
    }

    public void showLoading() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                return;
            }
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismissLoading() {
        try {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
