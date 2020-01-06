package com.dan.yousuanshi.module.addressbook.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.addressbook.adapter.SettingApplicationAdapter;
import com.dan.yousuanshi.module.addressbook.bean.DiyApplicationSettingBean;
import com.dan.yousuanshi.module.addressbook.presenter.SettingApplicationActivityPresenter;
import com.dan.yousuanshi.module.addressbook.view.SettingApplicationActivityView;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingApplicationActivity extends BaseActivity<SettingApplicationActivityPresenter> implements SettingApplicationActivityView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ll_add_diy)
    LinearLayout llAddDiy;

    private List<DiyApplicationSettingBean> diyList = new ArrayList<>();
    private SettingApplicationAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting_application;
    }

    @Nullable
    @Override
    protected SettingApplicationActivityPresenter initPresenter() {
        return new SettingApplicationActivityPresenter();
    }

    @Override
    protected void initView() {
        List<DiyApplicationSettingBean> data = getIntent().getParcelableArrayListExtra("data");
        diyList.addAll(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SettingApplicationAdapter(this,diyList);
        adapter.setOnItemClick(new SettingApplicationAdapter.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                diyList.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyDataSetChanged();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void settingApplicationSuccess(int code, List list) {
        finish();
    }

    @Override
    public void settingApplicationFailed(int code, String msg) {
        ToastUtils.showToast(this, "设置失败：" + code + msg);
        Log.e("SettingApplication", "设置失败：" + code + msg);
    }

    @OnClick({R.id.ll_back, R.id.ll_add_diy,R.id.ll_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_submit:
                Map<String,Object> map = new HashMap<>();
                map.put("companyId", UserUtils.getInstance().getUserBean().getUser_company());
                map.put("diyForm",new Gson().toJson(diyList));
                presenter.settingApplication(map);
                break;
            case R.id.ll_add_diy:
                startActivityForResult(new Intent(this,AddApplicationSettingActivity.class),1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == 1){
            DiyApplicationSettingBean diyApplicationSettingBean = data.getParcelableExtra("data");
            diyList.add(diyApplicationSettingBean);
            adapter.notifyItemInserted(diyList.size());
        }
    }
}
