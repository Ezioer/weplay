package com.example.weile.materialdesignexa.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.weile.materialdesignexa.R;
import com.example.weile.materialdesignexa.base.BaseActivity;
import com.example.weile.materialdesignexa.ui.detail.Fragment_doubandetail;
import com.example.weile.materialdesignexa.ui.list.DouBanMomentFragment;

import butterknife.Bind;

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
