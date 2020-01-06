package com.dan.yousuanshi.base;

import android.os.Bundle;

import com.dan.yousuanshi.utils.LoadingDialog;
import com.dan.yousuanshi.utils.StatusBarUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<P extends BasePresenter> extends MvpActivity<P>{
    private LoadingDialog mLoadingDialog = null;
//    private LoadingBarManager mLoadingBarManager = null;
    private Unbinder mUnbinder = null;
//    private LoginReceiver mLoginReceiver;


    @Override
    protected void initialize() {
        mUnbinder = ButterKnife.bind(this);
        super.initialize();
    }

    @Override
    protected void onDestroy() {
        clearLoading();
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    @Override
    public void showLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog.with(getContext());
        }
        mLoadingDialog.show();
    }

    @Override
    public void dismissLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }
    @Override
    public void showLoadingBar() {
//        if (mLoadingBarManager == null) {
//            mLoadingBarManager = LoadingBarManager.attach(getWindow().getDecorView());
//        }
//        mLoadingBarManager.show();
    }

    @Override
    public void dismissLoadingBar() {
//        if (mLoadingBarManager != null) {
//            mLoadingBarManager.dismiss();
//        }
    }

    @Override
    public void clearLoading() {
//        if (mLoadingBarManager != null) {
//            mLoadingBarManager.clear();
//            mLoadingBarManager.detach();
//        }
//        mLoadingBarManager = null;
        if (mLoadingDialog != null) {
            mLoadingDialog.clear();
        }
        mLoadingDialog = null;
    }
}
