package com.neteasenews.module.news.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.neteasenews.common.util.ImageUtil;
import com.neteasenews.module.news.bean.AdsBean;

import java.util.List;

/**
 * @author Yuan
 * @time 2016/6/26  20:07
 * @desc ${TODD}
 */
public class HotPagerAdapter extends PagerAdapter {
    private List<ImageView> mViews;
    private List<AdsBean> mPagerData;
    //图片加载器的显示配置

    public HotPagerAdapter(List<ImageView> views, List<AdsBean> pagerData) {
        mViews = views;
        mPagerData = pagerData;
    }

    @Override
    public int getCount() {
        return mPagerData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //int position = p % mViews.size();
        ImageView imageView = mViews.get(position);
        AdsBean tmpData = mPagerData.get(position);
        ImageUtil.loadImg(imageView, tmpData.getImgsrc());
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //int position = p % mViews.size();
        container.removeView(mViews.get(position));
    }
}
