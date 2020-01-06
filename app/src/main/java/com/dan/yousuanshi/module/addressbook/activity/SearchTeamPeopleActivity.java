package com.dan.yousuanshi.module.addressbook.activity;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.base.MvpPresenter;
import com.dan.yousuanshi.module.addressbook.adapter.TeamPeopleManagerAdapter;
import com.dan.yousuanshi.module.addressbook.bean.TeamPeopleBean;
import com.dan.yousuanshi.utils.StringUtils;
import com.dan.yousuanshi.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchTeamPeopleActivity extends BaseActivity {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_remind)
    TextView tvRemind;

    private List<TeamPeopleBean> teamPeopleList;
    private TeamPeopleManagerAdapter adapter;
    private List<TeamPeopleBean> searchData = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_team_people;
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
        etSearch.requestFocus();
        showInput(this,etSearch);
        teamPeopleList = getIntent().getParcelableArrayListExtra("data");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TeamPeopleManagerAdapter(this,searchData);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {

    }

    @OnClick(R.id.ll_back)
    public void onViewClicked() {
        finish();
    }

    private void initSearch() {
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(StringUtils.isEmpty(etSearch.getText().toString())){
                    ToastUtils.showToast(getActivity(),"请输入成员名称！");
                    return false;
                }
                searchData.clear();
                for(TeamPeopleBean item : teamPeopleList){
                    if(item.getReal_name().contains(etSearch.getText().toString())){
                        searchData.add(item);
                    }
                }
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideInput(this,etSearch);
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
