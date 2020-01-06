package com.dan.yousuanshi.module.chat.activity;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.base.MvpPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class ReserveMeetingActivity extends BaseActivity {

    @BindView(R.id.rl_reserve_meeting)
    RelativeLayout rlReserveMeeting;
    @BindView(R.id.rl_history_meeting)
    RelativeLayout rlHistoryMeeting;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reserve_meeting;
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

    @OnClick({R.id.ll_back, R.id.rl_reserve_meeting, R.id.rl_history_meeting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.rl_reserve_meeting:
                startActivity(new Intent(this,ReserveMeetingInfoActivity.class));
                finish();
                break;
            case R.id.rl_history_meeting:
                break;
        }
    }
}
