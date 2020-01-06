package com.dan.yousuanshi.module.shared.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseFragment;
import com.dan.yousuanshi.base.MvpPresenter;
import com.dan.yousuanshi.module.shared.adapter.TemplateAdapter;
import com.dan.yousuanshi.module.shared.bean.TemplateBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TemplateManagerFragment extends BaseFragment {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_submit)
    LinearLayout llSubmit;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ll_add)
    LinearLayout llAdd;

    private List<TemplateBean.ChildrenBean> data;
    private List<TemplateBean.ChildrenBean> templateList = new ArrayList<>();
    private TemplateAdapter adapter;

    public TemplateManagerFragment(List<TemplateBean.ChildrenBean> data) {
        this.data = data;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_template_manager;
    }

    @Nullable
    @Override
    protected MvpPresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        templateList.addAll(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new TemplateAdapter(getActivity(),templateList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.ll_back, R.id.ll_submit, R.id.ll_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_submit:
                break;
            case R.id.ll_add:
                break;
        }
    }
}
