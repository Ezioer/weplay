package com.example.weile.materialdesignexa.ui.girlphoto;

import com.example.weile.materialdesignexa.basemvp.BaseModel;
import com.example.weile.materialdesignexa.basemvp.BasePresenter;
import com.example.weile.materialdesignexa.basemvp.BaseView;
import com.example.weile.materialdesignexa.bean.GirlData;

import rx.Observable;

/**
 * Created by weile on 2017/2/22.
 */
public interface GirlPhotoListContract {
    interface ModelPhoto extends BaseModel{
        Observable<GirlData> getGirlList(int size,int page);
    }
    interface ViewPhoto extends BaseView{
        void refreshData(GirlData data);
        void loadData(GirlData data);
    }
    abstract class PresenterPhoto extends BasePresenter<ModelPhoto,ViewPhoto>{
        abstract void getGirlData(int size,int page,int i);
    }
}
