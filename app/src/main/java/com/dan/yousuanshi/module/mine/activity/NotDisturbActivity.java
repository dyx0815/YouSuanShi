package com.dan.yousuanshi.module.mine.activity;

import android.widget.Switch;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.base.MvpPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class NotDisturbActivity extends BaseActivity {

    @BindView(R.id.switch_allow)
    Switch switchAllow;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_not_disturb;
    }

    @Nullable
    @Override
    protected MvpPresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void loadData() {

    }

    @OnClick(R.id.ll_back)
    public void onViewClicked() {
        finish();
    }
}
