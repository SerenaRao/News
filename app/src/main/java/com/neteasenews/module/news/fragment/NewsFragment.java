package com.neteasenews.module.news.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neteasenews.R;
import com.neteasenews.module.news.adapter.FragmentAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Yuan
 * @time 2016/7/27  17:57
 * @desc 一个简单的fragment,不使用mvp模式
 */
public class NewsFragment extends Fragment {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tabs)
    TabLayout mTabs;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String[] tabs = getResources().getStringArray(R.array.news_tips);
        mViewPager.setAdapter(new FragmentAdapter(getActivity().getSupportFragmentManager(), tabs));
        mTabs.setupWithViewPager(mViewPager);
    }
}
