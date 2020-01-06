package com.dan.yousuanshi.module.mine.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.addressbook.activity.FriendInfoActivity;
import com.dan.yousuanshi.module.mine.adapter.BlackListAdapter;
import com.dan.yousuanshi.module.mine.bean.BlackListBean;
import com.dan.yousuanshi.module.mine.presenter.BlackListActivityPresenter;
import com.dan.yousuanshi.module.mine.view.BlackListActivityView;
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

public class BlackListActivity extends BaseActivity<BlackListActivityPresenter> implements BlackListActivityView, OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_not_have_black_list)
    TextView tvNotHaveBlackList;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;

    private BlackListAdapter adapter;
    private int page = 1;
    private List<BlackListBean.DataBean> blackList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_black_list;
    }

    @Nullable
    @Override
    protected BlackListActivityPresenter initPresenter() {
        return new BlackListActivityPresenter();
    }

    @Override
    protected void initView() {
        Map<String,Object> map = new HashMap<>();
        map.put("page",page);
        presenter.getBlackList(map);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BlackListAdapter(this,blackList);
        adapter.setOnItemClick(new BlackListAdapter.OnItemClick() {
            @Override
            public void onItemClick(int userId) {
                Intent intent = new Intent(getActivity(), FriendInfoActivity.class);
                intent.putExtra("data",userId);
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
    public void getBlackListSuccess(int code, BlackListBean data) {
        smartRefreshLayout.finishRefresh();
        smartRefreshLayout.finishLoadMore();
        if(data.getData().size() == 0){
            tvNotHaveBlackList.setVisibility(View.VISIBLE);
            smartRefreshLayout.setVisibility(View.GONE);
        }else{
            tvNotHaveBlackList.setVisibility(View.GONE);
            smartRefreshLayout.setVisibility(View.VISIBLE);
            blackList.addAll(data.getData());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getBlackListFailed(int code, String msg) {
        ToastUtils.showToast(this,"获取黑名单列表失败："+code+msg);
        Log.e("BlackList","获取黑名单列表失败："+code+msg);
    }

    @OnClick(R.id.ll_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        blackList.clear();
        Map<String,Object> map = new HashMap<>();
        map.put("page",page);
        presenter.getBlackList(map);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        Map<String,Object> map = new HashMap<>();
        map.put("page",page);
        presenter.getBlackList(map);
    }
}
