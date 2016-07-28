package com.neteasenews.common.config;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

/**
 * @author Yuan
 * @time 2016/7/26  17:07
 */
public class App extends Application {

    private static App mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }

    public static Context getAppContext() {
        return mApp;
    }

    public static Resources getAppResources() {
        return mApp.getResources();
    }

}
