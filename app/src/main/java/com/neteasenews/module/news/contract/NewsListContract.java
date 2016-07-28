package com.neteasenews.module.news.contract;

import com.neteasenews.common.base.BaseModel;
import com.neteasenews.common.base.BasePresenter;
import com.neteasenews.common.base.BaseView;
import com.neteasenews.module.news.bean.NewsList;

import java.util.List;

import rx.Observable;

/**
 * @author Yuan
 * @time 2016/7/27  18:51
 * @desc ${TODD}
 */
public interface NewsListContract {
    interface Model extends BaseModel {
        Observable<List<NewsList>> getNewsData(String type, int start, int end);
    }

    interface View extends BaseView {
        void onComplete(List<NewsList> headlineData);

        void onError();
    }

    abstract class Presenter extends BasePresenter<Model, View> {

        protected abstract void getNewsData(String type, int start, int end);

        @Override
        protected void onStart() {

        }
    }
}
