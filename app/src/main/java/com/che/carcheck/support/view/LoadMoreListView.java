package com.che.carcheck.support.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.che.carcheck.R;
import com.che.carcheck.support.util.DensityUtil;


public class LoadMoreListView extends ListView {
    private View footView;// 加载更多视图（底部视图）
    private ViewHolder viewHolder;//底部视图的包装类
    private boolean hasMoreData = true; // 是否还有更多数据
    private int footHeight;
    private OnGetMoreListener onLoadMoreListener;
    private boolean isLoadMoreing;
    private int layoutLoadmore= R.layout.view_loadmore;

    public LoadMoreListView(Context context) {
        this(context, null);
    }

    public LoadMoreListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    //初始化
    private void init() {
        //底部
        footView = LayoutInflater.from(getContext()).inflate(layoutLoadmore, null);
        footView.setVisibility(View.GONE);
        footHeight = DensityUtil.dp2px(40f);
        addFooterView(footView);
        viewHolder = new ViewHolder(footView);
        // 滑动监听
        setOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (!canLoadMore()) {
                    return;
                }
                onPreLoadMore();
            }
        });

    }


    //是否允许加载更多
    private boolean canLoadMore() {
        if (onLoadMoreListener == null) {
            return false;
        }
        if (isLoadMoreing) {
            return false;
        }
        if (!hasMoreData) {
            return false;
        }
        if (getAdapter() == null) {
            return false;
        }
        if (!canScroll(1) && !canScroll(-1)) {
            return false;
        }
        if (getLastVisiblePosition() != getAdapter().getCount() - 1) {
            return false;
        }
        return true;
    }

    //判断ListView是否可以滑动(针对ListView未铺满屏幕的情况)
    private boolean canScroll(int direction) {
        final int childCount = getChildCount();
        if (childCount == 0) {
            return false;
        }
        final int firstPosition = getFirstVisiblePosition();
        final int listPaddingTop = getPaddingTop();
        final int listPaddingBottom = getPaddingTop();
        final int itemCount = getAdapter().getCount();

        if (direction > 0) {
            final int lastBottom = getChildAt(childCount - 1).getBottom();
            final int lastPosition = firstPosition + childCount;
            return lastPosition < itemCount || lastBottom > getHeight() - listPaddingBottom;
        } else {
            final int firstTop = getChildAt(0).getTop();
            return firstPosition > 0 || firstTop < listPaddingTop;
        }
    }

    //准备加载更多
    private void onPreLoadMore() {
        isLoadMoreing = true;
        showLoading();
        if (onLoadMoreListener != null) {
            onLoadMoreListener.onGetMore();
        }
    }

    /*暴露的：设置加载完成*/
    public void setLoadMoreComplete() {
        isLoadMoreing = false;
        if (hasMoreData) {
            dismissLoading();
        } else {
            removeLoading();
        }
    }

    //显示加载视图
    private void showLoading() {
        footView.setVisibility(View.VISIBLE);
//        viewHolder.pgLoadMore.setVisibility(View.VISIBLE);
        viewHolder.tvTitle.setText("正在加载...");
    }

    //隐藏加载视图
    private void dismissLoading() {
        footView.setVisibility(View.GONE);
//        viewHolder.pgLoadMore.setVisibility(View.GONE);
        viewHolder.tvTitle.setText("加载完成");
    }

    //移除加载视图
    private void removeLoading() {
        ValueAnimator animator = ValueAnimator.ofInt(0, 1).setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                float fraction = animator.getAnimatedFraction();
                int padding = (int) (-footHeight * fraction);
                footView.setPadding(0, padding, 0, 0);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                dismissLoading();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    //设置回调监听
    public void setOnGetMoreListener(OnGetMoreListener getMoreListener) {
        this.onLoadMoreListener = getMoreListener;
    }

    //设置是否还有更多数据
    public void setHasMoreData(boolean hasMoreData) {
        this.hasMoreData = hasMoreData;
        if (hasMoreData) {
            footView.setPadding(0, 0, 0, 0);
        }
    }

    //获取加载状态
    public boolean isLoadMoreing() {
        return isLoadMoreing;
    }

    // 回调接口
    public interface OnGetMoreListener {
        void onGetMore();
    }

    //底部加载视图的包装类
    static class ViewHolder {
        TextView tvTitle;
//        ProgressBar pgLoadMore;

        ViewHolder(View view) {
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
//            pgLoadMore = (ProgressBar) view.findViewById(R.id.pb_loadMore);
        }
    }
}
