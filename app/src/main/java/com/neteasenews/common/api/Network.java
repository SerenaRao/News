package com.neteasenews.common.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.neteasenews.common.config.App;
import com.neteasenews.common.config.C;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Yuan
 * @time 2016/7/26  1:07
 * @desc ${TODD}
 */
public class Network {
    public static final int DEFAULT_TIMEOUT = 5;

    public Retrofit mRetrofit;
    public ApiService mService;

    //私有化构造方法
    private Network() {
        File cacheFile = new File(App.getAppContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 20);  //20Mb

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .cache(cache)
                .build();

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls().create();

        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(C.BASE_URL)
                .build();

        mService = mRetrofit.create(ApiService.class);
    }

    //在访问时创建单例
    private static class SingletonHolder {
        private static final Network INSTANCE = new Network();
    }

    //获取单例
    public static Network getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
