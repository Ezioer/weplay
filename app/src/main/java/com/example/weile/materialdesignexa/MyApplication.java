package com.example.weile.materialdesignexa;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import com.example.weile.materialdesignexa.util.Utils;
import com.example.weile.materialdesignexa.widget.GlideImageLoader;
import com.imnjh.imagepicker.PickerConfig;
import com.imnjh.imagepicker.SImagePicker;
import com.karumi.dexter.Dexter;

/**
 * Created by weile on 2016/12/16.
 */
public class MyApplication extends Application {
    public static Context mContext;
    private static MyApplication instance=null;
    public static MyApplication getInstance(){
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        Dexter.initialize(this);
        mContext=getApplicationContext();
        SImagePicker.init(new PickerConfig.Builder().setAppContext(this)
                .setImageLoader(new GlideImageLoader())
                .setToolbaseColor(getResources().getColor(R.color.colorPrimary))
                .build());
        if(Utils.getTagInt(mContext,"daynight",0)==0){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }

}
