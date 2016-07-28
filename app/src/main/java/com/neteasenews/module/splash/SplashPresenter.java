package com.neteasenews.module.splash;

import java.util.Map;

/**
 * @author Yuan
 * @time 2016/7/26  11:04
 * @desc ${TODD}
 */
public class SplashPresenter extends SplashContract.Presenter {

    @Override
    public void getSplashAds(String url) {
        mRxManager.add(mModel.getSplashAds(url).subscribe(urlMap -> {
            if(urlMap==null){

            }
            mView.onSuccess(urlMap);
            for (Map.Entry<String, String> entry : urlMap.entrySet()) {
                download(entry.getKey());
            }
        }));
    }

    @Override
    public void download(String url) {
        mRxManager.add(mModel.download(url).subscribe());
    }

}
