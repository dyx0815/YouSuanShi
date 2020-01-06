package com.dan.yousuanshi.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.dan.yousuanshi.utils.StatusBarUtil;

public abstract class MvpActivity<P extends MvpPresenter> extends FragmentActivity implements MvpView{
    public P presenter;

    /**
     * 获取布局资源文件
     */
    protected abstract int getLayoutId();

    /**
     * 初始化presenter
     */
    @Nullable
    protected abstract P initPresenter();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 绑定数据
     */
    protected abstract void loadData();

    protected void initWindow() {

    }

    protected void setStatusBar() {
        //这里做了两件事情，1.使状态栏透明并使contentView填充到状态栏 2.预留出状态栏的位置，防止界面上的控件离顶部靠的太近。这样就可以实现开头说的第二种情况的沉浸式状态栏了
        StatusBarUtil.setTransparent(this);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
        if (getLayoutId() > 0) {
            setContentView(getLayoutId());
        }
        setStatusBar();
        presenter = initPresenter();
        if (presenter != null) {
            presenter.attach(this);
        }
        initialize();
    }

    protected void initialize() {
        initView();
        loadData();
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.detach();
        }
        super.onDestroy();
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    protected Activity getActivity() {
        return this;
    }
}
