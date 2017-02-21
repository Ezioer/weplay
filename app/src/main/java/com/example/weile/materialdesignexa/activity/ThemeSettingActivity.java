package com.example.weile.materialdesignexa.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.weile.materialdesignexa.R;
import com.example.weile.materialdesignexa.base.BaseActivity;
import com.example.weile.materialdesignexa.bean.ThemeColor;
import com.example.weile.materialdesignexa.handle.ActivityCollector;
import com.example.weile.materialdesignexa.util.CommonRecAdapter;
import com.example.weile.materialdesignexa.util.RecycleViewHolder;
import com.example.weile.materialdesignexa.util.ThemeUtils;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by weile on 2017/1/6.
 */
public class ThemeSettingActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.rv_themelist)
    RecyclerView mRvTheme;

    @Bind(R.id.mfab)
    FloatingActionButton mFab;

    @Override
    protected boolean isneedfullscreen() {
        return false;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_themesetting;
    }

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {
        initToolbar();
        initAdapter();
        mRvTheme.setLayoutManager(new GridLayoutManager(mContext,3));
        mRvTheme.setAdapter(mThemeAdapter);
    }

    private void initToolbar() {
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ThemeUtils.setThemePosition(mThemeAdapter.getPosi());
                preferenceUtils.saveParam("change_theme_key",
                        mThemeAdapter.getPosi());
                ActivityCollector.getInstance().refreshAllActivity();
                getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
            }
        });
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private CommonRecAdapter<ThemeColor> mThemeAdapter;
    private ArrayList<ThemeColor> mThemeList = new ArrayList<>();

    private void initAdapter() {
        mThemeList.add(new ThemeColor(R.color.blue_primary, "魅惑蓝"));
        mThemeList.add(new ThemeColor(R.color.green_primary, "豆瓣绿"));
        mThemeList.add(new ThemeColor(R.color.red_primary, "活力红"));
        mThemeList.add(new ThemeColor(R.color.pink_primary, "酒红"));
        mThemeList.add(new ThemeColor(R.color.deep_purple_primary, "耀眼紫"));
        mThemeList.add(new ThemeColor(R.color.cyan_primary, "天空蓝"));
        mThemeAdapter = new CommonRecAdapter<ThemeColor>(mContext, R.layout.item_themecolor,
                mThemeList) {
            @Override
            public void convert(RecycleViewHolder holder, ThemeColor themeColor,int posi) {
                holder.setText(R.id.tv_themename, themeColor.getThemename());
                ImageView colorimage = holder.getView(R.id.iv_themecolor);
                colorimage.setBackgroundColor(getResources().getColor(themeColor.getColor()));
            }
        };
        mThemeAdapter.setOnItemClickListener(new CommonRecAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent,View view, RecyclerView.ViewHolder holder, int position) {
               ImageView imageView = (ImageView) view.findViewById(R.id.iv_select);
                mFab.setBackgroundColor(mThemeList.get(position).getColor());
                for (int i = 0; i < mThemeList.size(); i++) {
                    if(position==i){
                        imageView.setVisibility(View.VISIBLE);
                    }else{
                        parent.getChildAt(i).findViewById(R.id.iv_select).setVisibility(View.GONE);
                    }
                }
            }
        });
    }
}
