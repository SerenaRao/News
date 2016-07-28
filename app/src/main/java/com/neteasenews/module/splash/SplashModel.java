package com.neteasenews.module.splash;

import com.neteasenews.common.api.Network;
import com.neteasenews.common.config.C;
import com.neteasenews.common.manager.RxSchedulers;
import com.neteasenews.common.util.ImageUtil;
import com.neteasenews.common.util.SpUtils;
import com.neteasenews.module.splash.bean.AdsBean;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Yuan
 * @time 2016/7/26  11:03
 * @desc ${TODD}
 */
public class SplashModel implements SplashContract.Model {

    @Override
    public Observable<Map<String, String>> getSplashAds(String url) {
        return Network.getInstance().mService
                .getSplashAds(url).map(splashResult -> {
                    SpUtils.putLong(C.RECONNECT_TIME, splashResult.getNext_req() * 60 * 1000);
                    Map<String, String> map = new HashMap<>();
                    for (AdsBean ad : splashResult.getAds()) {
                        String imgUrl = ad.getRes_url().get(0);
                        String actionUrl = ad.getAction_params().getLink_url();
                        map.put(imgUrl, actionUrl);
                    }
                    SpUtils.putLong(C.LAST_CONNECT_TIME, System.currentTimeMillis());
                    return map;
                }).compose(RxSchedulers.io2Main());
    }

    @Override
    public Observable download(String imgUrl) {
        return Network.getInstance().mService
                .download(imgUrl)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(ResponseBody::byteStream)
                .observeOn(Schedulers.computation())
                .doOnNext(inputStream -> {
                    try {
                        ImageUtil.saveImg2Disk(inputStream, imgUrl);
                    } catch (IOException e) {
                        throw new RuntimeException(e.getMessage());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

}
