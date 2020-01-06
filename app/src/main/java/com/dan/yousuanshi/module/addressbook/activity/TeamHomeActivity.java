package com.dan.yousuanshi.module.addressbook.activity;

import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.addressbook.bean.TeamHomeBean;
import com.dan.yousuanshi.module.addressbook.presenter.TeamHomeActivityPresenter;
import com.dan.yousuanshi.module.addressbook.view.TeamHomeActivityView;
import com.dan.yousuanshi.utils.ToastUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class TeamHomeActivity extends BaseActivity<TeamHomeActivityPresenter> implements TeamHomeActivityView {

    @BindView(R.id.riv_head_icon)
    RoundedImageView rivHeadIcon;
    @BindView(R.id.tv_team_name)
    TextView tvTeamName;
    @BindView(R.id.tv_industry)
    TextView tvIndustry;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_create_date)
    TextView tvCreateDate;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_credit_number)
    TextView tvCreditNumber;
    @BindView(R.id.tv_create_address)
    TextView tvCreateAddress;
    @BindView(R.id.tv_manage_scope)
    TextView tvManageScope;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_team_home;
    }

    @Nullable
    @Override
    protected TeamHomeActivityPresenter initPresenter() {
        return new TeamHomeActivityPresenter();
    }

    @Override
    protected void initView() {
        int id = getIntent().getIntExtra("id",0);
        Map<String,String> map = new HashMap<>();
        map.put("companyId",String.valueOf(id));
        presenter.teamHome(map);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void teamHomeSuccess(int code, TeamHomeBean data) {
        Glide.with(this).load(data.getCompany_logo()).into(rivHeadIcon);
        tvTeamName.setText(data.getC_name());
        tvIndustry.setText(data.getIndustrya()+":"+data.getIndustryb());
        tvAddress.setText(data.getProvince()+"\t"+data.getCity()+"\t"+data.getDistrict());
        tvMoney.setText(data.getRegister_money());
        tvCreateDate.setText(data.getCreate_time());
        tvStatus.setText(data.getCompay_status());
        tvCreditNumber.setText(data.getUnified_social_credit_code());
        tvCreateAddress.setText(data.getRegister_address());
        tvCreateAddress.setText("经营范围："+data.getBusiness_scope());
    }

    @Override
    public void teamHomeFailed(int code, String msg) {
        ToastUtils.showToast(this, "请求主页信息失败：" + code + msg);
        Log.e("TeamHomeActivity", "请求主页信息失败：" + code + msg);
    }

    @OnClick(R.id.ll_back)
    public void onViewClicked() {
        finish();
    }
}
