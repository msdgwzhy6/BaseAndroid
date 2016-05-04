package com.che.carcheck.support.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.che.carcheck.R;
import com.che.carcheck.support.config.BaseApplication;

public class ImageManager {

    public static void display(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .fitCenter()
                .placeholder(R.drawable.img_default32)
                .error(R.drawable.img_default32)
                .into(imageView);
    }

    public static void displayCenterCrop(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .centerCrop()
                .error(R.drawable.img_default32)
                .into(imageView);
    }

    public static void display(String url, ImageView imageView) {
        Glide.with(BaseApplication.getContext())
                .load(url)
                .placeholder(R.drawable.img_default32)
                .error(R.drawable.img_default32)
                .into(imageView);
    }

    public static void load(String url, final CallBack callBack) {
        try {
            Glide.with(BaseApplication.getContext()).load(url).asBitmap().centerCrop().into(new BitmapImageViewTarget(new ImageView(BaseApplication.getContext())) {
                @Override
                protected void setResource(Bitmap resource) {
                    callBack.onSuccess(resource);
                }
            });
        } catch (Exception e) {
            callBack.onFailure(e);
        }
    }

    public interface CallBack{
        void onSuccess(Bitmap bitmap);
        void onFailure(Throwable throwable);
    }

}
