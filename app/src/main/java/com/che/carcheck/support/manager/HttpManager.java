package com.che.carcheck.support.manager;

import android.app.Activity;
import android.app.Dialog;

import com.che.carcheck.R;
import com.che.carcheck.support.constant.WebApi;
import com.che.carcheck.support.util.OkHttpUtil;
import com.che.carcheck.support.util.OkHttpUtil.HttpCallBack;
import com.che.carcheck.support.view.LoadDialog;

import java.util.HashMap;
import java.util.Map;

public class HttpManager {
    //网络加载进度条
    private static Dialog progressDialog;
    static int layoutResId;//加载动画的布局
    static int imgResId;//加载动画的图片
    static int gifResId;//加载动画的动画

    private static void showDialog(Activity activity) {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                return;
            }
            progressDialog = new LoadDialog(activity, layoutResId,imgResId,gifResId);
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void dismissDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private static HttpCallBack convertCallBack(final Activity activity, final HttpCallBack callBack) {
        showDialog(activity);
        HttpCallBack httpCallBack = new HttpCallBack() {
            @Override
            public void onSuccss(String t) {
                dismissDialog();
                callBack.onSuccss(t);
            }

            @Override
            public void onFailure(String error) {
                dismissDialog();
                callBack.onFailure(error);
            }
        };
        return httpCallBack;
    }

    /*车城资讯分类*/
    public static void getNewsClass(Activity activity,OkHttpUtil.HttpCallBack callBack) {
        OkHttpUtil.getInstance().addRequest(WebApi.getNewsClass, activity.hashCode(), convertCallBack(activity,callBack));
    }
    /*车城资讯列表*/
    public static void getNewsList(Activity activity,int classifyId, int  currentPage, OkHttpUtil.HttpCallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("classifyId", String.valueOf(classifyId));
        params.put("start", String.valueOf(currentPage*20));
        params.put("pagesize", "20");
        OkHttpUtil.getInstance().addRequest(WebApi.getNewsList, activity.hashCode(), params, callBack);
    }

}
