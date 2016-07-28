package com.neteasenews.module.news.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.neteasenews.common.util.LogUtil;
import com.neteasenews.module.news.fragment.NewsListFragment;


/**
 * @author Yuan
 * @time 2016/7/23  0:26
 * @desc ${TODD}
 */
public class FragmentAdapter extends FragmentStatePagerAdapter {
    private String[] mTitles;

    public FragmentAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        LogUtil.d("FragmentAdapter", position + "");
        return NewsListFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }

    /*
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vpContainer = (ViewPager) container;
        View view = vpContainer.getChildAt(position);
        if (view != null) {
            ImageView imgView = (ImageView) view.findViewById(R.id.image_iv);
            releaseImageViewResource(imgView);
        }
    }

    private void releaseImageViewResource(ImageView imgView) {
        if (imgView == null) {
            return;
        }
        Drawable drawable = imgView.getDrawable();
        if (drawable != null && drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            imgView.setImageDrawable(null);
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
        System.gc();
    }*/
}
