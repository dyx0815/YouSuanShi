package com.dan.yousuanshi.module.addressbook.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.base.MvpPresenter;
import com.dan.yousuanshi.utils.UserUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.OnClick;

public class TeamManagerActivity extends BaseActivity {

    @BindView(R.id.tv_team_name)
    TextView tvTeamName;
    @BindView(R.id.iv_team_proof)
    ImageView ivTeamProof;
    @BindView(R.id.riv_head_icon)
    RoundedImageView rivHeadIcon;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_team_manager;
    }

    @Nullable
    @Override
    protected MvpPresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        Glide.with(this).load(UserUtils.getInstance().getUserBean().getCompany_logo()).into(rivHeadIcon);
        tvTeamName.setText(UserUtils.getInstance().getUserBean().getC_name());
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.ll_back, R.id.ll_team, R.id.ll_address_book_manager, R.id.ll_certification_manager, R.id.ll_diy_manager})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_team:
                break;
            case R.id.ll_address_book_manager://通讯录管理
                startActivity(new Intent(this, TeamAddressBookManagerActivity.class));
                break;
            case R.id.ll_certification_manager://认证管理

                break;
            case R.id.ll_diy_manager://个性化管理
                startActivity(new Intent(this,DiyManagerActivity.class));
                break;
        }
    }
}
