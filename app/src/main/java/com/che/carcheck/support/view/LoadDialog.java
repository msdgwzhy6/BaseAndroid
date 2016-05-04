package com.che.carcheck.support.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.bumptech.glide.Glide;
import com.che.carcheck.R;

//加载进度对话框
public class LoadDialog extends Dialog {
    int layoutResId;//加载动画的布局
    int imgResId;//加载动画的图片
    int gifResId;//加载动画的动画

    public LoadDialog(Context context) {
        super(context, R.style.LoadingDialog);
    }

    public LoadDialog(Context context,int layoutResId,int imgResId, int gifResId) {
        super(context, R.style.LoadingDialog);
        this.layoutResId=layoutResId;
        this.imgResId=imgResId;
        this.gifResId=gifResId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(layoutResId, null);
        setContentView(view);
        setCanceledOnTouchOutside(false);
        CircleImageView ivLoadIcon= (CircleImageView) view.findViewById(imgResId);
        Glide.with(getContext()).load(imgResId).into(ivLoadIcon);
    }



}
