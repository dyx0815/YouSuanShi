package com.dan.yousuanshi.module.addressbook.activity;

import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.base.MvpPresenter;
import com.dan.yousuanshi.base.MyApplication;

import butterknife.OnClick;

public class AdminSettingActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_admin_setting;
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

    @OnClick({R.id.ll_back, R.id.rl_update_first_admin, R.id.rl_update_son_admin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.rl_update_first_admin:
                MyApplication.addActivity(this);
                startActivity(new Intent(this,UpdateAdminActivity.class));
                break;
            case R.id.rl_update_son_admin:
                startActivity(new Intent(this,SettingSonAdminActivity.class));
                break;
        }
    }
}
