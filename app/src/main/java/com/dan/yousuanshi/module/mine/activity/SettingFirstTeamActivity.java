package com.dan.yousuanshi.module.mine.activity;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.base.MvpPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingFirstTeamActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting_first_team;
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
