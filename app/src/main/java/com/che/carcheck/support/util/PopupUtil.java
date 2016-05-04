package com.che.carcheck.support.util;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import com.che.carcheck.R;
import com.che.carcheck.support.config.BaseApplication;

/**
 * PopupUtil工具类
 * 作者：Fishyer on 2015/11/26 18:10
 */
public class PopupUtil {
    enum Mode {
        Center,
        Bottom,
        Drop,//锚点下方（不变黑）
        DropBlack,//锚点下方(变黑)
        Top,
    }

    public interface ShowListener {
        void onShow(View view, PopupWindow popupWindow);
    }

    /*居中显示PopupWindow*/
    public static void showInCenter(Activity activity, int resourcesId, ShowListener showListener) {
        View popupView = LayoutInflater.from(BaseApplication.getContext()).inflate(resourcesId,null);
        PopupWindow popupWindow = initPopupWindow(popupView, Mode.Center);
        popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        showListener.onShow(popupView, popupWindow);
    }

    /*底部显示PopupWindow*/
    public static void showInBottom(Activity activity, int resourcesId, ShowListener showListener) {
        View popupView = LayoutInflater.from(BaseApplication.getContext()).inflate(resourcesId,null);
        PopupWindow popupWindow = initPopupWindow(popupView, Mode.Bottom);
        popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        showListener.onShow(popupView, popupWindow);
    }

    /*顶部显示PopupWindow*/
    public static void showInTop(Activity activity, View popupView, ShowListener showListener) {
        PopupWindow popupWindow = initPopupWindow(popupView, Mode.Top);
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.TOP, 0, 0);
        showListener.onShow(popupView, popupWindow);
    }

    /*下方显示PopupWindow*/
    public static void showInDrop(Context activity, int resourcesId, View anchor, ShowListener showListener) {
        View popupView = LayoutInflater.from(activity).inflate(resourcesId, null);
        PopupWindow popupWindow = initPopupWindow(popupView, Mode.DropBlack);
        popupWindow.showAsDropDown(anchor);
        showListener.onShow(popupView, popupWindow);
    }

    /*下方显示PopupWindow,有偏移值*/
    public static void showInDrop(Context activity, int resourcesId, View anchor,int x,int y, ShowListener showListener) {
        View popupView = LayoutInflater.from(activity).inflate(resourcesId, null);
        PopupWindow popupWindow = initPopupWindow(popupView, Mode.Drop);
        popupWindow.showAsDropDown(anchor,x,y);
        showListener.onShow(popupView, popupWindow);
    }

    /*初始化PopupWindow*/
    public static PopupWindow initPopupWindow(View popupView, Mode mode) {
        //创建背景视图
        FrameLayout rootView = new FrameLayout(BaseApplication.getContext());
        FrameLayout.LayoutParams rootParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        int rootBgColor = 0x7f000000;//默认半黑
        //创建内容视图
        FrameLayout contentView = new FrameLayout(BaseApplication.getContext());
        FrameLayout.LayoutParams contentParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        switch (mode) {
            case Center:
                contentParams.setMargins(50, 0, 50, 0);
                contentParams.gravity = Gravity.CENTER;
                contentView.setLayoutParams(contentParams);
                break;
            case Bottom:
                contentParams.gravity = Gravity.BOTTOM;
                break;

            case Top:
                contentParams.gravity = Gravity.TOP;
                break;
            case DropBlack:
//                rootBgColor = 0x00000000;//设置黑
                rootParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                break;
            case Drop:
                rootBgColor = 0x00000000;//设置不黑
                rootParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                break;
        }
        contentView.setLayoutParams(contentParams);
        contentView.addView(popupView);
        rootView.setLayoutParams(rootParams);
        rootView.setBackgroundColor(rootBgColor);
        rootView.addView(contentView);
        final PopupWindow popupWindow = createPopupWindow(rootView);
        //设置默认点击事件
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return popupWindow;
    }

    /*创建PopupWindow*/
    public static PopupWindow createPopupWindow(View popupView) {
        PopupWindow popupWindow = new PopupWindow(popupView);
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return popupWindow;

    }

}
