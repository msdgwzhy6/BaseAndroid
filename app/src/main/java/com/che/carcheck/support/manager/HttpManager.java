package com.che.carcheck.support.manager;

import android.app.Activity;

import com.che.carcheck.support.constant.WebApi;
import com.che.carcheck.support.helper.DialogHelper;
import com.che.carcheck.support.util.OkHttpUtil;
import com.che.carcheck.support.util.OkHttpUtil.HttpCallBack;

public class HttpManager {

    //添加网络加载对话框
    private static HttpCallBack convertCallBack(final Activity activity, final HttpCallBack callBack) {
        DialogHelper.getInstance(activity).showLoading();
        HttpCallBack httpCallBack = new HttpCallBack() {
            @Override
            public void onSuccss(String t) {
                DialogHelper.getInstance(activity).dismissLoading();
                callBack.onSuccss(t);
            }

            @Override
            public void onFailure(String error) {
                DialogHelper.getInstance(activity).dismissLoading();
                callBack.onFailure(error);
            }
        };
        return httpCallBack;
    }

    /*测试网络连接*/
    public static void testNet(Activity activity, Object params,OkHttpUtil.HttpCallBack callBack) {
        OkHttpUtil.getInstance().addRequest(WebApi.getNewsClass, activity.hashCode(),params , convertCallBack(activity, callBack));
    }

}
