package com.example.weile.materialdesignexa.base;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.weile.materialdesignexa.R;
import com.example.weile.materialdesignexa.basemvp.BaseModel;
import com.example.weile.materialdesignexa.basemvp.BasePresenter;
import com.example.weile.materialdesignexa.basemvp.BaseView;
import com.example.weile.materialdesignexa.basemvp.TUtil;

import butterknife.ButterKnife;


/**
 * Created by weile on 2017/1/12.
 */
public abstract class BaseFrameFragment<P extends BasePresenter,M extends BaseModel> extends Fragment implements BaseView {
    protected Context mContext=null;
    private View mRoot;
    private View mLoadingview;
    private AnimationDrawable mAnimationDrawable;
    protected int mScreenHeight;
    protected int mScreenWidth;
    public P mPresenter;
    public M mModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter= TUtil.getT(this,0);
        mModel=TUtil.getT(this,1);
        if(this instanceof BaseView){
            mPresenter.setVM(mModel,this);
        }
    }
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
        //判断当前页面是否需要加载动画
        if(isneedani()){
            mLoadingview = LayoutInflater.from(mContext).inflate(R.layout.viewloading,null);
            ((ViewGroup)view.getParent()).addView(mLoadingview);
            ImageView ivload= (ImageView) mLoadingview.findViewById(R.id.iv_loading);
            mAnimationDrawable= (AnimationDrawable) ivload.getDrawable();
        }
        initViewAndEvent(savedInstanceState);
        DisplayMetrics displayMetric=new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetric);
        mScreenHeight=displayMetric.heightPixels;
        mScreenWidth = displayMetric.widthPixels;
    }

    protected abstract boolean isneedani();
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPresenter!=null)mPresenter.onDestroy();
        ButterKnife.unbind(this);
    }
    protected abstract int getContentLayoutId();
    protected abstract void initViewAndEvent(Bundle savedInstanceState);
    @Override
    public void RequestStart() {
        if(null!=mAnimationDrawable){
            mAnimationDrawable.start();
        }
        Toast.makeText(mContext,"正在请求数据",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void Requesterror() {
        Toast.makeText(mContext,"网络错误",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void RequestEnd() {
        if(null!=mAnimationDrawable){
            mAnimationDrawable.stop();
            ((ViewGroup)mRoot.getParent()).removeView(mLoadingview);
        }
        Toast.makeText(mContext,"成功获取数据",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void NetError() {

    }
}
