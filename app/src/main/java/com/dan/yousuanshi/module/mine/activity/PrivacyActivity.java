package com.dan.yousuanshi.module.mine.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Switch;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.base.MvpPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class PrivacyActivity extends BaseActivity {

    @BindView(R.id.switch_allow)
    Switch switchAllow;
    @BindView(R.id.switch_shield)
    Switch switchShield;
    @BindView(R.id.switch_recommend)
    Switch switchRecommend;
    @BindView(R.id.switch_invite)
    Switch switchInvite;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_privacy;
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

    @OnClick({R.id.ll_back, R.id.ll_black_list})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_black_list:
                startActivity(new Intent(this,BlackListActivity.class));
                break;
        }
    }
}
