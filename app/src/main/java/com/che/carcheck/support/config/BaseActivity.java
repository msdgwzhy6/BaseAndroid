package com.che.carcheck.support.config;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.che.carcheck.MainActivity;
import com.che.carcheck.R;
import com.che.carcheck.support.util.OkHttpUtil;
import com.che.carcheck.support.view.LoadDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * 作者：Fishyer on 2015/11/12 10:28
 * 邮箱：yutianran1993@qq.com
 * 博客：http://www.cnblogs.com/yutianran/
 * 座右铭:知识来自积累,经验源于总结
 */
public class BaseActivity extends FragmentActivity {

    private Dialog progressDialog;

    public void showDialog() {
        try {
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismissDialog() {
        try {
            progressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*以下为：Activity栈管理*/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.getContext().addActivity(this);
        EventBus.getDefault().register(this);
        progressDialog = new LoadDialog(this);
    }


    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        BaseApplication.getContext().finishActivity(this);
        OkHttpUtil.getInstance().removeRequest(this.hashCode());
    }

    /*以下为：事件分发*/
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEvent(Object obj) {
        //接收消息-发送的线程
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(Object obj) {
        //接收消息-主线程
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEventBackgroundThread(Object obj) {
        //接收消息-后台线程
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEventAsyncThread(Object obj) {
        //接收消息-异步线程
    }

    /*以下为：友盟统计*/
    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);

        if (this instanceof MainActivity) {
        } else {
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
        }
    }
}
