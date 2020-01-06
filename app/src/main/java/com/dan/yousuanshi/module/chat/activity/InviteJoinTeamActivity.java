package com.dan.yousuanshi.module.chat.activity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.addressbook.bean.TeamHomeBean;
import com.dan.yousuanshi.module.chat.presenter.InviteJoinTeamActivityPresenter;
import com.dan.yousuanshi.module.chat.view.InviteJoinTeamActivityView;
import com.dan.yousuanshi.utils.StatusBarUtil;
import com.dan.yousuanshi.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class InviteJoinTeamActivity extends BaseActivity<InviteJoinTeamActivityPresenter> implements InviteJoinTeamActivityView {

    @BindView(R.id.tv_team_name)
    TextView tvTeamName;
    @BindView(R.id.tv_industry)
    TextView tvIndustry;
    @BindView(R.id.tv_address_reason)
    TextView tvAddress;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.et_leave_message)
    EditText etLeaveMessage;
    @BindView(R.id.tv_no)
    TextView tvNo;
    @BindView(R.id.tv_yes)
    TextView tvYes;

    private int id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_invite_join_team;
    }

    @Nullable
    @Override
    protected InviteJoinTeamActivityPresenter initPresenter() {
        return new InviteJoinTeamActivityPresenter();
    }

    @Override
    protected void initView() {
        id = getIntent().getIntExtra("id",0);
        Map<String,String> map = new HashMap<>();
        map.put("companyId",String.valueOf(id));
        presenter.teamHome(map);
    }

    @Override
    protected void setStatusBar() {
        super.setStatusBar();
        StatusBarUtil.setColor(this,getColor(R.color.color_FC9F44),0);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void teamHomeSuccess(int code, TeamHomeBean data) {
        tvIndustry.setText(data.getIndustrya()+"："+data.getIndustryb());
        tvAddress.setText(data.getProvince()+"\t"+data.getCity()+"\t"+data.getDistrict());
        tvDate.setText(data.getCreate_time());
        etLeaveMessage.setText(data.getCompay_status());
    }

    @Override
    public void teamHomeFailed(int code, String msg) {
        ToastUtils.showToast(this, "获取企业主页失败：" + code + msg);
        Log.e("InviteJoinTeam", "获取企业主页失败：" + code + msg);
    }

    @OnClick({R.id.ll_back, R.id.tv_no, R.id.tv_yes})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_no:
                break;
            case R.id.tv_yes:
                break;
        }
    }
}
