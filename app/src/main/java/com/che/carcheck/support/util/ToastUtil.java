package com.che.carcheck.support.util;

import android.view.Gravity;
import android.widget.Toast;

import com.che.carcheck.support.config.BaseApplication;

public class ToastUtil {

    /*对toast的简易封装。线程安全，可以在非UI线程调用。（默认为短提示）*/
    public static void showToastSafe(final String str) {
        if (BaseApplication.isRunInMainThread()) {
            showToast(str);
        } else {
            BaseApplication.getMainHandler().post(new Runnable() {
                @Override
                public void run() {
                    showToast(str);
                }
            });
        }
    }

    /*设置弹出提示*/
    private static void showToast(final String str) {
            Toast toast = Toast.makeText(BaseApplication.getContext(), str, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 100);
            toast.show();
    }

}
