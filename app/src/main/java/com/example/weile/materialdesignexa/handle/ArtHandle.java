package com.example.weile.materialdesignexa.handle;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.afollestad.async.Action;
import com.example.weile.materialdesignexa.util.RecycleViewHolder;

/**
 * Created by weile on 2016/11/21.
 */
public class ArtHandle extends Action {
    private Bitmap bitmap;
    private String path;
    private RecycleViewHolder holder;
    private int size;
    private long albumId;
    private int position;
    private ValueAnimator colorAnimation, colorAnimation1, colorAnimation2;
    @NonNull
    @Override
    public String id() {
        return null;
    }

    @Nullable
    @Override
    protected Object run() throws InterruptedException {
        return null;
    }
}
