package com.dan.yousuanshi.module.addressbook.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.annotation.Nullable;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.base.MyApplication;
import com.dan.yousuanshi.module.addressbook.bean.PartPurviewBean;
import com.dan.yousuanshi.module.addressbook.presenter.SettingSonAdmin2ActivityPresenter;
import com.dan.yousuanshi.module.addressbook.view.SettingSonAdmin2ActivityView;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import per.goweii.rxhttp.request.base.BaseBean;

public class SettingSonAdmin2Activity extends BaseActivity<SettingSonAdmin2ActivityPresenter> implements SettingSonAdmin2ActivityView {

    @BindView(R.id.ll_submit)
    LinearLayout llSubmit;
    @BindView(R.id.switch_all_purview)
    Switch switchAllPurview;

    private int userId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting_son_admin2;
    }

    @Nullable
    @Override
    protected SettingSonAdmin2ActivityPresenter initPresenter() {
        return new SettingSonAdmin2ActivityPresenter();
    }

    @Override
    protected void initView() {
        userId = getIntent().getIntExtra("data",0);
        Map<String,Object> map = new HashMap<>();
        map.put("companyId", UserUtils.getInstance().getUserBean().getUser_company());
        map.put("masterId",userId);
        presenter.getPartPurview(map);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void addSonAdminSuccess(int code, BaseBean data) {
        finish();
    }

    @Override
    public void addSonAdminFailed(int code, String msg) {
        ToastUtils.showToast(this, msg);
    }

    @Override
    public void getPartPurviewSuccess(int code, PartPurviewBean data) {
        switchAllPurview.setChecked(data.getIsAllModel() == 1 ? true : false);
//        for(PartPurviewBean.PowerListBean item : data.getPowerList()){
//            for(PartPurviewBean.PowerListBean.ChildrenBean item1 : item.getChildren()){
//                if(item1.getIs_had() == 0){
//                    return;
//                }
//            }
//        }
//        switchAllPurview.setChecked(true);
    }

    @Override
    public void getPartPurviewFailed(int code, String msg) {
        ToastUtils.showToast(this, msg);
    }

    @OnClick({R.id.ll_back, R.id.ll_submit, R.id.ll_part_purview})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_submit:
                Map<String, Object> map = new HashMap<>();
                map.put("companyId", UserUtils.getInstance().getUserBean().getUser_company());
                map.put("masterId", userId);
                if (switchAllPurview.isChecked()) {
                    map.put("masterPower", "[]");
                    map.put("isAllPower", 1);
                } else {
                    ToastUtils.showToast(this, "请选择权限！");
                    return;
                }
                presenter.addSonAdmin(map);
                break;
            case R.id.ll_part_purview:
                MyApplication.addActivity(this);
                Intent intent = new Intent(this, PartPurviewActivity.class);
                intent.putExtra("masterId", userId);
                startActivity(intent);
                break;
        }
    }
}
