package com.dan.yousuanshi.base;

import com.dan.yousuanshi.utils.LoadingDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<P extends BasePresenter> extends MvpFragment<P> {
    private LoadingDialog mLoadingDialog = null;
    private Unbinder mUnbinder = null;

    @Override
    protected void initialize() {
        mUnbinder = ButterKnife.bind(this,getView());
        super.initialize();
    }

    @Override
    public void onDestroyView() {
        clearLoading();
        super.onDestroyView();
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
    public void clearLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.clear();
        }
        mLoadingDialog = null;
    }

    public void finish() {
        if (getActivity() != null) {
            getActivity().finish();
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
}
