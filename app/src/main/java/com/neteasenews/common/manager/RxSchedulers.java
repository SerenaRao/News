package com.neteasenews.common.manager;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Yuan
 * @time 2016/7/26  16:59
 * @desc Rx线程调度类
 */
public class RxSchedulers {

    //从io线程切换到主线程
    public static <T> Observable.Transformer<T, T> io2Main() {
        return tObservable -> tObservable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
