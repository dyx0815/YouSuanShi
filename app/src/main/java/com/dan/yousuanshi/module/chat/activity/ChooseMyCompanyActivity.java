package com.dan.yousuanshi.module.chat.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.base.MvpPresenter;

public class ChooseMyCompanyActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_my_company;
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
}
