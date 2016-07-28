package com.neteasenews.module.news.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neteasenews.R;
import com.neteasenews.common.base.BaseFragment;
import com.neteasenews.common.config.App;
import com.neteasenews.common.config.C;
import com.neteasenews.module.news.adapter.HotPagerAdapter;
import com.neteasenews.module.news.adapter.RvAdapter;
import com.neteasenews.module.news.bean.AdsBean;
import com.neteasenews.module.news.bean.NewsList;
import com.neteasenews.module.news.contract.NewsListContract;
import com.neteasenews.module.news.model.NewsListModel;
import com.neteasenews.module.news.presenter.NewsListPresenter;
import com.neteasenews.widget.YRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Yuan
 * @time 2016/7/27  18:03
 * @desc ${TODD}
 */
public class NewsListFragment extends BaseFragment<NewsListPresenter, NewsListModel>
        implements NewsListContract.View, YRecyclerView.OnLoadingListener {

    @BindView(R.id.y_recycler_view)
    YRecyclerView mYRecyclerView;
    @BindView(R.id.retry_view)
    LinearLayout mRetryView;
    private List<NewsList> mData = new ArrayList<>();
    private List<ImageView> mImages = new ArrayList<>();
    private List<ImageView> mDots = new ArrayList<>();
    private int mStartIndex;
    private View mHeadView;
    private static String[] typeList = App.getAppContext().getResources().getStringArray(R.array.sub_url);
    private String mType;

    public static NewsListFragment newInstance(int position) {

        Bundle arguments = new Bundle();
        arguments.putString("type", typeList[position]);
        NewsListFragment fragment = new NewsListFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initView() {
        mHeadView = LayoutInflater.from(mContext).inflate(R.layout.hot_ads_viewpager, null);
        View footerView = LayoutInflater.from(mContext).inflate(R.layout.loading_more_footer, null);
        footerView.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        mYRecyclerView.setLayoutManager(new LinearLayoutManager(mContext))
                .addFooterView(footerView)
                .showLoadingView();
        mType = getArguments().getString("type");
        mPresenter.getNewsData(mType, mStartIndex, mStartIndex + C.PAGE_SIZE);
    }

    @Override
    public void onComplete(List<NewsList> headlineData) {
        if (mStartIndex == 0) {
            List<AdsBean> ads = headlineData.get(0).getAds();
            if (ads != null && ads.size() > 0) {
                getPagerData(ads);
                mYRecyclerView.addHeaderView(mHeadView);
            }
            headlineData.remove(headlineData.get(0));
        }
        mStartIndex += C.PAGE_SIZE;
        mYRecyclerView.setAdapter(new RvAdapter(mData))
                .setItemClickListener((parent, view, position) -> {

                }).setLoadingListener(this);
        mData.addAll(headlineData);
        mYRecyclerView.loadComplete();
    }

    @Override
    public void onError() {

    }

    @Override
    public void onRefreshLoading() {
        mStartIndex = 0;
        mData.clear();
        mPresenter.getNewsData(mType, mStartIndex, mStartIndex + C.PAGE_SIZE);
    }

    @Override
    public void onMoreLoading() {
        mPresenter.getNewsData(mType, mStartIndex, mStartIndex + C.PAGE_SIZE);
    }

    public void getPagerData(List<AdsBean> ads) {
        ViewPager pager = (ViewPager) mHeadView.findViewById(R.id.hot_ads_viewPager);
        LinearLayout dots = (LinearLayout) mHeadView.findViewById(R.id.ll_dots);
        dots.removeAllViews();
        mImages.clear();
        mDots.clear();
        final TextView pagerTitle = (TextView) mHeadView.findViewById(R.id.hot_pager_title);
        for (int i = 0; i < ads.size(); i++) {
            //生成显示图片的imageView
            ImageView imgView = new ImageView(mContext);
            imgView.setScaleType(ImageView.ScaleType.FIT_XY);
            mImages.add(imgView);
            //生成圆点imageView
            ImageView dotView = new ImageView(mContext);
            dotView.setImageResource(R.drawable.dot_gray);
            dotView.setPadding(8, 0, 8, 0);
            dots.addView(dotView);//将dotView添加到LinearLayout容器
            mDots.add(dotView);
        }
        //默认设置第一个dot为白色, 设置TextView显示为第一个title的内容
        pagerTitle.setText(ads.get(0).getTitle());
        mDots.get(0).setImageResource(R.drawable.dot_white);
        HotPagerAdapter pagerAdapter = new HotPagerAdapter(mImages, ads);
        pager.setAdapter(pagerAdapter);
        int initPosition = 1000 * mImages.size();
        //pager.setCurrentItem(initPosition);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //int index = position % mImages.size();
                pagerTitle.setText(ads.get(position).getTitle());
                for (int i = 0; i < mImages.size(); i++) {
                    ImageView dot = mDots.get(i);
                    if (i == position) {
                        dot.setImageResource(R.drawable.dot_white);
                    } else {
                        dot.setImageResource(R.drawable.dot_gray);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
