package com.dan.yousuanshi.module.addressbook.activity;

import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.base.MvpPresenter;

import butterknife.OnClick;

public class TeamAddressBookActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_team_address_book;
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

    @OnClick({R.id.ll_back, R.id.rl_department_manager, R.id.rl_people_manager, R.id.rl_application_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.rl_department_manager:
                startActivity(new Intent(this,DepartmentManagerActivity.class));
                break;
            case R.id.rl_people_manager:
                startActivity(new Intent(this,TeamPeopleManagerActivity.class));
                break;
            case R.id.rl_application_setting:
                startActivity(new Intent(this,ApplicationSettingActivity.class));
                break;
        }
    }
}
