package com.che.carcheck.support.view;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/*广告图片自动轮播控件*/
public class CycleView extends CycleViewPager {

    private Context context;
    private ImageAdapter adapter;
    private CallBack callBack;
    private int autoPlayTime = 3000;
    private int currentIndex = 1;
    private int size;

    public CycleView(Context context) {
        super(context);
    }

    public CycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setOnPageChangeListener(new GuidePageChangeListener());
        setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        startAutoPlay();
                        break;
                    default:
                        stopAutoPlay();
                        break;
                }
                return false;
            }
        });
    }

    /*装填图片数据*/
    public <T> void setImageResources(List<T> data, CallBack<T> callBack) {
        this.size = data.size();
        this.callBack = callBack;
        adapter = new ImageAdapter(context, data);
        setAdapter(adapter);
    }

    /*自动轮播*/
    public void startAutoPlay() {
        if (adapter == null || adapter.getCount() == 1) {
            return;
        }
        stopAutoPlay();
        mHandler.postDelayed(mImageTimerTask, autoPlayTime);
    }

    private void stopAutoPlay() {
        mHandler.removeCallbacks(mImageTimerTask);
    }

    private Handler mHandler = new Handler();

    private Runnable mImageTimerTask = new Runnable() {

        @Override
        public void run() {
            // 下标等于图片列表长度说明已滚动到最后一张图片,重置下标
            if ((++currentIndex) == size + 1) {
                currentIndex = 1;
            }
            setCurrentItem(currentIndex);
            mHandler.postDelayed(mImageTimerTask, autoPlayTime);
        }
    };

    /*滚动状态监听器*/
    private final class GuidePageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE)
                startAutoPlay(); // 开始下次计时
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int index) {

            if (index == 0 || index == size + 1) {
                return;
            }
            currentIndex = index;
            currentIndex -= 1;
            callBack.onPositionChanged(currentIndex);
        }

    }

    /*图片适配器*/
    private class ImageAdapter<T> extends PagerAdapter {
        private List<ImageView> mViewCacheList;
        private List<T> mData;
        private Context mContext;

        public ImageAdapter(Context context, List<T> adList) {
            mContext = context;
            mData = adList;
            mViewCacheList = new ArrayList<>();
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            if (mData.size() > 0) {
                final T t = mData.get(position);
                ImageView imageView = null;
                if (mViewCacheList.isEmpty()) {
                    imageView = new ImageView(mContext);
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                } else {
                    imageView = mViewCacheList.remove(0);
                }
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callBack.onImageClick(
                                t, v);
                    }
                });
                container.addView(imageView);
                callBack.displayImage(t, imageView);
                return imageView;
            }
            return null;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ImageView view = (ImageView) object;
            container.removeView(view);
            mViewCacheList.add(view);
        }

    }

    /*自定义轮播回调*/
    public interface CallBack<T> {

        void displayImage(T item, ImageView imageView);

        void onImageClick(T item, View imageView);

        void onPositionChanged(int currentPosition);
    }

}
