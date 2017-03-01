package com.example.weile.materialdesignexa.util;

import com.example.weile.materialdesignexa.MyApplication;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by weile on 2017/1/12.
 */
public class RetrofitUtil {
    private static RetrofitAPI mRetrofitApi;
    private static Retrofit mRetrofit;

    private RetrofitUtil() {
    }

    private static RetrofitUtil mRetrofitUtil = null;

    public static RetrofitUtil getInstance() {
        if(mRetrofit==null){
            synchronized (RetrofitUtil.class){
                if (mRetrofitUtil == null) {
                    mRetrofitUtil = new RetrofitUtil();
                }
            }
        }
        return mRetrofitUtil;
    }

    public RetrofitAPI getApi(int type) {
        return mRetrofitApi == null ? configRetrofit(RetrofitAPI.class, false,type) : mRetrofitApi;
    }
    private <T> T configRetrofit(Class<T> service, boolean istake,int type) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(API.getType(type))
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return mRetrofit.create(service);
    }

    private OkHttpClient getOkHttpClient() {
        //定制OkHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        //设置超时时间
        httpClientBuilder.connectTimeout(10000, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(10000, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(10000, TimeUnit.SECONDS);
        //设置缓存
        File httpCacheDirectory = new File((Utils.getCacheDir(MyApplication.getInstance())),
                "OkHttpCache");
        httpClientBuilder.cache(new Cache(httpCacheDirectory, 10 * 1024 * 1024));
        //添加拦截器,读取缓存的规则
        httpClientBuilder.addInterceptor(interceptor);
        return httpClientBuilder.build();
    }

    //拦截器
    Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetworkTools.isNetworkAvailable(MyApplication.mContext)) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }

            Response response = chain.proceed(request);
            if (NetworkTools.isNetworkAvailable(MyApplication.mContext)) {
                int maxAge = 60 * 60; // 网络可用状态下保存1个小时
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // 网络不可用时保存1个月
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    };
}
