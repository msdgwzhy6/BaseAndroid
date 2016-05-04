package com.che.carcheck.support.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 作者：余天然 on 16/4/20 下午4:58
 * 邮箱：yutianran1993@163.com
 * 博客：http://blog.csdn.net/fisher0113
 * Github：https://github.com/Fishyer
 * 座右铭:知识来自积累,经验源于总结
 */
public class CircleImageView extends ImageView {
    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path clipPath = new Path();
        int w = this.getWidth();
        int h = this.getHeight();
        clipPath.addRoundRect(new RectF(0, 0, w, h), 12f, 12f, Path.Direction.CW);
        canvas.clipPath(clipPath);
        super.onDraw(canvas);
    }
}
