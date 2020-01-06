package com.dan.yousuanshi.module.addressbook.activity;

import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.addressbook.adapter.MyApplyRecordAdapter;
import com.dan.yousuanshi.module.addressbook.bean.MyApplyRecordBean;
import com.dan.yousuanshi.module.addressbook.presenter.MyApplyRecordActivityPresenter;
import com.dan.yousuanshi.module.addressbook.view.MyApplyRecordActivityView;
import com.dan.yousuanshi.utils.StatusBarUtil;
import com.dan.yousuanshi.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class MyApplyRecordActivity extends BaseActivity<MyApplyRecordActivityPresenter> implements MyApplyRecordActivityView, OnLoadMoreListener, OnRefreshListener {

    @BindView(R.id.ll_clear)
    LinearLayout llClear;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;

    private List<MyApplyRecordBean.DataBean> applyList = new ArrayList<>();
    private MyApplyRecordAdapter adapter;
    private int page = 1;
    private Dialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_apply_record;
    }

    @Nullable
    @Override
    protected MyApplyRecordActivityPresenter initPresenter() {
        return new MyApplyRecordActivityPresenter();
    }

    @Override
    protected void setStatusBar() {
        super.setStatusBar();
        StatusBarUtil.setColor(this, getColor(R.color.white), 0);
    }

    @Override
    protected void initView() {
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        presenter.getMyApplyRecord(map);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyApplyRecordAdapter(this, applyList);
        recyclerView.setAdapter(adapter);
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setOnLoadMoreListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void getMyApplyRecordSuccess(int code, MyApplyRecordBean data) {
        if (page == 1) {
            applyList.clear();
        }
        if (data.getData().size() > 0) {
            llClear.setVisibility(View.VISIBLE);
        }
        applyList.addAll(data.getData());
        adapter.notifyDataSetChanged();
        smartRefreshLayout.finishLoadMore();
        smartRefreshLayout.finishRefresh();
    }

    @Override
    public void getMyApplyRecordFailed(int code, String msg) {
        ToastUtils.showToast(this, "获取我的申请记录：" + code + msg);
        Log.e("TeamHomeActivity", "获取我的申请记录：" + code + msg);
    }

    @Override
    public void clearApplyRecordSuccess(int code, List list) {
        llClear.setVisibility(View.GONE);
        applyList.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void clearApplyRecordFailed(int code, String msg) {
        ToastUtils.showToast(this, "清空我的申请记录：" + code + msg);
        Log.e("TeamHomeActivity", "清空我的申请记录：" + code + msg);
    }

    @OnClick({R.id.ll_back, R.id.ll_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_clear:
                showDialog();
                break;
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        presenter.getMyApplyRecord(map);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        presenter.getMyApplyRecord(map);
    }

    private void showDialog() {
        if (dialog == null) {
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_clear_team_apply);
            dialog.findViewById(R.id.tv_cancel).setOnClickListener((view) -> dialog.dismiss());
            dialog.findViewById(R.id.tv_sure).setOnClickListener((v) -> {
                presenter.clearApplyRecord();
                dialog.dismiss();
            });
        }
        dialog.show();
    }
}
