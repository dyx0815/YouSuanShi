package com.dan.yousuanshi.module.addressbook.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.addressbook.bean.SettingTeamBean;
import com.dan.yousuanshi.module.addressbook.bean.TeamInfoBean;
import com.dan.yousuanshi.module.addressbook.presenter.ApplicationSettingActivityPresenter;
import com.dan.yousuanshi.module.addressbook.view.ApplicationSettingActivityView;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ApplicationSettingActivity extends BaseActivity<ApplicationSettingActivityPresenter> implements ApplicationSettingActivityView {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.ll_diy)
    LinearLayout llDiy;
    @BindView(R.id.switch_agree_people_join)
    Switch switchAgreePeopleJoin;
    @BindView(R.id.ll_agree_people_join)
    LinearLayout llAgreePeopleJoin;
    @BindView(R.id.switch_search_team_name_join)
    Switch switchSearchTeamNameJoin;
    @BindView(R.id.ll_search_team_name_join)
    LinearLayout llSearchTeamNameJoin;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_application_setting;
    }

    @Nullable
    @Override
    protected ApplicationSettingActivityPresenter initPresenter() {
        return new ApplicationSettingActivityPresenter();
    }

    @Override
    protected void initView() {
        Map<String,Object> map = new HashMap<>();
        map.put("companyId", UserUtils.getInstance().getUserBean().getUser_company());
        presenter.getTeamInfo(map);
        switchSearchTeamNameJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> map = new HashMap<>();
                map.put("setType",0);
                map.put("companyId",UserUtils.getInstance().getUserBean().getUser_company());
                presenter.settingTeam(map,0);
            }
        });
        switchAgreePeopleJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> map = new HashMap<>();
                map.put("setType",1);
                map.put("companyId",UserUtils.getInstance().getUserBean().getUser_company());
                presenter.settingTeam(map,1);
            }
        });
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void getTeamInfoSuccess(int code, TeamInfoBean data) {
        if(data.getCan_not_replay_join() == 0){
            switchAgreePeopleJoin.setChecked(true);
        }
        if(data.getCan_not_search_by_name() == 0){
            switchSearchTeamNameJoin.setChecked(true);
        }
    }

    @Override
    public void getTeamInfoFailed(int code, String msg) {
        ToastUtils.showToast(this,"获取公司详情失败："+code+msg);
        Log.e("ApplicationSetting","获取公司详情失败："+code+msg);
    }

    @Override
    public void settingTeamSuccess(int code, SettingTeamBean data,int type) {
        if(type == 0){//是否能搜索
            switchSearchTeamNameJoin.setChecked(data.getResultType() == 0?true:false);
        }else if(type == 1){//允许成员加入
            switchAgreePeopleJoin.setChecked(data.getResultType() == 0?true:false);
        }
    }

    @Override
    public void settingTeamFailed(int code, String msg) {
        ToastUtils.showToast(this,"公司申请设置失败："+code+msg);
        Log.e("ApplicationSetting","公司申请设置失败："+code+msg);
    }

    @OnClick({R.id.ll_back,R.id.ll_diy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_diy:
                startActivity(new Intent(this,DiyApplicationSettingActivity.class));
                break;
        }
    }
}
