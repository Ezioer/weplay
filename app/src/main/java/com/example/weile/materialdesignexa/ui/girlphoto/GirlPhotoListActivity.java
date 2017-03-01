package com.example.weile.materialdesignexa.ui.girlphoto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.weile.materialdesignexa.R;
import com.example.weile.materialdesignexa.base.BaseActivity;

import butterknife.Bind;

/**
 * Created by weile on 2017/2/22.
 */
public class GirlPhotoListActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Override
    protected boolean isneedfullscreen() {
        return false;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_girlphotolist;
    }

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {
        initToolBar();
        initFragment();
    }

    private void initFragment() {
        Fragment fragment=GirlPhotoListFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content,fragment).commit();
    }

    private void initToolBar() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
