package com.dan.yousuanshi.module.addressbook.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.base.MvpPresenter;
import com.dan.yousuanshi.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class AddFriendActivity extends BaseActivity {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.rl_create_team)
    RelativeLayout rlCreateTeam;
    @BindView(R.id.rl_join_team)
    RelativeLayout rlJoinTeam;

    private int type;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_friend;
    }

    @Nullable
    @Override
    protected MvpPresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        type = getIntent().getIntExtra("type", 1);
        if (type == 2) {
            rlCreateTeam.setVisibility(View.GONE);
            rlJoinTeam.setVisibility(View.GONE);
        }
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColor(getActivity(), getActivity().getColor(R.color.white), 0);
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    @OnClick({R.id.ll_back, R.id.rl_create_team, R.id.rl_join_team, R.id.rl_add_friend_phone, R.id.rl_add_friend_scan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.rl_create_team:
                startActivity(new Intent(this, CreateTeamActivity.class));
                break;
            case R.id.rl_join_team:
                startActivity(new Intent(this, AddTeamActivity.class));
                break;
            case R.id.rl_add_friend_phone:
                startActivity(new Intent(this, SearchFriendByPhoneActivity.class));
                break;
            case R.id.rl_add_friend_scan:
                break;
        }
    }

}
