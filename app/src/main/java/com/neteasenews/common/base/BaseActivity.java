package com.neteasenews.common.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.neteasenews.common.util.TUtil;

import butterknife.ButterKnife;

/**
 * @author Yuan
 * @time 2016/7/26  1:04
 * @desc ${TODD}
 */
public abstract class BaseActivity<P extends BasePresenter, M extends BaseModel>
        extends AppCompatActivity {

    public P mPresenter;
    public M mModel;
    public Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(this.getLayoutId());
        ButterKnife.bind(this);
        mContext = this;
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (this instanceof BaseView) {
            mPresenter.setMV(mModel, this);
    }
        this.initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }

    protected abstract int getLayoutId();

    protected abstract void initView();
}
