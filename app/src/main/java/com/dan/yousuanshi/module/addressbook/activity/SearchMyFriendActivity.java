package com.dan.yousuanshi.module.addressbook.activity;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.base.MvpPresenter;
import com.dan.yousuanshi.module.addressbook.adapter.SearchMyFriendAdapter;
import com.dan.yousuanshi.module.addressbook.bean.MyFriendBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchMyFriendActivity extends BaseActivity {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_remind)
    TextView tvRemind;

    private List<MyFriendBean> data;
    private List<MyFriendBean> searchData = new ArrayList<>();
    private SearchMyFriendAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_my_friend;
    }

    @Nullable
    @Override
    protected MvpPresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        initSearch();
        etSearch.setFocusable(true);
        etSearch.setFocusableInTouchMode(true);
        etSearch.requestFocus();//获取焦点 光标出现
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchMyFriendAdapter(this,searchData);
        adapter.setOnItemClick(new SearchMyFriendAdapter.OnItemClick() {
            @Override
            public void onItemClick(int friendId) {
                Intent intent = new Intent(getActivity(),FriendInfoActivity.class);
                intent.putExtra("data",friendId);
                startActivity(intent);
                finish();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        Intent intent = getIntent();
        data = intent.getParcelableArrayListExtra("data");
    }

    @OnClick(R.id.ll_back)
    public void onViewClicked() {
        finish();
    }

    private void initSearch() {
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                searchData.clear();
                if (data.size() > 0) {
                    for (int i = 0; i < data.size(); i++) {
                        if(data.get(i).getNick_name().contains(etSearch.getText().toString())){
                            searchData.add(data.get(i));
                        }
                    }
                    if(searchData.size()>0){
                        tvRemind.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();
                        recyclerView.setVisibility(View.VISIBLE);
                    }else{
                        recyclerView.setVisibility(View.GONE);
                        tvRemind.setText("未搜索到关于“"+etSearch.getText().toString()+"”内容");
                        tvRemind.setVisibility(View.VISIBLE);
                    }
                }
                return true;
            }
        });
    }
}
