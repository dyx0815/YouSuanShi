package com.dan.yousuanshi.module.mine.activity;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.base.MvpPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class AboutMeActivity extends BaseActivity {

    @BindView(R.id.tv_version)
    TextView tvVersion;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_me;
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

    @OnClick({R.id.ll_back, R.id.ll_check_version, R.id.ll_manual, R.id.ll_service_agreement, R.id.ll_privacy_policy, R.id.ll_information})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                break;
            case R.id.ll_check_version:
                break;
            case R.id.ll_manual:
                break;
            case R.id.ll_service_agreement:
                break;
            case R.id.ll_privacy_policy:
                break;
            case R.id.ll_information:
                break;
        }
    }
}
