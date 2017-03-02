package com.example.weile.materialdesignexa.base;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.weile.materialdesignexa.R;
import com.example.weile.materialdesignexa.basemvp.BaseView;

import butterknife.ButterKnife;

/**
 * Created by weile on 2016/12/21.
 */
public abstract class BaseFragment extends Fragment implements BaseView{
    protected Context mContext=null;
    private View mRoot;
    private View mLoadingview;
    private AnimationDrawable mAnimationDrawable;
    protected int mScreenHeight;
    protected int mScreenWidth;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        if(getContentLayoutId()!=0){
            return inflater.inflate(getContentLayoutId(),null);
        }else {
            return super.onCreateView(inflater,container,savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext=getActivity();
        ButterKnife.bind(this,view);
        mRoot=view;
        if(isneedani()){
            mLoadingview=LayoutInflater.from(mContext).inflate(R.layout.viewloading,null);
            ((ViewGroup)view.getParent()).addView(mLoadingview);
            ImageView imageView= (ImageView) mLoadingview.findViewById(R.id.iv_loading);
            mAnimationDrawable= (AnimationDrawable) imageView.getDrawable();
        }
        initViewAndEvent(savedInstanceState);

        DisplayMetrics displayMetric=new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetric);
        mScreenHeight=displayMetric.heightPixels;
        mScreenWidth = displayMetric.widthPixels;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
    protected abstract int getContentLayoutId();
    protected abstract void initViewAndEvent(Bundle savedInstanceState);
    protected abstract boolean isneedani();
    @Override
    public void RequestStart() {
        if(null!=mAnimationDrawable){
            mAnimationDrawable.start();
        }
        Toast.makeText(mContext,"正在请求数据",Toast.LENGTH_LONG).show();
    }

    @Override
    public void RequestEnd() {
        if(null!=mAnimationDrawable){
            mAnimationDrawable.stop();
            ((ViewGroup)mRoot.getParent()).removeView(mLoadingview);
        }
        Toast.makeText(mContext,"请求成功",Toast.LENGTH_LONG).show();
    }

    @Override
    public void Requesterror() {
        Toast.makeText(mContext,"网络出现问题了",Toast.LENGTH_LONG).show();
    }

    @Override
    public void NetError() {
        Toast.makeText(mContext,"网络出现问题了",Toast.LENGTH_LONG).show();
    }
    protected void goIntentActivity(View view, Bundle bundle,Class<?>T){
        Intent intent=new Intent(getActivity(),T);
        intent.putExtras(bundle);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(getActivity(), view, "animation_artist_photos");
            startActivity(intent, options.toBundle());
        } else {
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
            ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
        }
    }
}
