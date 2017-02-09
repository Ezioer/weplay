package com.example.weile.materialdesignexa.ui.detail;

import com.example.weile.materialdesignexa.basemvp.BaseModel;
import com.example.weile.materialdesignexa.basemvp.BasePresenter;
import com.example.weile.materialdesignexa.basemvp.BaseView;
import com.example.weile.materialdesignexa.bean.DoubanMomentDetailBean;

import rx.Observable;

/**
 * Created by weile on 2017/1/16.
 */
public interface DoubanDetailContract {
    interface Model extends BaseModel{
        Observable<DoubanMomentDetailBean> getMomentDetail(String id);
    }
    interface View extends BaseView{
        void refreshData(DoubanMomentDetailBean doubanMomentDetailBean);
    }
    abstract class Presenter extends BasePresenter<Model,View>{
        abstract void getDetail(String postid);
    }
}
