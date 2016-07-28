package com.neteasenews.common.manager;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * @author Yuan
 * @time 2016/7/26  15:50
 * @desc 用RxJava实现的EventBus
 */
public class RxBus {

    private static RxBus instance;

    private ConcurrentHashMap<Object, List<Subject>> subjectManager = new ConcurrentHashMap<>();

    private RxBus() {
    }

    public static synchronized RxBus $() {
        if (instance == null) {
            instance = new RxBus();
        }
        return instance;
    }

    //订阅事件源
    public RxBus OnEvent(Observable<?> mObservable, Action1<Object> mAction1) {
        mObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(mAction1, (e) -> e.printStackTrace());
        return $();
    }

    //注册事件源
    public <T> Observable<T> register(@Nullable Object tag) {
        List<Subject> subjectList = subjectManager.get(tag);
        if (subjectList == null) {
            subjectList = new ArrayList<>();
            subjectManager.put(tag, subjectList);
        }
        Subject<T, T> subject;
        subjectList.add(subject = PublishSubject.create());
        return subject;
    }

    //注销
    public void unregister(@Nullable Object tag) {
        List<Subject> subjectList = subjectManager.get(tag);
        if (subjectList != null) {
            subjectManager.remove(tag);
        }
    }

    //注销事件源
    public RxBus unregister(@Nullable Object tag, @Nullable Observable<?> observable) {
        if (observable == null) {
            return $();
        }
        List<Subject> subjectList = subjectManager.get(tag);
        if (subjectList != null) {
            subjectList.remove((Subject<?, ?>) observable);
            if (!isEmpty(subjectList)) {
                subjectManager.remove(tag);
            }
        }
        return $();
    }

    //事件触发
    public void post(@NonNull Object content) {
        post(content.getClass().getName(), content);
    }

    @SuppressWarnings("unchecked")
    public void post(@NonNull Object tag, @NonNull Object content) {
        List<Subject> subjectList = subjectManager.get(tag);
        if (isEmpty(subjectList)) {
            for (Subject subject : subjectList) {
                subject.onNext(content);
            }
        }
    }

    private boolean isEmpty(Collection<Subject> collection) {
        return collection == null || collection.isEmpty();
    }

}
