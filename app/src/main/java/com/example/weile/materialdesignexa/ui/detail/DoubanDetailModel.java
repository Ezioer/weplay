package com.example.weile.materialdesignexa.ui.detail;

import com.example.weile.materialdesignexa.basemvp.rx.RxSchedulers;
import com.example.weile.materialdesignexa.bean.DoubanMomentDetailBean;
import com.example.weile.materialdesignexa.util.RetrofitUtil;

import rx.Observable;

/**
 * Created by weile on 2017/1/16.
 */
public class DoubanDetailModel implements DoubanDetailContract.Model{
    @Override
    public Observable<DoubanMomentDetailBean> getMomentDetail(String id) {
        return RetrofitUtil.getInstance().getApi(3)
                .getDetail(id)
                .compose(RxSchedulers.<DoubanMomentDetailBean>io_main());
    }
}
