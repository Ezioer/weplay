package com.example.weile.materialdesignexa.activity;

import android.Manifest;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.weile.materialdesignexa.R;
import com.example.weile.materialdesignexa.ui.SongList.Fragment_SongList;
import com.example.weile.materialdesignexa.fragment.Fragment_ablum;
import com.example.weile.materialdesignexa.fragment.Fragment_artist;
import com.example.weile.materialdesignexa.fragment.TabLayoutViewPager;
import com.example.weile.materialdesignexa.service.LockService;
import com.example.weile.materialdesignexa.service.PlayService;
import com.example.weile.materialdesignexa.base.BaseActivity;
import com.example.weile.materialdesignexa.fragment.Fragment_player;
import com.example.weile.materialdesignexa.ui.girlphoto.GirlPhotoListActivity;
import com.example.weile.materialdesignexa.util.Utils;
import com.example.weile.materialdesignexa.util.ViewUtil;
import com.example.weile.materialdesignexa.widget.slidinguppanel.SlidingUpPanelLayout;

import butterknife.Bind;

public class MainActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar mToolBar;
    @Bind(R.id.drawlayout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.navigationview)
    NavigationView mDrawerView;
    @Bind(R.id.spl_slidepane)
    SlidingUpPanelLayout mSlidingPane;
    @Bind(R.id.mTabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.mViewPager)
    ViewPager mViewpager;
    private Fragment[] mFragment;
    private String[] title = {"音乐", "艺术家", "专辑"};
    private TabLayoutViewPager mTabLayoutViewPager;
    private Fragment_player mPlayFragment;
    private View mNavigationHeader;
    public static boolean MainActivityIsRunning= false;

    @Override
    protected boolean isneedfullscreen() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainActivityIsRunning=false;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {
        setSwipeEnabled(false);
        //开启锁屏和音乐服务
        startService(new Intent(this, PlayService.class));
        startService(new Intent(this, LockService.class));
        initDrawer();
        initNavigation();
        initListener();
        initDayNight();
        //检查权限
        if (hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            initData();
        } else {
            requestPermission(11, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        MainActivityIsRunning=true;
    }

    private void initDayNight() {
        if(Utils.getTagInt(mContext,"daynight",0)==0){
            mDrawerView.getMenu().getItem(5).setTitle("夜间模式");
        }else {
            mDrawerView.getMenu().getItem(5).setTitle("白天模式");
        }
    }

    private void initViewPager() {
        mFragment = new Fragment[3];
        mFragment[0] = Fragment_SongList.newInstance();
        mFragment[1] = Fragment_artist.newInstance();
        mFragment[2] = Fragment_ablum.newInstance();
        mTabLayoutViewPager = new TabLayoutViewPager(getSupportFragmentManager(),
                mFragment, title);
        mViewpager.setOffscreenPageLimit(3);
        mViewpager.setAdapter(mTabLayoutViewPager);
        mTabLayout.setupWithViewPager(mViewpager);
    }


    @Override
    public void initData() {
        initViewPager();
    }

    private void initNavigation() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("fragment");
        if (fragment != null && !fragment.isVisible()) {
            ((Fragment_player) fragment).setSlidingPaneLayout(mSlidingPane);
            ((Fragment_player) fragment).setNavigationHeader(mNavigationHeader);
            getSupportFragmentManager().beginTransaction().show(fragment).commit();
        } else {
            mPlayFragment = Fragment_player.newInstance();
            mPlayFragment.setNavigationHeader(mNavigationHeader);
            mPlayFragment.setSlidingPaneLayout(mSlidingPane);
            getSupportFragmentManager().beginTransaction().add(R.id.slidingpane, mPlayFragment,
                    "fragment")
                    .commit();
        }
    }

    private void initDrawer() {
        setSupportActionBar(mToolBar);
        mToolBar.setPopupTheme(R.style.poptheme);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mToolBar, R.string.open, R.string.close);
        drawerToggle.syncState();
        mDrawerLayout.setDrawerListener(drawerToggle);
        setupdrawercontent(mDrawerView);
        mNavigationHeader = mDrawerView.getHeaderView(0);
    }

    private void initListener() {
        mToolBar.setOnMenuItemClickListener(onMenuItemClick);
    }

    private void openfragment() {
       /* Fragment mTempAllFragment = getSupportFragmentManager().findFragmentByTag("allsongs");
        if (mTempAllFragment != null && !mTempAllFragment.isVisible()) {
            getSupportFragmentManager().beginTransaction().show(mTempAllFragment).commit();
        } else {
            mAllSongfragment = new Fragment_allsongs();
            getSupportFragmentManager().beginTransaction().add(R.id.fl_maincontent,
                    mAllSongfragment, "allsongs")
                    .commit();
        }
        mDrawerView.setCheckedItem(R.id.allsong);*/
    }

    private void setupdrawercontent(NavigationView mDrawerView) {
        mDrawerView.setNavigationItemSelectedListener(new NavigationView
                .OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    /*case R.id.allsong:
                        FragmentTransaction transaction = getSupportFragmentManager()
                                .beginTransaction();
                        Fragment fragment=getSupportFragmentManager().findFragmentByTag("allsongs");
                        if (fragment != null) {
                            transaction.show(fragment);
                        } else {
                            mAllSongfragment = new Fragment_allsongs();
                            transaction.add(R.id.fl_maincontent, mAllSongfragment, "allsongs");
                            transaction.show(mAllSongfragment);
                        }
                        transaction.commit();
                        mToolBar.setTitle("所有音乐");
                        break;*/
                    case R.id.douban:
                        goActivity(DoubanMomentActivity.class);
                        break;
                    case R.id.girl:
                        goActivity(GirlPhotoListActivity.class);
                        break;
                    case R.id.mylove:
                        goActivity(MyLoveActivity.class);
                        /*FragmentTransaction transaction1 = getSupportFragmentManager()
                                .beginTransaction();
                        mMyLoveFragment = new Fragment_mylove();
                        transaction1.add(R.id.fl_maincontent, mMyLoveFragment, "lovesong");
                        transaction1.show(mMyLoveFragment);
                        if (mAllSongfragment != null) {
                            transaction1.hide(mAllSongfragment);
                        }
                        transaction1.commit();
                        mToolBar.setTitle("我喜欢的");*/
                        break;
                    case R.id.list:
                        goActivity(TestActivity.class);
                        break;
                    case R.id.changestyle:
                        goActivity(ThemeSettingActivity.class);
                        break;
                    case R.id.daynight:
                        preferenceUtils.saveParam("change_theme_key",
                                6);
                        if ((getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                                == Configuration.UI_MODE_NIGHT_YES) {
                            Utils.setTagInt(mContext,"daynight",0);
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                            item.setTitle("夜间模式");
                        } else {
                            Utils.setTagInt(mContext,"daynight",1);
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        }
                        getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
                        item.setTitle("白天模式");
                        recreate();
                        break;
                    case R.id.exit:
                        ViewUtil.getInstance().popExit(MainActivity.this);
                        break;
                }
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }


    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener
            () {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            String msg = "";
            switch (menuItem.getItemId()) {
                case R.id.more:
                    msg += "Click setting";
                    break;
            }

            if (!msg.equals("")) {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuitem, menu);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ViewUtil.getInstance().popExit(MainActivity.this);
        }
        return false;
    }
}
