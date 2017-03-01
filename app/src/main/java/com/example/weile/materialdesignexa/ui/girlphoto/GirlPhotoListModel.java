package com.example.weile.materialdesignexa.ui.girlphoto;

import com.example.weile.materialdesignexa.basemvp.rx.RxSchedulers;
import com.example.weile.materialdesignexa.bean.GirlData;
import com.example.weile.materialdesignexa.util.RetrofitUtil;

import rx.Observable;

/**
 * Created by weile on 2017/2/22.
 */
public class GirlPhotoListModel implements GirlPhotoListContract.ModelPhoto {
    @Override
    public Observable<GirlData> getGirlList(int size,int page) {
        return RetrofitUtil.getInstance().getApi(2)
                .getPhotoList(size,page)
                .compose(RxSchedulers.<GirlData>io_main());
    }
}
