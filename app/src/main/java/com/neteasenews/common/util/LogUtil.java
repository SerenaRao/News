package com.neteasenews.common.util;

import android.util.Log;

import com.neteasenews.BuildConfig;


/**
 * @author Yuan
 * @time 2016/7/26  17:16
 * @desc 控制是否输出日志
 */
public class LogUtil {

    public static void d(String tag, String data){
        if(BuildConfig.LOG_DEBUG){
            Log.d(tag, data);
        }
    }
}
