package com.che.carcheck.support.util;

import android.util.Log;

/**
 * Created by yutianran on 16/2/25.
 */
public class LogUtil {

    private static final String TAG = "print";

    /*打印*/
    public static void print(String msg) {
        //非Debug版本，则不打印日志
        if (false) {
            return;
        }
        String tmp = getLineFile() + getLineMethod() + msg;
        Log.i(TAG, tmp);
    }

    /*打印*/
    public static void print(String tag,String msg) {
        //非Debug版本，则不打印日志
        if (false) {
            return;
        }
        String tmp = getLineFile() + getLineMethod() + msg;
        Log.i(tag, tmp);
    }

    /*获取行所在的行号和方法名*/
    public static String getLineMethod() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[2];
        StringBuffer toStringBuffer = new StringBuffer("[")
                .append(traceElement.getLineNumber()).append(" | ")
                .append(traceElement.getMethodName()).append("]");
        return toStringBuffer.toString();
    }

    /*获取行所在的文件名*/
    public static String getLineFile() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[2];
        return traceElement.getFileName();
    }
}
