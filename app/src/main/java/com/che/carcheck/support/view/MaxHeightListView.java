package com.che.carcheck.support.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 作者：余天然 on 2015/7/27 10:19
 * 邮箱：yutianran1993@qq.com
 * 博客：http://my.oschina.net/u/2345676/blog
 * 座右铭:知识来自积累,经验源于总结
 */
//放在ScrollView中，避免只显示第一项
public class MaxHeightListView extends ListView {

    public MaxHeightListView(Context context) {
        super(context);
    }

    public MaxHeightListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaxHeightListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
