package com.neteasenews.module.news.presenter;

import com.neteasenews.module.news.contract.NewsListContract;

/**
 * @author Yuan
 * @time 2016/7/27  18:57
 * @desc ${TODD}
 */
public class NewsListPresenter extends NewsListContract.Presenter {

    @Override
    public void getNewsData(String type, int start, int end) {
        mRxManager.add(mModel.getNewsData(type,start,end).subscribe(headlines -> {
            mView.onComplete(headlines);
        }, throwable -> {
            throwable.printStackTrace();
            mView.onError();
        }));
    }
}
