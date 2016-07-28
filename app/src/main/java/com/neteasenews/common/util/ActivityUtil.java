package com.neteasenews.common.util;

import android.app.Activity;
import android.content.Intent;

import com.neteasenews.R;

/**
 * @author Yuan
 * @time 2016/7/10  13:03
 * @desc ${TODD}
 */
public class ActivityUtil {

    public static void start(Activity activity, Class target, boolean isFinish) {
        Intent intent = new Intent(activity, target);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.page_in, R.anim.page_out);
        if (isFinish) {
            activity.finish();
        }
    }
}
