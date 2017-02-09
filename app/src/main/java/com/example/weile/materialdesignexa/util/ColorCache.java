package com.example.weile.materialdesignexa.util;

import android.util.LruCache;

/**
 * Created by weile on 2016/11/17.
 */
public class ColorCache {
    private static ColorCache instance=null;
    private LruCache<Long,int[]> lru;
    private ColorCache(){
        lru=new LruCache<>(1024);
    }
    public static ColorCache getinstance(){
        if(instance==null){
            instance=new ColorCache();
        }
        return instance;
    }
    public LruCache<Long,int[]> getLru(){
        return lru;
    }
}
