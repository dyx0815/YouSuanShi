package com.dan.yousuanshi.module.addressbook.activity;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.addressbook.adapter.SonAdminAdapter;
import com.dan.yousuanshi.module.addressbook.bean.SonAdminBean;
import com.dan.yousuanshi.module.addressbook.presenter.SettingSonAdminActivityPresenter;
import com.dan.yousuanshi.module.addressbook.view.SettingSonAdminActivityView;
import com.dan.yousuanshi.module.chat.activity.SharedBusinessCardActivity;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import per.goweii.rxhttp.request.base.BaseBean;

public class SettingSonAdminActivity extends BaseActivity<SettingSonAdminActivityPresenter> implements SettingSonAdminActivityView {

    @BindView(R.id.ll_submit)
    LinearLayout llSubmit;
    @BindView(R.id.ll_setting_son_admin)
    LinearLayout llSettingSonAdmin;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ll_son_admin)
    LinearLayout llSonAdmin;

    private SonAdminAdapter adapter;
    private List<SonAdminBean> sonAdminList = new ArrayList<>();
    private Dialog removeDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting_son_admin;
    }

    @Nullable
    @Override
    protected SettingSonAdminActivityPresenter initPresenter() {
        return new SettingSonAdminActivityPresenter();
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SonAdminAdapter(this,sonAdminList);
        adapter.setOnItemClick(new SonAdminAdapter.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                showDialog(sonAdminList.get(position));
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sonAdminList.clear();
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", UserUtils.getInstance().getUserBean().getUser_company());
        presenter.getSonAdmin(map);
    }

    @Override
    public void getSonAdminSuccess(int code, List<SonAdminBean> data) {
        if (data.size() == 0) {
            llSonAdmin.setVisibility(View.GONE);
        } else {
            sonAdminList.addAll(data);
            adapter.notifyDataSetChanged();
            llSonAdmin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getSonAdminFailed(int code, String msg) {
        ToastUtils.showToast(this, msg);
    }

    @Override
    public void removeSonAdminSuccess(int code, BaseBean data) {
        onResume();
    }

    @Override
    public void removeSonAdminFailed(int code, String msg) {
        ToastUtils.showToast(this, msg);
    }

    @OnClick({R.id.ll_back, R.id.ll_submit, R.id.ll_setting_son_admin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_submit:
                break;
            case R.id.ll_setting_son_admin:
                Intent intent = new Intent(this, SharedBusinessCardActivity.class);
                intent.putExtra("type", 7);
                startActivity(intent);
                break;
        }
    }

    private void showDialog(SonAdminBean sonAdminBean){
        if(removeDialog == null){
            removeDialog = new Dialog(this);
            removeDialog.setContentView(R.layout.dialog_clear_new_friend);
            TextView tvTitle = removeDialog.findViewById(R.id.tv_title);
            TextView tvCancel = removeDialog.findViewById(R.id.tv_cancel);
            TextView tvSure = removeDialog.findViewById(R.id.tv_sure);
            tvTitle.setText("确认移除该人员？");
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeDialog.dismiss();
                }
            });
            tvSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeDialog.dismiss();
                    Map<String,Object> map = new HashMap<>();
                    map.put("companyId",UserUtils.getInstance().getUserBean().getUser_company());
                    map.put("masterId",sonAdminBean.getUser_id());
                    presenter.removeSonAdmin(map);
                }
            });
        }
        removeDialog.show();
    }
}
