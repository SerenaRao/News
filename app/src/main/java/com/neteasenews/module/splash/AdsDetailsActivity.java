package com.neteasenews.module.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import com.neteasenews.R;
import com.neteasenews.module.news.activity.IndexActivity;

public class AdsDetailsActivity extends AppCompatActivity {


    public static final String ACTION_NAME = "link_url";
    private WebView mWebView;
    private TextView mTvTitle;
    private ImageButton mIbBack;
    private ImageButton mIbMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads_details);
        initViews();
        
        String lickUrl = (String) getIntent().getSerializableExtra(ACTION_NAME);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(lickUrl);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mTvTitle.setText(view.getTitle());
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                // TODO: 2016/6/25 网页加载失败是回调此方法
            }
        });
        
        mIbBack.setOnClickListener(v -> gotoIndexPage());
        
        mIbMore.setOnClickListener(v -> {
            // TODO: 2016/6/24
        });
    }

    private void initViews() {
        mIbBack = (ImageButton) findViewById(R.id.ib_cancel);
        mIbMore = (ImageButton) findViewById(R.id.ib_more);
        mWebView = (WebView) findViewById(R.id.webView);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        gotoIndexPage();
    }

    private void gotoIndexPage() {
        Intent intent = new Intent(this, IndexActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.page_in, R.anim.page_out);
        finish();
    }
}
