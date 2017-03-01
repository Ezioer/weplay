package com.example.weile.materialdesignexa.util;

/**
 * Created by weile on 2017/1/11.
 */
public class API {
    // 豆瓣一刻
    // 根据日期查询消息列表
    // eg:https://moment.douban.com/api/stream/date/2016-08-11
    public static final String DOUBAN_MOMENT = "https://moment.douban.com/api/";
    //图片
    public static final String SINA_PHOTO_HOST = "http://gank.io/api/";

    // 获取文章具体内容
    // eg:https://moment.douban.com/api/post/100484
    public static final String DOUBAN_ARTICLE_DETAIL = "https://moment.douban.com/api/";
    //获取轮播广告栏
    private final static String DONGTING_PHOTO = "http://api.dongting.com";
    private final static String BANNER="http://fengzone.applinzi.com/api/";
    public static String getType(int type){
        switch (type){
            case 1:
                return DOUBAN_MOMENT;
            case 2:
                return SINA_PHOTO_HOST;
            case 3:
                return DOUBAN_ARTICLE_DETAIL;
            case 4:
                return DONGTING_PHOTO;
            case 5:
                return BANNER;
        }
        return null;
    }

}
