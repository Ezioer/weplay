package com.example.weile.materialdesignexa.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.View;
import com.example.weile.materialdesignexa.R;
import com.example.weile.materialdesignexa.base.BaseActivity;
import com.example.weile.materialdesignexa.util.Utils;
import com.example.weile.materialdesignexa.widget.CirclePageIndicator;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by weile on 2017/2/14.
 */
public class IntroduceActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.vp_introduce)
    ViewPager mVp;
    @Bind(R.id.tv_start)
    TextView mStart;
    @Bind(R.id.indicator)
    CirclePageIndicator mIndicator;
    private ArrayList<View> mList=new ArrayList<>();
    @Override
    protected boolean isneedfullscreen() {
        return true;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_introduce;
    }

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {
        setSwipeEnabled(false);
        Utils.setTagInt(mContext,"isfirst",1);
        LayoutInflater inflater=LayoutInflater.from(mContext);
        mList.add(inflater.inflate(R.layout.intro1,null));
        mList.add(inflater.inflate(R.layout.intro2,null));
        mList.add(inflater.inflate(R.layout.intro3,null));
        mList.add(inflater.inflate(R.layout.intro4,null));
        mStart.setOnClickListener(this);
        mVp.setAdapter(pagerAdapter);
        mIndicator.setViewPager(mVp);
        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==mList.size()-1){
                    mStart.setVisibility(View.VISIBLE);
                    mIndicator.setVisibility(View.GONE);
                }else{
                    mIndicator.setVisibility(View.VISIBLE);
                    mStart.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mIndicator.setFillColor(Color.parseColor("#00aaff"));
        mIndicator.setStrokeColor(Color.parseColor("#000000"));
        mIndicator.setStrokeWidth(3);
    }
    private PagerAdapter pagerAdapter=new PagerAdapter() {
        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewGroup)container).removeView((View)object);
        }

        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager)container).addView(mList.get(position));
            return mList.get(position);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
    };

    @Override
    public void onClick(View view) {
        goActivity(AdActivity.class);
        finish();
    }
}
