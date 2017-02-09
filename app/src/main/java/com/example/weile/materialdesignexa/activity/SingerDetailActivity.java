package com.example.weile.materialdesignexa.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.weile.materialdesignexa.R;
import com.example.weile.materialdesignexa.base.BaseActivity;
import com.example.weile.materialdesignexa.fragment.Fragment_ArtistAlbum;
import com.example.weile.materialdesignexa.fragment.Fragment_ArtistSongs;
import com.example.weile.materialdesignexa.fragment.TabLayoutViewPager;
import com.example.weile.materialdesignexa.util.Utils;
import com.squareup.picasso.Picasso;

import butterknife.Bind;

/**
 * Created by weile on 2016/11/22.
 */
public class SingerDetailActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar mToolBar;
    @Bind(R.id.tablayout)
    TabLayout mTabLayout;
    @Bind(R.id.viewpager)
    ViewPager mViewPager;
    @Bind(R.id.collapslayout)
    CollapsingToolbarLayout mCollApsLayout;
    private String artistname;
    private Fragment[] mfragment;
    private Long artistid;
    private TabLayoutViewPager mTabLayoutpager;
    private String[] title={"歌曲","专辑"};
    @Bind(R.id.iv_singerpic)
    ImageView mSingerPic;

    @Override
    protected boolean isneedfullscreen() {
        return true;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_singerdetail;
    }

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {
        getIntentData();
        initView();
        initTablayout();
    }

    private void initTablayout() {
        Picasso.with(this).load(R.mipmap.g1).centerCrop().resize(Utils.getScreenWidth
                (SingerDetailActivity.this), Utils.dp2px(this, 300)).into(mSingerPic);
        mfragment=new Fragment[2];
        mfragment[0]=new Fragment_ArtistSongs();
        Bundle bundle=new Bundle();
        bundle.putString("artistname",artistname);
        mfragment[0].setArguments(bundle);

        mfragment[1]=new Fragment_ArtistAlbum();
        Bundle bundle1=new Bundle();
        bundle1.putLong("artistid",artistid);
        mfragment[1].setArguments(bundle1);
        mTabLayoutpager=new TabLayoutViewPager(getSupportFragmentManager(),mfragment,title);
        mViewPager.setAdapter(mTabLayoutpager);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initView() {
        mCollApsLayout.setTitle(artistname);
        mCollApsLayout.setCollapsedTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(mToolBar);
        mToolBar.setPopupTheme(R.style.poptheme);
        mToolBar.setNavigationIcon(R.mipmap.back);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getIntentData() {
        Bundle bundle= getIntent().getExtras();
        artistname =bundle.getString("artistname");
        artistid =bundle.getLong("artistid",0);
    }
    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            String msg = "";
            switch (menuItem.getItemId()) {
                case R.id.more:
                    msg += "Click setting";
                    break;
            }

            if(!msg.equals("")) {
                Toast.makeText(SingerDetailActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detailmenu, menu);
        return true;
    }
}
