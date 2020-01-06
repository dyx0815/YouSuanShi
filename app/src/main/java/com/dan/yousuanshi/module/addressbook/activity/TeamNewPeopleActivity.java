package com.dan.yousuanshi.module.addressbook.activity;

import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.addressbook.adapter.TeamNewPeopleAdapter;
import com.dan.yousuanshi.module.addressbook.bean.TeamNewApplyBean;
import com.dan.yousuanshi.module.addressbook.presenter.TeamNewPeopleActivityPresenter;
import com.dan.yousuanshi.module.addressbook.view.TeamNewPeopleActivityView;
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

public class TeamNewPeopleActivity extends BaseActivity<TeamNewPeopleActivityPresenter> implements TeamNewPeopleActivityView, OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.ll_clear)
    LinearLayout llClear;

    private int page = 1;
    private int teamId;
    private TeamNewPeopleAdapter adapter;
    private List<TeamNewApplyBean.DataBean> applyList = new ArrayList<>();
    private Dialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_team_new_people;
    }

    @Nullable
    @Override
    protected TeamNewPeopleActivityPresenter initPresenter() {
        return new TeamNewPeopleActivityPresenter();
    }

    @Override
    protected void setStatusBar() {
        super.setStatusBar();
        StatusBarUtil.setColor(this, getColor(R.color.white), 0);
    }

    @Override
    protected void initView() {
        teamId = getIntent().getIntExtra("id", 0);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TeamNewPeopleAdapter(this, applyList);
        adapter.setOnItemClick(new TeamNewPeopleAdapter.OnItemClick() {
            @Override
            public void onItemClick(int id, int position) {
                Map<String, String> map = new HashMap<>();
                map.put("companyId", String.valueOf(teamId));
                map.put("isPass", "1");//1通过 2拒绝
                map.put("applyId", String.valueOf(id));
                presenter.agreeApply(map, position);
            }

            @Override
            public void lookInfo(TeamNewApplyBean.DataBean data) {
                Intent intent = new Intent(getActivity(), ApplyInfoActivity.class);
                intent.putExtra("data", data);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setOnLoadMoreListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        applyList.clear();
        Map<String, String> map = new HashMap<>();
        map.put("companyId", String.valueOf(teamId));
        map.put("page", String.valueOf(page));
        presenter.getNewApply(map);
    }

    @Override
    public void getNewApplySuccess(int code, TeamNewApplyBean data) {
        if (page == 1) {
            applyList.clear();
        }
        if (data.getData().size() > 0) {
            llClear.setVisibility(View.VISIBLE);
        }
        applyList.addAll(data.getData());
        adapter.notifyDataSetChanged();
        smartRefreshLayout.finishRefresh();
        smartRefreshLayout.finishLoadMore();
    }

    @Override
    public void getNewApplyFailed(int code, String msg) {
        ToastUtils.showToast(this, "获取申请列表失败：" + code + msg);
        Log.e("TeamHomeActivity", "获取申请列表失败：" + code + msg);
    }

    @Override
    public void agreeApplySuccess(int code, List list, int position) {
        applyList.get(position).setIs_pass(1);
        adapter.notifyItemChanged(position);
    }

    @Override
    public void agreeApplyFailed(int code, String msg) {
        ToastUtils.showToast(this, "同意申请失败：" + code + msg);
        Log.e("TeamHomeActivity", "同意申请失败：" + code + msg);
    }

    @Override
    public void clearApplySuccess(int code, List list) {
        llClear.setVisibility(View.GONE);
        applyList.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void clearApplyFailed(int code, String msg) {
        ToastUtils.showToast(this, "清空申请列表：" + code + msg);
        Log.e("TeamHomeActivity", "清空申请列表：" + code + msg);
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

    private void showDialog() {
        if (dialog == null) {
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_clear_team_apply);
            dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String, String> map = new HashMap<>();
                    map.put("companyId", String.valueOf(teamId));
                    presenter.clearApply(map);
                    dialog.dismiss();
                }
            });
        }
        dialog.show();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        Map<String, String> map = new HashMap<>();
        map.put("companyId", String.valueOf(teamId));
        map.put("page", String.valueOf(page));
        presenter.getNewApply(map);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        Map<String, String> map = new HashMap<>();
        map.put("companyId", String.valueOf(teamId));
        map.put("page", String.valueOf(page));
        presenter.getNewApply(map);
    }
}
