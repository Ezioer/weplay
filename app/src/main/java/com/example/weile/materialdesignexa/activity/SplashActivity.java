package com.example.weile.materialdesignexa.activity;

import android.os.Bundle;

import com.example.weile.materialdesignexa.R;
import com.example.weile.materialdesignexa.base.BaseActivity;
import com.example.weile.materialdesignexa.util.Utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by weile on 2017/2/14.
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected boolean isneedfullscreen() {
        return true;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activitysplash;
    }

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {
        setSwipeEnabled(false);
        final int isFirst= Utils.getTagInt(mContext,"isfirst",0);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(isFirst==1){
                    goActivity(IntroduceActivity.class);
                }else{
                    goActivity(AdActivity.class);
                }
                finish();
            }
        },500);
    }
}
