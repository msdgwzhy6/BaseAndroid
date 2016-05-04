package com.che.carcheck.support.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;

/**
 * Created by yutianran on 16/2/25.
 */
public class BitmapUtil {

    /*Drawable转Bitmap*/
    public static Bitmap drawableToBitmap(Drawable src) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) src;
        return bitmapDrawable.getBitmap();
    }

    /*Bitmap转Drawable*/
    public static Drawable bitmapToDrawable(Bitmap src) {
        BitmapDrawable bitmapDrawable = new BitmapDrawable(src);
        return bitmapDrawable;
    }

    /*获取圆形图片*/
    public static Bitmap roundBitmap(final Context context, Bitmap src) {
        RoundedBitmapDrawable circularBitmapDrawable =
                RoundedBitmapDrawableFactory.create(context.getResources(), src);
        circularBitmapDrawable.setCircular(true);
        return circularBitmapDrawable.getBitmap();
    }

    /*改变图片尺寸*/
    public static Bitmap resizeBitmap(Bitmap src, float newWidth, float newHeight) {
        float width = src.getWidth();
        float height = src.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth =  newWidth/ width;
        float scaleHeight = newHeight / height;
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(src, 0, 0, (int) width, (int) height, matrix, true);
    }

    /*缩放图片*/
    public static Bitmap scaleBitmap(Bitmap src, float scaleX, float scaleY) {
        Matrix matrix = new Matrix();
        matrix.postScale(scaleX, scaleY);
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(),
                matrix, true);
    }
}
