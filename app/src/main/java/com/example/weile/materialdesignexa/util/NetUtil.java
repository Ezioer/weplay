package com.example.weile.materialdesignexa.util;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by weile on 2017/1/12.
 */
public class NetUtil {
    private static DoubanAPI mDoubanApi;
    private static Retrofit mRetrofit;

    private NetUtil() {
    }

    private static NetUtil mNetUtil = null;

    public static NetUtil getInstance() {
        if (mNetUtil == null) {
            mNetUtil = new NetUtil();
        }
        return mNetUtil;
    }

    public DoubanAPI getDoubanApi() {
        return mDoubanApi == null ? configRetrofit(DoubanAPI.class,false) : mDoubanApi;
    }

    private <T> T configRetrofit(Class<T> service, boolean istake) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(API.DOUBAN_MOMENT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return mRetrofit.create(service);
    }

}
