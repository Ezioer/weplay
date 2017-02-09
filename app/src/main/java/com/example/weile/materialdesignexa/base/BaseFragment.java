package com.example.weile.materialdesignexa.base;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;

/**
 * Created by weile on 2016/12/21.
 */
public abstract class BaseFragment extends Fragment {
    protected Context mContext=null;
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
}
