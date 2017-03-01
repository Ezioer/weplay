package com.example.weile.materialdesignexa.ui.girlphoto;


import com.example.weile.materialdesignexa.bean.GirlData;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by weile on 2017/2/22.
 */
public class GirlPhotoListPresenter extends GirlPhotoListContract.PresenterPhoto {
    @Override
    void getGirlData(final int size, final int page, int type) {
        mView.RequestStart();
        rxManager.add(Observable.just(null)
        .flatMap(new Func1<Object, Observable<GirlData>>() {
            @Override
            public Observable<GirlData> call(Object o) {
                return mModel.getGirlList(size,page);
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(getList(type)));
    }

    public Observer<GirlData> getList(final int type) {
        return new Observer<GirlData>() {
            @Override
            public void onCompleted() {
                mView.RequestEnd();
            }

            @Override
            public void onError(Throwable e) {
                mView.Requesterror();
            }

            @Override
            public void onNext(GirlData girlData) {
                if(type==0){
                    mView.refreshData(girlData);
                }else {
                    mView.loadData(girlData);
                }
            }
        };
    }
}
