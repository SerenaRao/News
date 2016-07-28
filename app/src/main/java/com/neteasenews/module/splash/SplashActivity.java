package com.neteasenews.module.splash;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.neteasenews.R;
import com.neteasenews.common.base.BaseActivity;
import com.neteasenews.common.config.C;
import com.neteasenews.common.util.ActivityUtil;
import com.neteasenews.common.util.ImageUtil;
import com.neteasenews.common.util.SpUtils;
import com.neteasenews.module.news.activity.IndexActivity;
import com.neteasenews.widget.RingTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class SplashActivity extends BaseActivity<SplashPresenter, SplashModel> implements SplashContract.View {

    @BindView(R.id.iv_ads_image)
    ImageView mIvAdsImage;
    @BindView(R.id.rtv_ring)
    RingTextView mRtvRing;

    private int mAdsTime = 5000;
    private int mIndex;
    private Subscription mSubscribe;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        String adsUrlStr = SpUtils.getString(C.ADS_URLS, "");
        String[] adsUrls = adsUrlStr.split("-");
        String actionUrlStr = SpUtils.getString(C.ACTION_URLS, "");
        String[] actionUrls = actionUrlStr.split("-");
        long lastConnTime = SpUtils.getLong(C.LAST_CONNECT_TIME, 0);
        mIndex = SpUtils.getInt(C.ADS_IMAGE_INDEX, 0);
        mRtvRing.setShowTime(mAdsTime);

        if (TextUtils.isEmpty(adsUrlStr) || System.currentTimeMillis() - lastConnTime > 600 * 60 * 1000) {
            mPresenter.getSplashAds(C.SPLASH_URL);
            mAdsTime = 2000;
        } else {
            ImageUtil.loadImgFromLocal(mIvAdsImage, adsUrls[mIndex % adsUrls.length]);
        }
        mSubscribe = Observable.timer(mAdsTime, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    ActivityUtil.start(this, IndexActivity.class, true);
                });

        mRtvRing.setOnClickListener(view -> {
            ActivityUtil.start(this, IndexActivity.class, true);
            mSubscribe.unsubscribe();
        });

        mIvAdsImage.setOnClickListener(view -> {
            mSubscribe.unsubscribe();
            if (!TextUtils.isEmpty(actionUrls[mIndex % actionUrls.length])) {
                startActivity(new Intent(SplashActivity.this, AdsDetailsActivity.class)
                        .putExtra(AdsDetailsActivity.ACTION_NAME, actionUrls[mIndex % actionUrls.length]));
                finish();
            }
        });

        SpUtils.putInt(C.ADS_IMAGE_INDEX, ++mIndex);
    }


    @Override
    public void onSuccess(Map<String, String> urlMap) {
        List<String> adsUrls = new ArrayList<>();
        List<String> actionUrls = new ArrayList<>();
        Log.d("SplashActivity", "onSuccess: " + urlMap.toString());

        for (Map.Entry<String, String> entry : urlMap.entrySet()) {
            String key = entry.getKey();
            mPresenter.download(key);
            adsUrls.add(key);
            actionUrls.add(entry.getValue());
        }
        SpUtils.putString(C.ADS_URLS, TextUtils.join("-", adsUrls));
        SpUtils.putString(C.ACTION_URLS, TextUtils.join("-", actionUrls));
        SpUtils.putLong(C.LAST_CONNECT_TIME, System.currentTimeMillis());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscribe.unsubscribe();
    }
}
