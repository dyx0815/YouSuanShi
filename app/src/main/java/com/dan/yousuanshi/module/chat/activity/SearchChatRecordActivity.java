package com.dan.yousuanshi.module.chat.activity;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.base.MvpPresenter;
import com.dan.yousuanshi.base.MyApplication;
import com.dan.yousuanshi.dao.DataBaseHelper;
import com.dan.yousuanshi.module.chat.adapter.SearchChatRecordAdapter;
import com.dan.yousuanshi.module.chat.bean.ChatBean;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchChatRecordActivity extends BaseActivity {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.ll_search_data)
    LinearLayout llSearchData;

    private ChatBean pUser;
    private List<ChatBean> data;
    private List<ChatBean> searchData = new ArrayList<>();
    private SearchChatRecordAdapter adapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_chat_record;
    }

    @Nullable
    @Override
    protected MvpPresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        MyApplication.addActivity(this);
        pUser = (ChatBean) getIntent().getSerializableExtra("data");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchChatRecordAdapter(this,searchData);
        adapter.setOnItemClick(new SearchChatRecordAdapter.OnItemClick() {
            @Override
            public void onItemClick(ChatBean chatBean) {
                int position = data.indexOf(chatBean);
                Intent intent = new Intent(getActivity(),ChatSetActivity.class);
                intent.putExtra("data",position);
                setResult(1,intent);
                finish();
                hideInput(getActivity(),etSearch);
            }
        });
        recyclerView.setAdapter(adapter);
        initSearch();
    }

    @Override
    protected void loadData() {

    }

    private void initSearch() {
        etSearch.setFocusable(true);
        etSearch.requestFocus();
        showInput(this,etSearch);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                searchData.clear();
                if(StringUtils.isEmpty(etSearch.getText().toString())){
                    ToastUtils.showToast(getActivity(),"请输入搜索内容！");
                    return false;
                }
                data = DataBaseHelper.queryChatRecord(getActivity(),pUser.getPid(), UserUtils.getInstance().getUserBean().getUser_id(),pUser.getName());
                for(ChatBean item : data){
                    if(item.getStxt().contains(etSearch.getText().toString())){
                        item.setName(pUser.getName());
                        item.setUserIconUrl(pUser.getUserIconUrl());
                        searchData.add(item);
                    }
                }
                if(searchData.size()==0){
                    tvSearch.setText("“"+etSearch.getText().toString()+"”");
                    llSearchData.setVisibility(View.VISIBLE);
                }else{
                    llSearchData.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                }
                return true;
            }
        });
    }

    @OnClick(R.id.ll_back)
    public void onViewClicked() {
        finish();
    }

    private void hideInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void showInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }
}
