package com.dan.yousuanshi.module.addressbook.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dan.yousuanshi.R;
import com.dan.yousuanshi.base.BaseActivity;
import com.dan.yousuanshi.module.addressbook.adapter.TeamPeopleAdapter;
import com.dan.yousuanshi.module.addressbook.bean.TeamPeopleBean;
import com.dan.yousuanshi.module.addressbook.presenter.TeamPeopleActivityPresenter;
import com.dan.yousuanshi.module.addressbook.view.TeamPeopleActivityView;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class TeamPeopleActivity extends BaseActivity<TeamPeopleActivityPresenter> implements TeamPeopleActivityView {

    @BindView(R.id.ll_submit)
    LinearLayout llSubmit;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<TeamPeopleBean> teamPeopleList = new ArrayList<>();
    private TeamPeopleAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_team_people;
    }

    @Nullable
    @Override
    protected TeamPeopleActivityPresenter initPresenter() {
        return new TeamPeopleActivityPresenter();
    }

    @Override
    protected void initView() {
        Map<String,Object> map = new HashMap<>();
        map.put("companyId", UserUtils.getInstance().getUserBean().getUser_company());
        presenter.getTeamPeople(map);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TeamPeopleAdapter(this,teamPeopleList);
        adapter.setOnItemClick(new TeamPeopleAdapter.OnItemClick() {
            @Override
            public void onItemClick(TeamPeopleBean teamPeopleBean) {
                Intent intent = new Intent(getActivity(),AddDepartmentActivity.class);
                intent.putExtra("data",teamPeopleBean);
                setResult(1,intent);
                finish();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void getTeamPeopleSuccess(int code, List<TeamPeopleBean> data) {
        teamPeopleList.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getTeamPeopleFailed(int code, String msg) {
        ToastUtils.showToast(this,"获取公司成员失败："+code+msg);
        Log.e("TeamPeople","获取公司成员失败："+code+msg);
    }

    @OnClick({R.id.ll_back, R.id.ll_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_submit:

                break;
        }
    }
}
