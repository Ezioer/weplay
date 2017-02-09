package com.example.weile.materialdesignexa.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.weile.materialdesignexa.R;
import com.example.weile.materialdesignexa.base.BaseActivity;
import com.example.weile.materialdesignexa.ui.list.DouBanMomentFragment;

import butterknife.Bind;

/**
 * Created by weile on 2017/1/12.
 */
public class DoubanMomentActivity extends BaseActivity{
    @Bind(R.id.toolbar)
    Toolbar mToolBar;
    @Override
    protected boolean isneedfullscreen() {
        return false;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_doubanmoment;
    }

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {
        initToolBar();
        initFragment();
    }

    private void initFragment() {
        Fragment douban= DouBanMomentFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content,douban).commit();
    }

    private void initToolBar() {
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationIcon(R.mipmap.back);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
