package com.neteasenews.module.news.activity;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.neteasenews.R;
import com.neteasenews.common.base.BaseActivity;
import com.neteasenews.module.mine.fragment.MineFragment;
import com.neteasenews.module.news.fragment.NewsFragment;
import com.neteasenews.module.read.fragment.ReadFragment;
import com.neteasenews.module.topic.fragment.TopicFragment;
import com.neteasenews.module.video.fragment.VideoFragment;
import com.neteasenews.widget.FragmentTabHost;

import butterknife.BindView;

public class IndexActivity extends BaseActivity {

    @BindView(R.id.tabhost)
    FragmentTabHost mTabhost;
    private long mExitTime;

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 2500) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_index;
    }

    @Override
    protected void initView() {

        Class[] fragments = {NewsFragment.class, ReadFragment.class, VideoFragment.class, TopicFragment.class, MineFragment.class};
        String[] tabTitle = getResources().getStringArray(R.array.bottom_tab_title);
        TypedArray tabIcons = getResources().obtainTypedArray(R.array.bottom_tab_icon);

        mTabhost = (FragmentTabHost) findViewById(R.id.tabhost);
        mTabhost.setup(this, getSupportFragmentManager(), R.id.fl_content);

        for (int i = 0; i < fragments.length; i++) {
            mTabhost.addTab(mTabhost.newTabSpec(tabTitle[i]).setIndicator(getTabItemView(tabTitle[i], tabIcons.getDrawable(i))), fragments[i], null);
        }
    }

    private View getTabItemView(String title, Drawable drawable) {
        View view = LayoutInflater.from(this).inflate(R.layout.bottom_tab, null);
        TextView textView = (TextView) view.findViewById(R.id.bottom_tab_title);
        textView.setText(title);
        ImageView imgView = (ImageView) view.findViewById(R.id.bottom_tab_image);
        imgView.setImageDrawable(drawable);
        return view;
    }

}
