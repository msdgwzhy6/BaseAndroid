package com.che.carcheck.support.config;

import android.app.Activity;
import android.app.Application;
import android.os.Handler;
import android.os.HandlerThread;

import java.util.Stack;


/**
 * 作者：Fishyer on 2015/11/12 10:26
 * 邮箱：yutianran1993@qq.com
 * 博客：http://www.cnblogs.com/yutianran/
 * 座右铭:知识来自积累,经验源于总结
 */
public class BaseApplication extends Application {

    private static Stack<Activity> activityStack;
    private static BaseApplication singleton;
    private static int mMainThreadId = -1;//主线程ID
    private static Handler mMainThreadHandler;//主线程Handler
    private static HandlerThread handlerThread;//后台线程
    private static Handler mBgThreadHandler;//后台线程Handler

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        singleton = this;
        mMainThreadId = android.os.Process.myTid();
        mMainThreadHandler = new Handler();
        handlerThread = new HandlerThread("bg", 10);
        mBgThreadHandler = new Handler(handlerThread.getLooper());
        activityStack = new Stack<>();
    }

    public static BaseApplication getContext() {
        return singleton;
    }

    //添加Activity到栈
    public void addActivity(Activity activity) {
        activityStack.add(activity);
    }

    //结束指定的Activity
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    //结束所有Activity
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    //获取主线程的handler
    public static Handler getMainHandler() {
        return mMainThreadHandler;
    }

    //获取后台线程的handler
    public static Handler getBgThreadHandler() {
        return mBgThreadHandler;
    }

    // 判断当前的线程是不是在主线程
    public static boolean isRunInMainThread() {
        return android.os.Process.myTid() == mMainThreadId;
    }

}
