package com.example.weile.materialdesignexa.util;

import com.example.weile.materialdesignexa.bean.AdBean;
import com.example.weile.materialdesignexa.bean.DoubanMomentDetailBean;
import com.example.weile.materialdesignexa.bean.DoubanMomentListBean;
import com.example.weile.materialdesignexa.bean.GirlData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by weile on 2017/1/12.
 */
public interface RetrofitAPI {
    @GET("stream/date/{date1}")
    Observable<DoubanMomentListBean> getdoubanlist(@Path("date1") String date1);

    @GET("post/{postid}")
    Observable<DoubanMomentDetailBean> getDetail(@Path("postid") String postid);
    @GET("data/福利/{size}/{page}")
    Observable<GirlData> getPhotoList(@Path("size") int size, @Path("page") int page);
    @GET("http://fengzone.applinzi.com/api/banner.php")
    Observable<AdBean> getAdData();
}
