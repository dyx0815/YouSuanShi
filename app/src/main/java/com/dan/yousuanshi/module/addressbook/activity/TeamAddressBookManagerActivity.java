package com.dan.yousuanshi.module.addressbook.activity;

import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.base.MvpPresenter;

import butterknife.OnClick;

public class TeamAddressBookManagerActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_team_address_book_manager;
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

    @OnClick({R.id.ll_back, R.id.rl_team_address_book, R.id.rl_add_people, R.id.rl_admin_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.rl_team_address_book:
                startActivity(new Intent(this,TeamAddressBookActivity.class));
                break;
            case R.id.rl_add_people:
                startActivity(new Intent(this,AddPeopleActivity.class));
                break;
            case R.id.rl_admin_setting:
                startActivity(new Intent(this,AdminSettingActivity.class));
                break;
        }
    }
}
