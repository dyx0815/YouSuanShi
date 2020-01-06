package com.dan.yousuanshi.module.shared.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.common.Constant;
import com.dan.yousuanshi.module.mine.bean.MyTeamBean;
import com.dan.yousuanshi.module.shared.adapter.AnnoucementAdapter;
import com.dan.yousuanshi.module.shared.adapter.AnnoucementInfoAdapter;
import com.dan.yousuanshi.module.shared.bean.AnnouncementLisBean;
import com.dan.yousuanshi.module.shared.presenter.AnnouncementActivityPresenter;
import com.dan.yousuanshi.module.shared.view.AnnouncementActivityView;
import com.dan.yousuanshi.utils.SPUtils;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;
import com.google.gson.Gson;
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
import per.goweii.rxhttp.request.base.BaseBean;

public class AnnouncementActivity extends BaseActivity<AnnouncementActivityPresenter> implements AnnouncementActivityView, OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.ll_submit)
    LinearLayout llSubmit;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ll_no_announcement)
    LinearLayout llNoAnnouncement;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;

    private int page = 1;
    private int pageSize = 10;
    private int companyId;
    private List<AnnouncementLisBean.DataBean> announcementList = new ArrayList<>();
    private AnnoucementAdapter adapter;
    private AnnoucementInfoAdapter adapter2;
    private boolean isMaster;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_announcement;
    }

    @Nullable
    @Override
    protected AnnouncementActivityPresenter initPresenter() {
        return new AnnouncementActivityPresenter();
    }

    @Override
    protected void initView() {
        String team = SPUtils.getInstance().get(Constant.SHARED_COMPANY,"");
        if(StringUtils.isEmpty(team)){
            companyId = UserUtils.getInstance().getUserBean().getUser_company();
        }else{
            companyId = new Gson().fromJson(team, MyTeamBean.class).getCompanyId();
        }
        isMaster = getIntent().getBooleanExtra("isMaster",false);
        smartRefreshLayout.setOnLoadMoreListener(this);
        smartRefreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if(isMaster){
            llSubmit.setVisibility(View.VISIBLE);
            adapter = new AnnoucementAdapter(this,announcementList,isMaster);
            adapter.setOnItemClick(new AnnoucementAdapter.OnItemClick() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(getActivity(),UpdateAnnouncementActivity.class);
                    intent.putExtra("data",announcementList.get(position));
                    startActivity(intent);
                }

                @Override
                public void remove(int position) {
                    Map<String,Object> map = new HashMap<>();
                    map.put("companyId",companyId);
                    map.put("afficheId",announcementList.get(position).getId());
                    presenter.deleteSharedAnnouncement(map,position);
                }
            });
            recyclerView.setAdapter(adapter);
        }else{
            adapter2 = new AnnoucementInfoAdapter(this,announcementList);
            adapter2.setOnItemClick(new AnnoucementInfoAdapter.OnItemClick() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(getActivity(),AnnouncementInfoActivity.class);
                    intent.putExtra("type",2);
                    intent.putExtra("data",announcementList.get(position));
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(adapter2);
        }
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        page = 1;
        announcementList.clear();
        Map<String,Object> map = new HashMap<>();
        map.put("companyId",companyId);
        map.put("page",page);
        map.put("pageSize",pageSize);
        presenter.getAnnouncement(map);
    }

    @Override
    public void getAnnouncementSuccess(int code, AnnouncementLisBean data) {
        if(data.getData().size() == 0){
            llNoAnnouncement.setVisibility(View.VISIBLE);
        }else{
            llNoAnnouncement.setVisibility(View.GONE);
            announcementList.addAll(data.getData());
            if(isMaster){
                adapter.notifyDataSetChanged();
            }else{
                adapter2.notifyDataSetChanged();
            }
        }
        if(data.getData().size()<pageSize){
            smartRefreshLayout.setEnableLoadMore(false);
        }
        smartRefreshLayout.finishRefresh();
        smartRefreshLayout.finishLoadMore();
    }

    @Override
    public void getAnnouncementFailed(int code, String msg) {
        ToastUtils.showToast(this,msg);
    }

    @Override
    public void deleteSharedAnnouncementSuccess(int code, BaseBean data,int position) {
        announcementList.remove(position);
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void deleteSharedAnnouncementFailed(int code, String msg) {
        ToastUtils.showToast(this,msg);
    }

    @OnClick({R.id.ll_back, R.id.ll_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_submit:
                Intent intent = new Intent(getActivity(),UpdateAnnouncementActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        announcementList.clear();
        Map<String,Object> map = new HashMap<>();
        map.put("companyId",companyId);
        map.put("page",page);
        map.put("pageSize",pageSize);
        presenter.getAnnouncement(map);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        Map<String,Object> map = new HashMap<>();
        map.put("companyId",companyId);
        map.put("page",page);
        map.put("pageSize",pageSize);
        presenter.getAnnouncement(map);
    }
}
