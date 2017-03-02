package com.example.weile.materialdesignexa.ui.doubanmomentdetail;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.weile.materialdesignexa.R;
import com.example.weile.materialdesignexa.base.BaseActivity;

/**
 * Created by weile on 2017/1/12.
 */
public class DoubanMomentDetailActivity extends BaseActivity{
    @Override
    protected boolean isneedfullscreen() {
        return true;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_doubanmomentdetail;
    }

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {
        initFragment();
    }

    private void initFragment() {
        Fragment douban= Fragment_doubandetail.newInstance();
        douban.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content,douban)
                .commit();
    }
}
