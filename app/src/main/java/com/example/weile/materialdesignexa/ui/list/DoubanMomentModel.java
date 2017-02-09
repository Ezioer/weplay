package com.example.weile.materialdesignexa.ui.list;

import com.example.weile.materialdesignexa.basemvp.rx.RxSchedulers;
import com.example.weile.materialdesignexa.bean.DoubanMomentListBean;
import com.example.weile.materialdesignexa.util.NetUtil;

import rx.Observable;

/**
 * Created by weile on 2017/1/12.
 */
public class DoubanMomentModel implements DoubanMomentListContract.Model {
    @Override
    public Observable<DoubanMomentListBean> getMomentList(String date) {
        return NetUtil.getInstance().getDoubanApi()
                .getdoubanlist(date)
                .compose(RxSchedulers.<DoubanMomentListBean>io_main());
    }
}
