package com.neteasenews.common.base;

import com.neteasenews.common.manager.RxManager;

/**
 * @author Yuan
 * @time 2016/7/26  1:00
 * @desc ${TODD}
 */
public abstract class BasePresenter<M, V> {

    public M mModel;
    public V mView;
    public RxManager mRxManager = new RxManager();

    public void setMV(M model, V view) {
        mModel = model;
        mView = view;
        this.onStart();
    }

    public void onDestroy() {
        mRxManager.clear();
    }

    protected abstract void onStart();
}
