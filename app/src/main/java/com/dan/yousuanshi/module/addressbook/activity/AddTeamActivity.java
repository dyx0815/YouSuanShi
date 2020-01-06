package com.dan.yousuanshi.module.addressbook.activity;

import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.base.MvpPresenter;

import butterknife.OnClick;

public class AddTeamActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_team;
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

    @OnClick({R.id.ll_back, R.id.ll_search, R.id.ll_scan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_search:
                startActivity(new Intent(this,SearchTeamActivity.class));
                finish();
                break;
            case R.id.ll_scan:
                Intent intent = new Intent(this,ApplyJoinTeamActivity.class);
                intent.putExtra("type",2);
                startActivity(intent);
                finish();
                break;
        }
    }
}
