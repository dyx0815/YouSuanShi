package com.dan.yousuanshi.module.addressbook.activity;

import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.base.MyApplication;
import com.dan.yousuanshi.module.addressbook.adapter.SearchTeamAdapter;
import com.dan.yousuanshi.module.addressbook.bean.SearchTeamBean;
import com.dan.yousuanshi.module.addressbook.presenter.SearchTeamActivityPresenter;
import com.dan.yousuanshi.module.addressbook.view.SearchTeamActivityView;
import com.dan.yousuanshi.utils.StatusBarUtil;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchTeamActivity extends BaseActivity<SearchTeamActivityPresenter> implements SearchTeamActivityView {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<SearchTeamBean.DataBean> teamList = new ArrayList<>();
    private SearchTeamAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_team;
    }

    @Nullable
    @Override
    protected SearchTeamActivityPresenter initPresenter() {
        return new SearchTeamActivityPresenter();
    }

    @Override
    protected void setStatusBar() {
        super.setStatusBar();
        StatusBarUtil.setColor(this,getColor(R.color.white),0);
    }

    @Override
    protected void initView() {
        initSearch();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchTeamAdapter(this,teamList);
        adapter.setOnItemClick(new SearchTeamAdapter.OnItemClick() {
            @Override
            public void onItemClick(SearchTeamBean.DataBean dataBean) {
                MyApplication.addActivity(SearchTeamActivity.this);
                Intent intent = new Intent(SearchTeamActivity.this,ApplyJoinTeamActivity.class);
                intent.putExtra("data",dataBean.getId());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void searchTeamSuccess(int code, SearchTeamBean searchTeamBean) {
        teamList.addAll(searchTeamBean.getData());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void searchTeamFailed(int code, String msg) {
        ToastUtils.showToast(this,"搜索公司失败！"+code+msg);
        Log.e("SearchTeam","搜索公司失败！"+code+msg);
    }

    @OnClick({R.id.ll_back, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_search:
                if(StringUtils.isEmpty(etSearch.getText().toString())){
                    ToastUtils.showToast(this,"搜索条件不能为空！");
                    return;
                }
                if(etSearch.getText().toString().length()<3){
                    ToastUtils.showToast(this,"公司名称必须大于3！");
                    return;
                }
                Map<String,String> map = new HashMap<>();
                map.put("searchName",etSearch.getText().toString());
                presenter.searchTeam(map);
                break;
        }
    }

    private void initSearch() {
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(StringUtils.isEmpty(etSearch.getText().toString())){
                    ToastUtils.showToast(SearchTeamActivity.this,"搜索条件不能为空！");
                    return false;
                }
                if(etSearch.getText().toString().length()<3){
                    ToastUtils.showToast(SearchTeamActivity.this,"公司名称必须大于3！");
                    return false;
                }
                Map<String,String> map = new HashMap<>();
                map.put("searchName",etSearch.getText().toString());
                presenter.searchTeam(map);
                return true;
            }
        });
    }
}
