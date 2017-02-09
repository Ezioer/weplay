package com.example.weile.materialdesignexa.util;

import com.example.weile.materialdesignexa.bean.DoubanMomentDetailBean;
import com.example.weile.materialdesignexa.bean.DoubanMomentListBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by weile on 2017/1/12.
 */
public interface DoubanAPI {
    @GET("stream/date/{date1}")
    Observable<DoubanMomentListBean> getdoubanlist(@Path("date1") String date1);

    @GET("post/{postid}")
    Observable<DoubanMomentDetailBean> getDetail(@Path("postid") String postid);
}
