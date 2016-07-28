package com.neteasenews.module.news.model;

import com.neteasenews.common.api.Network;
import com.neteasenews.common.manager.RxSchedulers;
import com.neteasenews.module.news.bean.NewsList;
import com.neteasenews.module.news.contract.NewsListContract;

import java.util.List;

import rx.Observable;

/**
 * @author Yuan
 * @time 2016/7/27  18:52
 * @desc ${TODD}
 */
public class NewsListModel implements NewsListContract.Model {
    @Override
    public Observable<List<NewsList>> getNewsData(String type, int start, int end) {
        return Network.getInstance().mService
                .getNewsData(type, start, end)
                .map(newsResult -> {
                    switch (type) {
                        case "T1467284926140":
                            return newsResult.T1467284926140;
                        case "T1348648517839":
                            return newsResult.T1348648517839;
                        case "T1348649079062":
                            return newsResult.T1348649079062;
                        case "T1348648756099":
                            return newsResult.T1348648756099;
                        case "T1348649580692":
                            return newsResult.T1348649580692;
                        case "T1348650593803":
                            return newsResult.T1348650593803;
                        case "T1350383429665":
                            return newsResult.T1350383429665;
                        case "T1348648141035":
                            return newsResult.T1348648141035;
                        case "T1368497029546":
                            return newsResult.T1368497029546;
                        case "T1348654105308":
                            return newsResult.T1348654105308;
                        case "T1370583240249":
                            return newsResult.T1370583240249;
                        case "T1348654151579":
                            return newsResult.T1348654151579;
                        case "T1414389941036":
                            return newsResult.T1414389941036;
                        case "T1414142214384":
                            return newsResult.T1414142214384;
                        case "T1356600029035":
                            return newsResult.T1356600029035;
                        default:
                            return newsResult.T1348647909107;
                    }
                })
                .compose(RxSchedulers.io2Main());
    }
}
