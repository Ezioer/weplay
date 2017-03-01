package com.example.weile.materialdesignexa.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.weile.materialdesignexa.MyApplication;

/**
 * Created by weile on 2017/2/24.
 */
public class UrlImageGetter implements Html.ImageGetter {
    Context c;
    TextView container;
    int width;

    /**
     * @param t
     * @param c
     */
    public UrlImageGetter(TextView t, Context c) {
        this.c = c;
        this.container = t;
        width = c.getResources().getDisplayMetrics().widthPixels;
    }

    @Override
    public Drawable getDrawable(String source) {
        final UrlDrawable urlDrawable = new UrlDrawable();
        Glide.with(MyApplication.mContext).load(source).
                asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                        // 计算缩放比例
                float scaleWidth = ((float) width)/resource.getWidth();
                // 取得想要缩放的matrix参数
                Matrix matrix = new Matrix();
                matrix.postScale(scaleWidth, scaleWidth);
                resource = Bitmap.createBitmap(resource, 0, 0, resource.getWidth(),
                        resource.getHeight(), matrix,
                        true);
                urlDrawable.bitmap = resource;
                urlDrawable.setBounds(0, 0, resource.getWidth(), resource.getHeight());
                container.invalidate();
                container.setText(container.getText()); // 解决图文重叠
                                    }
                                }


        );
       /* ImageLoader.getInstance().loadImage(source, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

            }
        });*/
        return urlDrawable;
    }

    @SuppressWarnings("deprecation")
    public class UrlDrawable extends BitmapDrawable {
        protected Bitmap bitmap;

        @Override
        public void draw(Canvas canvas) {
            // override the draw to facilitate refresh function later
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, 0, 0, getPaint());
            }
        }
    }
}
