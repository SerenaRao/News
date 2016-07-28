package com.neteasenews.common.manager;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * @author Yuan
 * @time 2016/7/26  16:37
 * @desc 用于管理RxRus的事件和Rxjava相关代码的生命周期
 */
public class RxManager {

    public RxBus mRxBus = RxBus.$();
    //管理观察源
    private Map<String, Observable<?>> mObservableMap = new HashMap<>();
    //管理订阅者
    private CompositeSubscription mSubscription = new CompositeSubscription();

    public void on(String eventName, Action1<Object> action1) {
        Observable<?> mObservable = mRxBus.register(eventName);
        mObservableMap.put(eventName, mObservable);
        mSubscription.add(mObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action1, (e) -> e.printStackTrace()));
    }

    public void add(Subscription m) {
        mSubscription.add(m);
    }

    public void clear() {
        mSubscription.unsubscribe();//取消订阅
        for (Map.Entry<String, Observable<?>> entry : mObservableMap.entrySet()) {
            mRxBus.unregister(entry.getKey(), entry.getValue());//移除观察
        }
    }

    public void post(Object tag, Object content) {
        mRxBus.post(tag, content);
    }
}
