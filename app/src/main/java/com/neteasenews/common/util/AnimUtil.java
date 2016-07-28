package com.neteasenews.common.util;

import android.view.animation.Animation;

/**
 * @author Yuan
 * @time 2016/7/26  18:28
 * @desc 监听动画结束的工具类
 */
public class AnimUtil {

    public static void setAnimationListener(Animation anim, final AnimListener listener) {

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                listener.onAnimFinish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    public interface AnimListener {
        void onAnimFinish();
    }
}
