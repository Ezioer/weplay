package com.example.weile.materialdesignexa.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.weile.materialdesignexa.R;
import com.example.weile.materialdesignexa.bean.AdBean;
import com.example.weile.materialdesignexa.util.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by weile on 2017/2/27.
 */
public class AdAdapter extends PagerAdapter {
    private ArrayList<AdBean.Banner> mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private int mScreenWidth,mScreenHeight;
    public AdAdapter(ArrayList<AdBean.Banner> mList,Context mContext){
        this.mList=mList;
        this.mContext=mContext;
        mLayoutInflater=LayoutInflater.from(mContext);
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

        mScreenWidth = wm.getDefaultDisplay().getWidth();
        mScreenHeight = wm.getDefaultDisplay().getHeight();
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewGroup)container).removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view=mLayoutInflater.inflate(R.layout.item_adheader,container,false);
        container.addView(view);
        ImageView imageView= (ImageView) view.findViewById(R.id.iv_pic);
        Picasso.with(mContext).load(mList.get(position).img).into(imageView);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}
