package com.example.weile.materialdesignexa.basemvp;

/**
 * Created by weile on 2017/1/12.
 */
public interface BaseView {
    void RequestStart();
    void Requesterror();
    void RequestEnd();
    void NetError();
}
