package com.example.weile.materialdesignexa.ui.doubanmomentlist;

import com.example.weile.materialdesignexa.basemvp.BaseModel;
import com.example.weile.materialdesignexa.basemvp.BasePresenter;
import com.example.weile.materialdesignexa.basemvp.BaseView;
import com.example.weile.materialdesignexa.bean.DoubanMomentListBean;

import rx.Observable;

/**
 * Created by weile on 2017/1/12.
 */
public interface DoubanMomentListContract {
    interface Model extends BaseModel {
        Observable<DoubanMomentListBean> getMomentList(String date);
    }
    interface View extends BaseView {
        void refreshData(DoubanMomentListBean doubanMomentBean);
        void loadData(DoubanMomentListBean doubanMomentListBean);
    }
    abstract class Presenter extends BasePresenter<Model,View> {
        abstract void getDoubanMomentList(String date,int i);
    }
}
