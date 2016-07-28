package com.neteasenews.module.splash;


import com.neteasenews.common.base.BaseModel;
import com.neteasenews.common.base.BasePresenter;
import com.neteasenews.common.base.BaseView;

import java.util.Map;

import rx.Observable;

/**
 * @author Yuan
 * @time 2016/7/26  11:03
 * @desc ${TODD}
 */
public interface SplashContract {
    interface Model extends BaseModel {

        Observable<Map<String, String>> getSplashAds(String url);

        Observable download(String url);
    }

    interface View extends BaseView {
        void onSuccess(Map<String, String> urlMap);

    }

    abstract class Presenter extends BasePresenter<Model, View> {

        public abstract void getSplashAds(String url);

        public abstract void download(String url);

        @Override
        protected void onStart() {
        }
    }
}
