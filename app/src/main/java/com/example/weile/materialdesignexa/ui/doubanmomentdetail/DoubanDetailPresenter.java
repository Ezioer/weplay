package com.example.weile.materialdesignexa.ui.doubanmomentdetail;

import com.example.weile.materialdesignexa.bean.DoubanMomentDetailBean;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by weile on 2017/1/16.
 */
public class DoubanDetailPresenter extends DoubanDetailContract.Presenter {
    public Observer<DoubanMomentDetailBean> getDetailContent(final String postid){
        return new Observer<DoubanMomentDetailBean>(){
            @Override
            public void onCompleted() {
                mView.RequestEnd();
            }

            @Override
            public void onError(Throwable e) {
                mView.Requesterror();
            }

            @Override
            public void onNext(DoubanMomentDetailBean doubanMomentDetailBean) {
                mView.refreshData(doubanMomentDetailBean);
            }
        };

    }
    @Override
    void getDetail(final String postid) {
        mView.RequestStart();
        rxManager.add(Observable.just(null)
        .flatMap(new Func1<Object, Observable<DoubanMomentDetailBean>>() {
            @Override
            public Observable<DoubanMomentDetailBean> call(Object o) {
                return mModel.getMomentDetail(postid);
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(getDetailContent(postid)));
    }
}
