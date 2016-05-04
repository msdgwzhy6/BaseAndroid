package com.che.carcheck.support.config;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;

import com.che.carcheck.support.view.LoadDialog;

import butterknife.ButterKnife;

/**
 * Created by yutianran on 16/3/9.
 */
public abstract class BaseHolder {

    private View contentView;//界面
    private Activity activity;//上下文对象

    private BaseHolder() {
    }

    public BaseHolder(Activity activity) {
        this.activity = activity;
        contentView = setContentView();
        ButterKnife.bind(this, contentView);
        init();
    }

    //把当前的view返回给父类
    public View getContentView() {
        return contentView;
    }

    //设置根视图
    public abstract View setContentView();

    //执行一些初始化的操作，非必须，所以空实现了，需要的话重写即可
    public void init() {

    }

    public Activity getActivity() {
        return activity;
    }



}
