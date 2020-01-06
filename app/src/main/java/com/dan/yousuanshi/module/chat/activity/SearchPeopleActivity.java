package com.dan.yousuanshi.module.chat.activity;

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
import com.dan.yousuanshi.module.chat.adapter.SearchPeopleAdapter;
import com.dan.yousuanshi.module.chat.bean.SearchPeopleBean;
import com.dan.yousuanshi.module.chat.presenter.SearchPeopleActivityPresenter;
import com.dan.yousuanshi.module.chat.view.SearchPeopleActivityView;
import com.dan.yousuanshi.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchPeopleActivity extends BaseActivity<SearchPeopleActivityPresenter> implements SearchPeopleActivityView {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_remind)
    TextView tvRemind;

    private List<SearchPeopleBean.DataBean> data = new ArrayList<>();
    private SearchPeopleAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_people;
    }

    @Nullable
    @Override
    protected SearchPeopleActivityPresenter initPresenter() {
        return new SearchPeopleActivityPresenter();
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchPeopleAdapter(this,data);
        recyclerView.setAdapter(adapter);
        initSearch();
    }

    @Override
    protected void loadData() {

    }


    @Override
    public void searchPeopleSuccess(int code, SearchPeopleBean searchPeopleBean) {
        if (searchPeopleBean.getData() != null && searchPeopleBean.getData().size() > 0) {
            data.addAll(searchPeopleBean.getData());
            adapter.notifyDataSetChanged();
        }else{
            recyclerView.setVisibility(View.GONE);
            tvRemind.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void searchPeopleFailed(int code, String msg) {
        ToastUtils.showToast(this, "模糊查询失败：" + code + msg);
        Log.e("SearchPeople", "模糊查询失败：" + code + msg);
    }

    @OnClick(R.id.ll_back)
    public void onViewClicked() {
        finish();
    }

    private void initSearch() {
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                data.clear();
                Map<String,String> map = new HashMap<>();
                map.put("pageSize","10");
                map.put("searchStr",etSearch.getText().toString());
                presenter.searchPeople(map);
                return false;
            }
        });
    }
}
