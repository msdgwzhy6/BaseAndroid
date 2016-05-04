package com.che.carcheck.support.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

/**
 * 文本上下滚动的视图
 */
public class TextSwitchView extends TextSwitcher implements ViewSwitcher.ViewFactory {

    public TextSwitchView(Context context) {
        super(context);
        init();
    }
    public TextSwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init() {
        this.setFactory(this);
        this.setInAnimation(createAnim(0, 0, 1.0f, 0));
        this.setOutAnimation(createAnim(0, 0, 0, -1.0f));
    }

    @Override
    public View makeView() {
        TextView tv =new TextView(getContext());
        tv.setTextSize(14);
        tv.setTextColor(0xff7a7a7a);
        tv.setMaxLines(1);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        return tv;
    }

    public Animation createAnim(
            float fromXValue, float toXValue, float fromYValue, float toYValue) {
        Animation transAnim = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, fromXValue,
                Animation.RELATIVE_TO_PARENT, toXValue,
                Animation.RELATIVE_TO_PARENT, fromYValue,
                Animation.RELATIVE_TO_PARENT, toYValue);
        transAnim.setFillAfter(true);
        transAnim.setDuration(1000);
        transAnim.setInterpolator(new LinearInterpolator());
        return transAnim;
    }


}
