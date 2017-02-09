package com.example.weile.materialdesignexa.ui.list;

import com.example.weile.materialdesignexa.bean.DoubanMomentListBean;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by weile on 2017/1/12.
 */
public class DoubanMomentPresenter extends DoubanMomentListContract.Presenter {
    public Observer<DoubanMomentListBean> getTopicListObserver(final int type) {
        return new Observer<DoubanMomentListBean>() {
            @Override
            public void onCompleted() {
                mView.RequestEnd();
            }

            @Override
            public void onError(Throwable e) {
                mView.Requesterror();
                mView.NetError();
            }

            @Override
            public void onNext(DoubanMomentListBean topicListEntity) {
                if(type==0){
                    mView.refreshData(topicListEntity);
                }else if(type==1){
                    mView.loadData(topicListEntity);
                }

            }
        };
    }

    @Override
    void getDoubanMomentList(final String date,int type) {
        rxManager.add(Observable.just(null)
                .flatMap(new Func1<Object, Observable<DoubanMomentListBean>>() {
                    @Override
                    public Observable<DoubanMomentListBean> call(Object o) {
                        return mModel.getMomentList(date);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getTopicListObserver(type)));
    }
}
