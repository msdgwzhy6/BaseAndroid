package com.che.carcheck.support.config;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 作者：Fishyer on 2015/11/12 10:31
 * 邮箱：yutianran1993@qq.com
 * 博客：http://www.cnblogs.com/yutianran/
 * 座右铭:知识来自积累,经验源于总结
 */
public abstract class BaseFragment extends Fragment {

    protected boolean isVisible;// 是否显示
    boolean isLoad = false;//是否已经加载

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册EventBus
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //反注册EventBus
        EventBus.getDefault().unregister(this);
    }

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
        //接收消息-后台线程
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        if (!isLoad) {
            isLoad = true;
            onLazyLoad();
        }
    }

    protected void onInvisible() {
    }

    protected void onLazyLoad() {

    }


}
