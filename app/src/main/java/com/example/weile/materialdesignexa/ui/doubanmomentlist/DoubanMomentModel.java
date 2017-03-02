package com.example.weile.materialdesignexa.ui.doubanmomentlist;

import com.example.weile.materialdesignexa.basemvp.rx.RxSchedulers;
import com.example.weile.materialdesignexa.bean.DoubanMomentListBean;
import com.example.weile.materialdesignexa.util.RetrofitUtil;

import rx.Observable;

/**
 * Created by weile on 2017/1/12.
 */
public class DoubanMomentModel implements DoubanMomentListContract.Model {
    @Override
    public Observable<DoubanMomentListBean> getMomentList(String date) {
        return RetrofitUtil.getInstance().getApi(1)
                .getdoubanlist(date)
                .compose(RxSchedulers.<DoubanMomentListBean>io_main());
    }
}
