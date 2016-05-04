package com.che.carcheck.support.util;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class CommonAdapter<T> extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<T> mDatas;
    private  int mItemLayoutId;

    public Context getContext() {
        return mContext;
    }

    public int getItemLayoutId() {
        return mItemLayoutId;
    }

    public void setData(List<T> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public CommonAdapter(Context context, int itemLayoutId) {

        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mItemLayoutId = itemLayoutId;
    }

    public CommonAdapter(Context context, List<T> mDatas, int itemLayoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mDatas = mDatas;
        this.mItemLayoutId = itemLayoutId;
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas == null ? null : mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder =  MyViewHolder.getInstance(getContext(), convertView, parent, getItemLayoutId(),
                position);
        convertView = myViewHolder.getConvertView();
        setItemView(myViewHolder, getItem(position), position);
        return convertView;
    }

    public abstract void setItemView(MyViewHolder viewHolder, T item, int position);


    public static class MyViewHolder {
        private int mPosition;
        private View mConvertView;
        private SparseArray<View> mViews;

        private MyViewHolder(Context context, ViewGroup parent, int layoutId,
                             int position) {
            this.mPosition = position;
            this.mViews = new SparseArray<>();
            this.mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,false);
            this.mConvertView.setTag(this);
        }


        public static MyViewHolder getInstance(Context context, View convertView,
                                               ViewGroup parent, int layoutId, int position) {
            if (convertView == null) {
                return new MyViewHolder(context, parent, layoutId, position);
            }
            return (MyViewHolder) convertView.getTag();
        }



        public  <T extends View> T getView(int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = mConvertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

        public View getConvertView() {
            return mConvertView;
        }

        public int getPosition() {
            return mPosition;
        }

    }

}