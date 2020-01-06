package com.dan.yousuanshi.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class MvpFragment<T extends MvpPresenter> extends Fragment implements MvpView {
    protected T presenter;

    /**
     * 获取布局资源文件
     */
    protected abstract int getLayoutId();
    /**
     * 初始化presenter
     *
     * @return P
     */
    @Nullable
    protected abstract T initPresenter();
    /**
     * 绑定事件
     */
    protected abstract void initView();

    /**
     * 加载数据
     */
    protected abstract void loadData();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(),null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = initPresenter();
        if (presenter != null) {
            presenter.attach(this);
        }
        initialize();
    }

    @Override
    public void onDestroyView() {
        if (presenter != null) {
            presenter.detach();
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    protected void initialize() {
        initView();
        loadData();
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    public Fragment getFragment() {
        return this;
    }

}
