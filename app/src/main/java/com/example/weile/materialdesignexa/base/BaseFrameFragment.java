package com.example.weile.materialdesignexa.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


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
        initViewAndEvent(savedInstanceState);
        DisplayMetrics displayMetric=new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetric);
        mScreenHeight=displayMetric.heightPixels;
        mScreenWidth = displayMetric.widthPixels;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPresenter!=null)mPresenter.onDestroy();
        ButterKnife.unbind(this);
    }
    protected abstract int getContentLayoutId();
    protected abstract void initViewAndEvent(Bundle savedInstanceState);
}
