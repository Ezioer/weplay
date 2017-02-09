package com.example.weile.materialdesignexa.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.weile.materialdesignexa.R;
import com.example.weile.materialdesignexa.base.BaseFragment;

import butterknife.Bind;

/**
 * Created by weile on 2016/12/26.
 */
public class Fragment_allsongs extends BaseFragment {
    @Bind(R.id.mTabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.mViewPager)
    ViewPager mViewpager;
    private Fragment[] mFragment;
    private String[] title = {"音乐", "艺术家", "专辑"};
    private TabLayoutViewPager mTabLayoutViewPager;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_allsongs;
    }

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {
        initViewPager();
    }

    private void initViewPager() {
        mFragment = new Fragment[3];
        mFragment[0] = Fragment_SongList.newInstance();
        mFragment[1] = Fragment_artist.newInstance();
        mFragment[2] = Fragment_ablum.newInstance();
        mTabLayoutViewPager = new TabLayoutViewPager(getActivity().getSupportFragmentManager(),
                mFragment, title);
        mViewpager.setAdapter(mTabLayoutViewPager);
        mTabLayout.setupWithViewPager(mViewpager);
    }
}
