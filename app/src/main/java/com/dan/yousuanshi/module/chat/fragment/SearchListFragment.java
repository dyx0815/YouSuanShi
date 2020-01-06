package com.dan.yousuanshi.module.chat.fragment;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseFragment;
import com.dan.yousuanshi.base.MvpPresenter;
import com.dan.yousuanshi.module.chat.adapter.SearchListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SearchListFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<String> data;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_list;
    }

    @Nullable
    @Override
    protected MvpPresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        initData();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new SearchListAdapter(data,getActivity()));
    }

    @Override
    protected void loadData() {

    }

    private void initData(){
        data = new ArrayList<>();
        data.add("伊泽瑞尔");
        data.add("薇恩");
        data.add("卢锡安");
    }
}
