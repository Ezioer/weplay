package com.example.weile.materialdesignexa.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by weile on 2016/11/16.
 */
public class TabLayoutViewPager extends FragmentPagerAdapter {
    private Fragment[] mFragment;
    private String[] title;
    public TabLayoutViewPager(FragmentManager fragmentManager,Fragment[] mFragment,String[] title){
        super(fragmentManager);
        this.title=title;
        this.mFragment=mFragment;
    }
    @Override
    public Fragment getItem(int position) {
        return mFragment[position];
    }

    @Override
    public int getCount() {
        return mFragment.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
       return title[position];
    }
}
