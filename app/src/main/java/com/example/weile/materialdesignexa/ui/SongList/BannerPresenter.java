package com.example.weile.materialdesignexa.ui.SongList;

import com.example.weile.materialdesignexa.basemvp.rx.RxManager;
import com.example.weile.materialdesignexa.basemvp.rx.RxSchedulers;
import com.example.weile.materialdesignexa.bean.AdBean;
import com.example.weile.materialdesignexa.util.RetrofitUtil;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by weile on 2017/3/2.
 */
public class BannerPresenter {
    private BannerView mCallBack;
    public BannerPresenter(BannerView mCallBack){
        this.mCallBack=mCallBack;
    }
   public void getBannerList(){
       mCallBack.RequestStart();
       new RxManager().add(Observable.just(null)
               .flatMap(new Func1<Object, Observable<AdBean>>() {
                   @Override
                   public Observable<AdBean> call(Object o) {
                       return RetrofitUtil.getInstance().getApi(4).getAdData().compose(RxSchedulers.<AdBean>io_main());
                   }
               })
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Observer<AdBean>() {
                   @Override
                   public void onCompleted() {
                       mCallBack.RequestEnd();
                   }

                   @Override
                   public void onError(Throwable e) {
                       mCallBack.Requesterror();
                   }

                   @Override
                   public void onNext(AdBean adBean) {
                       mCallBack.refreshDada(adBean);
                   }
               }));
    }
}
