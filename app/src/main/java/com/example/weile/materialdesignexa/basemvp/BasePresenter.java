package com.example.weile.materialdesignexa.basemvp;

import android.content.Context;

import com.example.weile.materialdesignexa.basemvp.rx.RxManager;

/**
 * Created by weile on 2017/1/12.
 */
public abstract class BasePresenter<M,V> {
    public Context context;
    public M mModel;
    public V mView;
    public RxManager rxManager=new RxManager();
    public void setVM(M m,V v){
        this.mModel=m;
        this.mView=v;
    }
    public void onDestroy(){
        rxManager.clear();
    }
}
