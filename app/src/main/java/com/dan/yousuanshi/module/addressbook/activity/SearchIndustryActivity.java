package com.dan.yousuanshi.module.addressbook.activity;

import android.content.Intent;
import android.os.Parcelable;
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
import com.dan.yousuanshi.base.MvpPresenter;
import com.dan.yousuanshi.module.addressbook.adapter.IndustrySearchAdapter;
import com.dan.yousuanshi.module.addressbook.bean.IndustryBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchIndustryActivity extends BaseActivity {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_remind)
    TextView tvRemind;

    private List<IndustryBean> data;
    private List<IndustryBean> searchData = new ArrayList<>();
    IndustrySearchAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_industry;
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
    }

    @Override
    protected void loadData() {
        Intent intent = getIntent();
        data = intent.getParcelableArrayListExtra("data");
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchIndustryActivity.this));
        adapter = new IndustrySearchAdapter(SearchIndustryActivity.this,searchData);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClick(new IndustrySearchAdapter.OnItemClick() {
            @Override
            public void chooseIndustry(View v, int position) {
                Intent intent1 = new Intent(SearchIndustryActivity.this,ChooseIndustryActivity.class);
                intent1.putExtra("data", (Parcelable) searchData.get(position));
                setResult(1,intent1);
                finish();
            }
        });
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
                        List<IndustryBean.ChildBean> data2 = data.get(i).getChild();
                        for(int j = 0;j < data2.size();j++){
                            String industry = data.get(i).getIndustry()+data2.get(j).getIndustry();
                            if(industry.contains(etSearch.getText().toString())){
                                IndustryBean industryBean = new IndustryBean();
                                industryBean.setIndustry(data.get(i).getIndustry());
                                List<IndustryBean.ChildBean> list = new ArrayList<>();
                                list.add(data2.get(j));
                                industryBean.setChild(list);
                                searchData.add(industryBean);
                                Log.e("SearchIndustry",data.get(i).getIndustry());
                            }
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
