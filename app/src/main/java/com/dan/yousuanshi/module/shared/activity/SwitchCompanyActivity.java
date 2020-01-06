package com.dan.yousuanshi.module.shared.activity;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.common.Constant;
import com.dan.yousuanshi.module.mine.bean.MyTeamBean;
import com.dan.yousuanshi.module.shared.adapter.SwitchCompanyAdapter;
import com.dan.yousuanshi.module.shared.presenter.SwitchCompanyActivityPresenter;
import com.dan.yousuanshi.module.shared.view.SwitchCompanyActivityView;
import com.dan.yousuanshi.utils.SPUtils;
import com.dan.yousuanshi.utils.ToastUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class SwitchCompanyActivity extends BaseActivity<SwitchCompanyActivityPresenter> implements SwitchCompanyActivityView {

    @BindView(R.id.ll_submit)
    LinearLayout llSubmit;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private SwitchCompanyAdapter adapter;
    private List<MyTeamBean> teamList = new ArrayList<>();
    private int companyId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_switch_company;
    }

    @Nullable
    @Override
    protected SwitchCompanyActivityPresenter initPresenter() {
        return new SwitchCompanyActivityPresenter();
    }

    @Override
    protected void initView() {
        companyId = getIntent().getIntExtra("companyId",0);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SwitchCompanyAdapter(this,teamList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        teamList.clear();
        Map<String,String> map = new HashMap<>();
        map.put("isAllCompany","1");
        presenter.getMyTeam(map);
    }

    @Override
    public void getMyTeamSuccess(int code, List<MyTeamBean> data) {
        for(MyTeamBean item : data){
            if(companyId == item.getCompanyId()){
                item.setChecked(true);
                break;
            }
        }
        teamList.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getMyTeamFailed(int code, String msg) {
        ToastUtils.showToast(this, msg);
    }

    @OnClick({R.id.ll_back, R.id.ll_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_submit:
                SPUtils.getInstance().save(Constant.SHARED_COMPANY,new Gson().toJson(adapter.getMyTeamBean()));
                finish();
                break;
        }
    }
}
