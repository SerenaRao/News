package com.neteasenews.common.api;

import com.neteasenews.common.config.C;
import com.neteasenews.module.news.bean.NewsResult;
import com.neteasenews.module.splash.bean.SplashResult;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * @author Yuan
 * @time 2016/7/26  1:06
 * @desc ${TODD}
 */
public interface ApiService {

    /**
     * @return 获取Splash页面的广告信息
     */
    @GET
    Observable<SplashResult> getSplashAds(@Url String url);

    /**
     * 根据url下载图片到本地
     *
     * @param url
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);

    @GET(C.NEWS_URL)
    Observable<NewsResult> getNewsData(@Path("type") String type, @Path("start") int start, @Path("end") int end);

}
